package therobotpeople.urcap;

import com.ur.urcap.api.contribution.ProgramNodeContribution;
import com.ur.urcap.api.domain.URCapAPI;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;

/*
 * The Node Contribution is what is seen in the 'Command' Tab of the URCap.
 */
public class TRPProgramNodeContribution implements ProgramNodeContribution {
	// These two instance variable are instantiated by Polyscope
	public static DataModel model;
	public static URCapAPI api;
	
	// The Pallet GUI runs on a separate thread so it is not blocking the 
	// usual functions of the UR. 
	public static Thread gui_thread = new Thread(new GUI_HomeImpl());

	public TRPProgramNodeContribution(URCapAPI api, DataModel model) {
		TRPProgramNodeContribution.api = api;
		TRPProgramNodeContribution.model = model;
	}

	// This method sends URScript to the Robot when the URCap is reached in
	// the program structure. This only occurs once. 
	@Override
	public void generateScript(ScriptWriter writer) {
		// Make sure that only one instance of the GUI is open 
		if (GUI_HomeImpl.on == false){
		    gui_thread.run();
		    writer.sync();
		    return;
		}
		
		// When generateScript is run while the GUI is on, this means that the 
		// user is trying to start/restart the palletising process. In order to
		// perform move commands, all commands must be written in through the
		// script writer from a static location.
		//
		// As a result, this requires all move commands to be placed in ../programs/trp_palletiser.script
		// prior to the start/restart. This function assumes this has been done already.
		// This implementation can be found in GUI_HomeImpl
		if( GUI_HomeImpl.on == true) {
			String currentLine = null;
			
			// Open the UR Script file determined by the last_selected.txt file
			FileManipulate ur_script = new FileManipulate("trp_palletiser.script", "../programs"); 
			
			// Read the UR Script file line by line, and write each command to the UR
			currentLine = ur_script.readLine();
			while(currentLine != null) {
				writer.appendLine(currentLine + "\n");
				currentLine = ur_script.readLine();
			}
			
			ur_script.close();
		}
		
		// This line of code prevents some error about the robot not being
		// set any commands for a long period of time. 
		writer.sync();
		
		// Not sure what Stefan was trying to achieve here

		// code to get rrobot position
		// getRobotRealtimeData getData = new getRobotRealtimeData();
		// getData.readNow();
		// DecimalFormat df = new DecimalFormat("#.####");
		// double[] tcp = getData.getActualTcpPose();
		// String showTcp = "p["+
		//		df.format(tcp[0])+","+
		//		df.format(tcp[1])+","+
		//		df.format(tcp[2])+","+
		//		df.format(tcp[3])+","+
		//		df.format(tcp[4])+","+
		//		df.format(tcp[5])+"]";
		//
		// writer.appendLine("popup(\""+showTcp+"\", title=\"OMG it worked\", blocking=True)");
		//
		///writer.appendLine("foo = "+showTcp+"\n");
		// 
		// writer.appendLine("foo2 = "+nameTextField2.getText()+"\n");
		// writer.appendLine("foo3 = "+nameTextField3.getText()+"\n");on == true
		// writer.appendLine("popup(\""+Double.toString(tcp[0])+"\", title=\"OMG it worked\", blocking=True)");
		// writer.sync();
		// Selector.script_file = "Popup.script";
		// writer.writeChildren();
	}
	
	
	
	@Override
	public void openView() {	
		// Java Code that is run when the 'Command' Tab is opened
	}

	@Override
	public void closeView() {
		// Java Code that is run when the 'Command' Tab is closed
	}

	@Override
	public String getTitle() {
		return "TRP";
		
	}

	@Override
	public boolean isDefined() {
		return true;
	}
}
