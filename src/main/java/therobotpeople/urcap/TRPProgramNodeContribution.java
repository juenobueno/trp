package therobotpeople.urcap;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.annotation.Label;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.InputTextField;
import com.ur.urcap.api.ui.component.LabelComponent;

public class TRPProgramNodeContribution implements ProgramNodeContribution {
	private static final String NAME = "name";

	private final DataModel model;
	private final URCapAPI api;

	public TRPProgramNodeContribution(URCapAPI api, DataModel model) {
		this.api = api;
		this.model = model;
	}
	
	@Input(id = "yourname")
	private InputTextField nameTextField;

	@Label(id = "titlePreviewLabel")
	private LabelComponent titlePreviewLabel;

	@Label(id = "messagePreviewLabel")
	private LabelComponent messagePreviewLabel;

	@Input(id = "yourname")
	public void onInput(InputEvent event) {
		if (event.getEventType() == InputEvent.EventType.ON_CHANGE) {
			setName(nameTextField.getText());
			updatePopupMessageAndPreview();
		}
	}

	@Override
	public void openView() {
		nameTextField.setText(getName());
		updatePopupMessageAndPreview();
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "HelloWorld: " + (model.isSet(NAME) ? getName() : "");
	}

	@Override
	public boolean isDefined() {
		return true;
	}

	@Override
	public void generateScript(ScriptWriter writer) {

		if (GUIHome.on == false){
		    GUIHome gui = new GUIHome(writer);
		    Thread t = new Thread(gui);
			t.start();
		}
		
		writer.sync();
	}

	private String generatePopupMessage() {
		return model.isSet(NAME) ? "Hello " + getName() + ", welcome to PolyScope!" : "No name set";
	}

	private void updatePopupMessageAndPreview() {
		messagePreviewLabel.setText(generatePopupMessage());
	}

	private String getName() {
		return model.get(NAME, "");
	}

	private void setName(String name) {
		if ("".equals(name)){
			model.remove(NAME);
		}else{
			model.set(NAME, name);
		}
	}
}
