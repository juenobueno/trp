package therobotpeople.urcap;

import java.text.DecimalFormat;

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
	@Input(id = "yourname2")
	private InputTextField nameTextField2;
	@Input(id = "yourname3")
	private InputTextField nameTextField3;

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
		    GUIHome gui = new GUIHome(api);
		    Thread t = new Thread(gui);
			t.start();
		}
		String temp = null;
		getRobotRealtimeData getData = new getRobotRealtimeData();
		double[] tcp = getData.getActualJointPose();
		DecimalFormat df = new DecimalFormat("#.####");
		String showTcp = "p["+
				df.format(tcp[0])+","+
				df.format(tcp[1])+","+
				df.format(tcp[2])+","+
				df.format(tcp[3])+","+
				df.format(tcp[4])+","+
				df.format(tcp[5])+"]";

		writer.appendLine("foo = "+showTcp+"\n");
		//writer.appendLine("foo2 = "+nameTextField2.getText()+"\n");
		//writer.appendLine("foo3 = "+nameTextField3.getText()+"\n");
		//writer.appendLine("popup(\""+Double.toString(tcp[0])+"\", title=\"OMG it worked\", blocking=True)");
		//writer.sync();
		///*
		//Selector.script_file = "Popup.script";
		
		
		if( Selector.first_run == true) {
		//if( Selector.script_file == "") {
		//	writer.sync();
		//}else {
			//Open and read the script file line by line into the writer
			FileManipulate urscript = new FileManipulate("../../programs/waypointTest.script");
			//FileManipulate urcopy = new FileManipulate("../../programs/robocopy.script");
			//writer.appendLine("popup(\"Messages\", title=\"OMG it worked\", blocking=True)");
			temp = urscript.readLine();
			while(temp != null) {
				//writer.appendLine("popup(\""+temp+"\", title=\"OMG it worked\", blocking=True)");
				writer.appendLine(temp+"\n");
				//urcopy.writeln(temp);
				temp = urscript.readLine();
			}
			
			//urcopy.close();
			urscript.close();
			
		}
		Selector.first_run = true;
			//writer.writeChildren();
		writer.sync();
		//}
		//*/
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
