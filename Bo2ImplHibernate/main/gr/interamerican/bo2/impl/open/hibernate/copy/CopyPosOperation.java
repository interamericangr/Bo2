package gr.interamerican.bo2.impl.open.hibernate.copy;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.creation.annotations.Property;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProvider;
import gr.interamerican.bo2.impl.open.hibernate.adapters.Unproxy;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractPoOperation;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Operation to copy a list of {@link CopyPoBean}s from one system to an other.
 */
public abstract class CopyPosOperation extends AbstractOperation {

	/**
	 * Default logger for Bo2.
	 */
	public static final Logger LOG = LoggerFactory.getLogger(CopyPosOperation.class);

	/**
	 * from
	 */
	@Property
	String from;
	/**
	 * to
	 */
	@Property
	String to;
	/**
	 * beans to copy.
	 */
	List<CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>>> poBeans = new ArrayList<CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>>>();
	/**
	 *
	 */
	private boolean executed = false;

	/**
	 * from
	 *
	 * @param manager
	 */
	void setFrom(String manager) {
		from = manager;
	}

	/**
	 * to
	 *
	 * @param manager
	 */
	void setTo(String manager){
		to = manager;
	}

	/**
	 * @return the pobeans
	 */
	public List<? super CopyPoBean<?, ?, ?>> getPoBeans() {
		return Utils.cast(poBeans);
	}

	/**
	 * clears the poBeans list
	 */
	public void clearList() {
		poBeans.clear();
	}
	/**
	 * read the Po and puts it in the bean back.
	 *
	 * @param bean
	 * @throws DataException
	 */
	void readPo(CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean) throws DataException {
		LOG.info("Reading a " + bean.getPoInterface() + StringConstants.SPACE + bean.getFromKey()); //$NON-NLS-1$
		if (bean.getFromPo() != null) {
			LOG.warn("fromPo is already set to " + bean.getFromPo()); //$NON-NLS-1$
			return;
		}
		PersistenceWorker<? super PersistentObject<?>> pw = Factory.createPw(bean.getPoInterface());
		((AbstractResourceConsumer) pw).setManagerName(from);
		try {
			pw.init(getProvider());
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		pw.open();
		bean.setFromPo(Factory.create(bean.getPoInterface()));
		ReflectionUtils.copyProperties(bean.getFromKey(), bean.getFromPo());
		bean.setFromPo(Unproxy.INSTANCE.execute(pw.read(bean.getFromPo())));
		pw.close();
	}

	/**
	 * copy the Po from->to
	 *
	 * @param bean
	 */
	void copyPo(CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean) {
		if (bean.getToPo() != null) {
			LOG.warn("toPo is already set to " + bean.getToPo()); //$NON-NLS-1$
			return;
		}
		bean.setToPo(PoUtils.deepCopy(bean.getFromPo()));
		if (bean.getToKey() != null) {
			ReflectionUtils.copyProperties(bean.getToKey(), bean.getToPo());
		}
	}

	/**
	 * run the post operation
	 *
	 * @param bean
	 * @throws LogicException
	 * @throws DataException
	 */
	void runPostOperation(CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean)
			throws LogicException, DataException {
		AbstractPoOperation<PersistentObject<?>> op = bean.getOperation();
		if (op == null) {
			return;
		}
		op.setManagerName(getManagerName());
		try {
			op.init(getProvider());
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		op.open();
		op.setPo(bean.getToPo());
		op.execute();
		bean.setToPo(op.getPo());
		op.close();
	}


	/**
	 * write the Pos to the database.
	 *
	 * @throws DataException
	 */
	void writePos() throws DataException {
		for (CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean : poBeans) {
			LOG.info("Writing a " + bean.getPoInterface() + StringConstants.SPACE + bean.getToKey()); //$NON-NLS-1$
			PersistenceWorker<? super PersistentObject<?>> pw = Factory.createPw(bean.getPoInterface());
			((AbstractResourceConsumer) pw).setManagerName(to);
			try {
				pw.init(getProvider());
			} catch (InitializationException e) {
				throw new DataException(e);
			}
			pw.open();
			pw.store(bean.getToPo());
			pw.close();
		}
	}

	/**
	 * @param manager
	 * @throws DataException
	 */
	void closeHibernateSession(String manager) throws DataException {
		HibernateSessionProvider fromHsp;
		try {
			fromHsp = getProvider().getResource(manager, HibernateSessionProvider.class);
		} catch (InitializationException e) {
			throw new DataException(e);
		}
		fromHsp.getHibernateSession().close();
	}

	/**
	 * validates the bean
	 *
	 * @param bean
	 * @throws LogicException
	 */
	void validateBean(CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean)
			throws LogicException {
		if ((bean.getFromPo() != null) && (!bean.getPoInterface().isAssignableFrom(bean.getFromPo().getClass()))) {
			throw new LogicException("From Po is not an instance of CopyPoBean interface"); //$NON-NLS-1$
		}
		if ((bean.getToPo() != null) && !bean.getPoInterface().isAssignableFrom(bean.getToPo().getClass())) {
			throw new LogicException("To Po is not an instance of CopyPoBean interface"); //$NON-NLS-1$
		}
		if ((bean.getFromPo() != null) && (bean.getToPo() != null)
				&& (bean.getFromPo().getClass() != bean.getToPo().getClass())) {
			throw new LogicException("To Po is not same type with from Po"); //$NON-NLS-1$
		}
		if (!bean.getPoInterface().isInterface()) {
			throw new LogicException("Po interface is not an interface"); //$NON-NLS-1$
		}
		if (bean.getFromKey().getClass() != bean.getToKey().getClass()) {
			throw new LogicException("To Po key is not same type with From Po key"); //$NON-NLS-1$
		}
	}

	@Override
	public void execute() throws LogicException, DataException {
		if (CollectionUtils.isNullOrEmpty(poBeans)) {
			return;
		}
		if (executed) {
			throw new IllegalStateException("CopyPosOperation cannot be executed twice."); //$NON-NLS-1$
		}
		executed = true;
		for (CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean : poBeans) {
			validateBean(bean);
		}
		LOG.info("Copying from " + from + " to " + to); //$NON-NLS-1$ //$NON-NLS-2$
		for (CopyPoBean<?, PersistentObject<?>, AbstractPoOperation<PersistentObject<?>>> bean : poBeans) {
			readPo(bean);
			copyPo(bean);
			runPostOperation(bean);
		}
		if (!from.equals(to)) {
			closeHibernateSession(from);
		}
		writePos();
	}
}
