package therobotpeople.urcap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public final class DashboardServerInterface {

	private static Socket sock;
	private static BufferedReader in;
	private static PrintWriter out;

	//If the socket ever fails, iwll need to reconnect it

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
		out.println("load "+Name+"\n");

		try {
			return in.readLine();

		} catch (IOException e) {
			return "failed";
		}

	}

	public static boolean Play_Program() {
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
		out.println("quit\n");
	}

	public static void Shutdown() {
		out.println("shutdown\n");
	}

	public static boolean is_Program_Running() {
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
}
