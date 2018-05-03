package therobotpeople.urcap;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

public class TRPProgramNodeContribution implements ProgramNodeContribution {
	private static final String NAME = "name";

	private final DataModel model;
	private final URCapAPI api;

	public TRPProgramNodeContribution(URCapAPI api, DataModel model) {
		this.api = api;
		this.model = model;
	}

	@Override
	public void openView() {
		
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "Test";
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {
		CustomGUI gui = new CustomGUI(writer);
		
		if(CustomGUI.on == false) {
			Thread t = new Thread(gui);
			t.start();
		}
		
		writer.sync();
	}
}
