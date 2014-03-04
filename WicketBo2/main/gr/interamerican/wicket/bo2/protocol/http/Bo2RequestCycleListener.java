package gr.interamerican.wicket.bo2.protocol.http;

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.enums.TargetEnvironment;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.Timer;
import gr.interamerican.bo2.utils.mail.MailMessage;
import gr.interamerican.wicket.def.WicketOutputMedium;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.PageRequestHandlerTracker;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.IPageRequestHandler;

/**
 * {@link IRequestCycleListener} implementation that facilitates transaction management
 * and OSIV implementation for the integration of Bo2 with wicket 1.5.
 * 
 * <br/><br/>
 * <em>This is a SINGLETON and must be thread safe.</em>
 * <br/><br/>
 * 
 * {@link #onBeginRequest(RequestCycle)} begins the transaction. <br/>
 * {@link #onException(RequestCycle, Exception)} rolls back the transaction and registers an error message on the UI. <br/>
 * {@link #onRequestHandlerScheduled(RequestCycle, IRequestHandler)} cleans up any error messages registered previously. </br>
 * {@link #onRequestHandlerResolved(RequestCycle, IRequestHandler)} cleans up any error messages registered previously. </br>
 * {@link #onRequestHandlerExecuted(RequestCycle, IRequestHandler)} commits the transaction. </br>
 * {@link #onEndRequest(RequestCycle)} cleans up any resources, including the Hibernate session (OSIV implementation).
 */
@SuppressWarnings("nls")
public class Bo2RequestCycleListener extends AbstractRequestCycleListener {
	
	@Override
	public void onBeginRequest(RequestCycle cycle) {
		Bo2WicketRequestCycle.stats.cycles++;
		try {
			Bo2WicketRequestCycle.get().timer = new Timer();
			Session<?, ?> session = Bo2WicketSession.get();
			Bo2Session.setSession(session);
			Bo2WicketRequestCycle.get().provider = Bo2.getDefaultDeployment().getProvider();
			Bo2Session.setProvider(Bo2WicketRequestCycle.get().provider);
			TransactionManager manager = Bo2WicketRequestCycle.get().provider.getTransactionManager();
			if(manager!=null) {
				manager.begin();
				debug("started the transaction manager " + Bo2WicketRequestCycle.get().toString());
			}
		} catch (CouldNotBeginException e) {
			throw new RuntimeException(e);
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public IRequestHandler onException(RequestCycle cycle, Exception ex) {
		Throwable t = ExceptionUtils.unwrap(ex);
		Bo2WicketRequestCycle.stats.updateExceptionStats(t);
		Bo2WicketRequestCycle.get().error = true;
		try {
			TransactionManager manager = Bo2WicketRequestCycle.get().provider.getTransactionManager();
			if(manager!=null) {
				manager.rollback();
				debug("rolled back the transaction.");
			}
		} catch (CouldNotRollbackException cnrbex) {
			cnrbex.setInitial(t);
			emergencyLogAndEmail("CouldNotRollbackException");
			Bo2WicketRequestCycle.LOGGER.error("CouldNotRollbackException: " + ExceptionUtils.getThrowableStackTrace(cnrbex));
		}
		
		/*
		 * If an invocation target exception is somewhere in the chain,
		 * use its (non null) target to render the error stack trace.
		 */
		if(ExceptionUtils.isCausedBy(t, InvocationTargetException.class)) {
			InvocationTargetException itex = ExceptionUtils.causeInTheChain(t, InvocationTargetException.class);
			t = Utils.notNull(itex.getTargetException(), t);
		}
		
		/*
		 * Facilitate debugging of unit tests run on DEVELOPMENT environment.
		 */
		if(Bo2.getDefaultDeployment().getDeploymentBean().getTargetEnvironment()==TargetEnvironment.DEVELOPMENT) {
			Bo2WicketRequestCycle.LOGGER.error(ExceptionUtils.getThrowableStackTrace(t));
		}
		
		IRequestablePage page = null;
		IPageRequestHandler pageHandler = PageRequestHandlerTracker.getLastHandler(RequestCycle.get());
		if(pageHandler!=null) {
			page = pageHandler.getPage();
		}
		
		if (page instanceof WicketOutputMedium) {
			WicketOutputMedium outputMedium = (WicketOutputMedium) page;
			/*
			 * TODO: why? This is also the last handler. Are there any cases this is not true?
			 */
			IRequestHandler target = RequestCycle.get().getRequestHandlerScheduledAfterCurrent();
			if(target instanceof AjaxRequestTarget) {
				AjaxRequestTarget art = (AjaxRequestTarget) target;
				outputMedium.showError(t, art);
			} else {
				Bo2WicketRequestCycle.LOGGER.error("Failed to display error from Throwable " + t.toString());
			}
			return target;
		}
		return null;
		
	}
	
	@Override
	public void onRequestHandlerScheduled(RequestCycle cycle, IRequestHandler handler) {
		clearErrorPanel();
	}
	
	@Override
	public void onRequestHandlerResolved(RequestCycle cycle, IRequestHandler handler) {
		clearErrorPanel();
	}
	
	@Override
	public void onRequestHandlerExecuted(RequestCycle cycle, IRequestHandler handler) {
		/*
		 * If an error has occurred, the transaction has already rolled back.
		 */
		if(Bo2WicketRequestCycle.get().error) {
			debug("Nothing to do, an error was registered in the cycle.");
			return;
		}
		
		try {
			TransactionManager manager = Bo2WicketRequestCycle.get().provider.getTransactionManager();
			if(manager!=null) {
				manager.commit();
				debug("committed the transaction.");					
			}
		} catch (CouldNotCommitException cnce) {
			emergencyLogAndEmail("CouldNotCommitException");
			Bo2WicketRequestCycle.LOGGER.error("CouldNotCommitException: " + ExceptionUtils.getThrowableStackTrace(cnce));
			throw new RuntimeException(cnce);		
		}
	}
	
	@Override
	public void onEndRequest(RequestCycle cycle) {
		try {
			Bo2WicketRequestCycle.stats.logForDebugging(workerNames(), Bo2WicketRequestCycle.get().timer);
			debug("performing cleanup.");
			Bo2WicketRequestCycle.get().cleanup();
			debug("cleaned up.");
		}catch (DataException de) {
			Bo2WicketRequestCycle.LOGGER.error("DataException on cleanup: " + ExceptionUtils.getThrowableStackTrace(de));
			throw new RuntimeException(de);		
		} finally {
			Bo2Session.setSession(null);
			Bo2Session.setProvider(null);
			Bo2WicketRequestCycle.release();
		}
	}
	
	/**
	 * Clears any messages from the error panel. This happens on the next Ajax request
	 * after the error appeared.
	 */
	void clearErrorPanel() {
		IPageRequestHandler pageHandler = PageRequestHandlerTracker.getLastHandler(RequestCycle.get());
		if (pageHandler!=null && pageHandler.getPage() instanceof WicketOutputMedium) {
			WicketOutputMedium outputMedium = (WicketOutputMedium) pageHandler.getPage();
			if(pageHandler instanceof AjaxRequestTarget) {
				AjaxRequestTarget art = (AjaxRequestTarget) pageHandler;
				outputMedium.clearMessages(art);
			}
		}
	}
	
	/**
	 * Closes any worker opened during this cycle.
	 * @return Returns the worker type names.
	 */
	String workerNames() {
		StringBuilder sb = new StringBuilder();
		for (Worker worker : Bo2WicketRequestCycle.get().workers) {
			sb.append(worker.getClass().getSimpleName());
			sb.append(COMMA);
		}
		return sb.toString();
	}
	
	/**
	 * Writes a debug message through the logger.
	 * 
	 * @param msg
	 */
	void debug(String msg) {
		if (Bo2WicketRequestCycle.LOGGER.isDebugEnabled()) {
			Bo2WicketRequestCycle.LOGGER.debug(msg);
		}
	}
	
	/*
	 * These methods are temporary. Wicket 1.5 should not require these
	 */
	
	/**
	 * EMERGENCY LOG AND EMAIL
	 * @param exceptionType 
	 */
	void emergencyLogAndEmail(String exceptionType) {
		String msg = exceptionType + " for user " + Bo2Session.getUserId() + " and workers: " + workerNames() + " on " + new Date();
		Bo2WicketRequestCycle.LOGGER.error(msg);
		
		try {
			String[] recipients = recepients();
			if(recipients == null) {
				return;
			}

			MailMessage m = new MailMessage();
			m.setFrom("no-reply@interamerican.gr");
			for (String recipient : recipients) {
				m.addTo(recipient + "@interamerican.gr");
			}
			m.setSubject(exceptionType);
			m.setMessage(msg);
			m.send();
		} catch (Exception e) {
			Bo2WicketRequestCycle.LOGGER.error("Failed to notify " + exceptionType + " by email due to: " + e.getMessage());
		}
	}
	
	/**
	 * @return Emergency email recepients
	 */
	String[] recepients() {
		switch(Bo2.getDefaultDeployment().getDeploymentBean().getTargetEnvironment()) {
			case UAT:
				return new String[]{"skondrasp", "katerosd", "sofrasth", "nakoss", "milonakisv"};
			case PRODUCTION:
				return new String[]{"zabelid", "katerosd", "sofrasth", "nakoss", "milonakisv"};
			default:
				return null;
		}
	}
}
