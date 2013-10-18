package gr.interamerican.bo2.samples;

import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.legacy.LegacyPwAdapter;
import interamerican.architecture.PersistenceWorker;

/**
 * Sample implementation of {@link LegacyPwAdapter}.
 */
@ManagerName("LOCALDB")
public class SampleLegacyPwAdapter extends LegacyPwAdapter<SamplePo> {

	/**
	 * Creates a new SampleLegacyPwAdapter object. 
	 *
	 * @param pw
	 */
	public SampleLegacyPwAdapter(PersistenceWorker<?> pw) {
		super(pw);		
	}
	
	/**
	 * @return returns the legacy persistence worker.
	 */
	public MockLegacyPw getPw() {
		return (MockLegacyPw) this.legacy;
	}

}
