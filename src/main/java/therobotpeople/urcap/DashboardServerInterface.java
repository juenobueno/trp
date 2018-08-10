package therobotpeople.urcap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * DashboardServerInterface connects to a locally hosted server in order
 * to control the state of the UR. Strings are written to the open port
 * in order to issue commands to the UR. 
 */
public final class DashboardServerInterface {

	private static Socket sock = null;
	private static BufferedReader in;
	private static PrintWriter out;

	//If the socket ever fails, will need to reconnect it

	public DashboardServerInterface() {
		Open();
	}

	public DashboardServerInterface(String Address) {
		Open(Address);
	}

	public static boolean Close() {
	//This will close the connection
		try {
			out.close();
			in.close();
			sock.close();

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Open() {
		try {
			//Make a connection to a UR robot on the local server/pendant if this is being run on the pendant
			sock = new Socket("127.0.0.1", 29999);
			sock.setSoTimeout(50);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			in.readLine(); //this will be "Connected: Universal Robots Dashboard Server"
			return true;
		} catch (UnknownHostException e1) {
			//
			return false;
		} catch (IOException e1) {
			//
			return false;
		}
	}

	public static boolean Open(String Address) {
		try {
			//Make a connection to a UR robot on the local server/pendant if this is being run on the pendant
			sock = new Socket(Address, 29999);
			sock.setSoTimeout(50);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			in.readLine(); //this will be "Connected: Universal Robots Dashboard Server"
			return true;
		} catch (UnknownHostException e1) {
			//
			return false;
		} catch (IOException e1) {
			//
			return false;
		}
	}

	public static String Load_Program(String Name) {
		test_and_open();
		out.println("load "+Name+"\n");

		try {
			return in.readLine();

		} catch (IOException e) {
			return "failed";
		}

	}

	public static boolean Play_Program() {
		test_and_open();
		out.println("play\n");

		try {
			if (in.readLine() == "Starting program") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Stop_Program() {
		test_and_open();
		out.println("stop\n");
		//return true;
		try {
			if (in.readLine() == "Stopped") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean Pause_Program() {
		test_and_open();
		out.println("pause\n");
		return true;
		//try {
		//	if(in.readLine() == "Pausing program") {
		//		return true;
		//	}else {
		//		return false;
		//	}
		//} catch (IOException e) {

		//	e.printStackTrace();
		//	return false;
		//}
	}

	public static void Disconnect() {
		test_and_open();
		out.println("quit\n");
	}

	public static void Shutdown() {
		test_and_open();
		out.println("shutdown\n");
	}

	public static boolean is_Program_Running() {
		test_and_open();
		out.println("running\n");
		try {
			if (in.readLine() == "Program running:True") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static String Robot_Mode() {
		test_and_open();
		out.println("robotmode\n");

		String val = "";
		try {
			val = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return val;
	}

	public static String Get_Loaded_Program() {
		test_and_open();
		out.println("get loaded program\n");
		String val="";
		try {
			val = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static boolean Open_Popup() {
		test_and_open();
		out.println("popup blag\n");
		try {
			if (in.readLine() == "showing popup") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Close_Popup() {
		test_and_open();
		out.println("close popup\n");
		try {
			if (in.readLine() == "closing popup") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Log() {
		test_and_open();
		out.println("addToLog blah\n");
		try {
			if (in.readLine() == "Added log message") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean is_Program_Saved() {
		test_and_open();
		out.println("isProgramSaved\n");
		try {
			if (in.readLine() == "True") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static String Get_Program_State() {
		test_and_open();
		out.println("programState");
		String val = "";
		try {
			val = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static String Polyscope_Version() {
		test_and_open();
		out.println("PolyscopeVersion\n");
		String val = "";
		try {
			val = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static boolean Set_User_Role() {
		test_and_open();
		out.println("setUserRole none\n");
		try {
			if (in.readLine() == "Setting user role: none") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Power_On() {
		test_and_open();
		out.println("power on\n");
		try {
			if (in.readLine() == "Powering On") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Power_Off() {
		test_and_open();
		out.println("power off\n");
		try {
			if (in.readLine() == "Powering off") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Brake_Release() {
		test_and_open();
		out.println("brake release\n");
		try {
			if (in.readLine() == "Brake releasing") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static String Get_Safety_Mode() {
		test_and_open();
		out.println("safetymode\n");
		String val = "";
		try {
			val = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}

	public static boolean Unlock_Protective_Stop() {
		test_and_open();
		out.println("unlock protective stop\n");
		try {
			if (in.readLine() == "Protective stop releasing") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Close_Protective_Popup() {
		test_and_open();
		out.println("close safety popup\n");
		try {
			if (in.readLine() == "closing safety popup") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean Load_Installation() {
		test_and_open();
		out.println("load installation default.installation\n");
		try {
			if (in.readLine() == "Load installation: default.installation") {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean is_Open() {
		if (sock == null) {
			return false;
		}

		return sock.isConnected();
	}
	
	public static void test_and_open() {
		if( sock == null) {
			Open();
		}
	}
}
