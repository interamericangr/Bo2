package gr.interamerican.bo2.gui.batch.runtime;

import gr.interamerican.bo2.arch.batch.LongProcessLauncher;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.gui.batch.GuiLauncher;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeWithPropertiesCmd;
import gr.interamerican.bo2.impl.open.runtime.PropertiesLauncherParamsNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.ConsoleLauncher;
import gr.interamerican.bo2.utils.NumberUtils;

/**
 * executes a {@link BatchProcess} with the given properties
 */
public class BpPropertiesLauncherCmd extends AbstractBo2RuntimeWithPropertiesCmd {

	/**
	 * @return if the process will support GUI
	 */
	private boolean supportGui() {
		if ((executionProperties.getProperty(PropertiesLauncherParamsNames.WITHGUI) != null)
				&& (NumberUtils.string2Int(executionProperties
						.getProperty(PropertiesLauncherParamsNames.WITHGUI)) > 0)) {
			return true;
		}
		return false;
	}
	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		LongProcessLauncher lp = null;
		if (supportGui()) {
			lp = new GuiLauncher();
		}else{
			lp = new ConsoleLauncher();
		}
		lp.launch(executionProperties);
	}
}
