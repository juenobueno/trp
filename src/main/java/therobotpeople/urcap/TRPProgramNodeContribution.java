package therobotpeople.urcap;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.util.Filter;
import com.ur.urcap.api.domain.variable.Variable;
import com.ur.urcap.api.ui.annotation.Input;
import com.ur.urcap.api.ui.annotation.Label;
import com.ur.urcap.api.ui.component.InputEvent;
import com.ur.urcap.api.ui.component.InputTextField;
import com.ur.urcap.api.ui.component.LabelComponent;

public class TRPProgramNodeContribution implements ProgramNodeContribution {
	private static final String NAME = "name";

	private final DataModel model;
	public static URCapAPI api;
	public static Thread gui_home_thread = new Thread(new GUIHome());
	//private final ScriptWriter writer;

	public TRPProgramNodeContribution(URCapAPI api, DataModel model) {
		TRPProgramNodeContribution.api = api;
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
		}
	}
	

	@Override
	public void openView() {	
	}

	@Override
	public void closeView() {
	}

	@Override
	public String getTitle() {
		return "TRP";
		
	}

	@Override
	public boolean isDefined() {
		return true;
	}
	
	@Override
	public void generateScript(ScriptWriter writer) {
		String temp = null;
		if( GUIHome.on == true) {
			//if( Selector.script_file == "") {
			//	writer.sync();
			//}else {
				//Open and read the script file line by line into the writer
			FileManipulate urscript = new FileManipulate("waypointTest.script", "../programs"); 
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
		
		if (GUIHome.on == false){
		    gui_home_thread.run();
		}

		
		
		//writer.appendLine("foo = "+showTcp+"\n");
		///*
		//writer.appendLine("foo2 = "+nameTextField2.getText()+"\n");
		//writer.appendLine("foo3 = "+nameTextField3.getText()+"\n");on == true
		//writer.appendLine("popup(\""+Double.toString(tcp[0])+"\", title=\"OMG it worked\", blocking=True)");
		//writer.sync();
		//Selector.script_file = "Popup.script";
		
		
		
			//writer.writeChildren();
		writer.sync();
		//}
	}

	private String generatePopupMessage() {
		return model.isSet(NAME) ? "Hello " + getName() + ", welcome to PolyScope!" : "No name set";
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
