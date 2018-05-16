package therobotpeople.urcap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManipulate {
	
	//private String folder = null;
	//private String filename = null;
	private File file = null;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	
	public FileManipulate(String file_name) {
		this(file_name, "Default");
	}
	
	
	public FileManipulate(String file_name, String folder) {
		//this.folder = folder;
		//this.filename = file_name;
		this.file = new File(folder+"/"+file_name);
		
		if( this.file.exists() == false) {
			try {
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		
	}
	
	public boolean exists() {
		if( this.file == null) {
			return false;
		}
		return this.file.exists();
	}
	
	public void close() {
		//Reset the class by closing the reader and writer and setting the variables to null
		try {
			if( this.reader != null) {
				this.reader.close();
				this.reader = null;
			}
			if( this.writer != null) {
				this.writer.close();
				this.writer = null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String read() {
		try {
			if( this.writer !=null) {
				this.writer.close();
				this.writer = null;
			}
			if( this.reader == null) {
				this.reader = new BufferedReader(new FileReader(this.file));
			}
			String content = "";
			String line;
			while( (line = this.reader.readLine()) != null ) {
				content += line;
				content += "\n";
				
			}
			return content;
			
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Only does a line by line read
	public String readLine() {
		try {
		//Check if writer open and close it
			if( this.writer != null) {
				this.writer.close();
				this.writer = null;
			}
			
			//Load in the reader
			if( this.reader == null) {
					this.reader = new BufferedReader(new FileReader(this.file));
			}
			
			//Read a line from the file
			String line = this.reader.readLine();
			
			//Close the file if it gets null, will read from the start next time
			if(line == null) {
				this.reader.close();
				this.reader = null;
			}
			
			return line;
		}catch(IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public boolean write(String input) {
		try {
			if( this.reader != null) {
				this.reader.close();
				this.reader = null;
				
			}
			if( this.writer != null) {
				this.writer.close();
			}
			this.writer = new BufferedWriter(new FileWriter(this.file, false)); //false overwrites
			
			
			writer.write(input);
			
			return true;
			
		}catch(IOException e) {
			e.printStackTrace();
			
			return false;
		}	
	}
	
	public boolean writeln(String input) {
		try {
			if(this.reader != null) {
				this.reader.close();
				this.reader = null;
			}
			if( this.writer == null) {
				this.writer = new BufferedWriter(new FileWriter(this.file, false));
			}
			
			this.writer.append(input + "\n");
			
			return true;
			
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void delete() {
		if( this.file != null) {
			this.file.delete();
		}
	}
	
}
