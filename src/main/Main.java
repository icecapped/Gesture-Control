package main;

import javax.microedition.io.*;

import gesture.Recognition;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.*;

public class Main {
	static final String DEVICE_URL = "btspp://98D331F7557E:1;authenticate=false;encrypt=false;master=false";
	
	static int[] rawValues = new int[6];
	static int inputRotation = 0; //0=X, 1=Y, 2=Z, 3=GX, 4=GY, 5=GZ, 
	static boolean commandState = false;
	static boolean completeInput = false;
	
	
	/*
	 * TEMPORARY CODE BEFORE GUI GETS FINISHED (probably)
	 */
	static Robot actor;
	static Recognition process = new Recognition();
	
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		StreamConnection stream = (StreamConnection) Connector.open(DEVICE_URL);
		OutputStream os = stream.openOutputStream();
		InputStream is = stream.openInputStream();
		
		String curInput = "TAG0";
		
		//TEMP PROBABLY
		actor = new Robot();
		
		//calibrating input
		while(!(curInput.charAt(curInput.length() - 1) == 'X')) {
			if(is.available() != 0) {
				curInput += (char)is.read();
				if(curInput.charAt(curInput.length() - 1) == 'G') {
					is.read(); // flush X following G to make sure doesnt calibrate to gyro
				}
			}
		}
		curInput = "";
		
		//execution loop
		while(true) {
			//Thread.sleep(10);
			
			//Parsing data from Bluetooth
			
			if(is.available() != 0) {
				
				curInput += (char)is.read();
				if(curInput.charAt(curInput.length() - 1) == '\n' || curInput.charAt(curInput.length() - 1) == 'G') {
					curInput = curInput.substring(0, curInput.length() - 1); //removing newline characters and G markers
				}
				
				//End of value marker found
				if(curInput.charAt(curInput.length() - 1) == 'X' ||
						curInput.charAt(curInput.length() - 1) == 'Y' ||
						curInput.charAt(curInput.length() - 1) == 'Z') {
					//parse value as integer
					rawValues[inputRotation] = Integer.parseInt(curInput.substring(0, curInput.length() - 1));
					
					//flush input and rotate input vector
					curInput = "";
					inputRotation++;
					if(inputRotation >= 6) { //Full data of given moment
						inputRotation = 0; //Reset rotation
						completeInput = true;
					}
				}
				//System.out.print((char)is.read()); //Debug (will consume half of characters if used with input processing)
			}
			
			//Complete set of input for processing
			if(completeInput) {
				completeInput = false;
				
				int action = process.checkAction(rawValues[0], rawValues[1], rawValues[2]);
				if(action != -1) { // no gesture found
					actor.keyPress(action);
					actor.keyRelease(action);
					System.out.println("ACTION: " + action);
				}
				/*
				 * TEMPORARY CODE BEFORE GUI GETS FINISHED (probably)
				 */
				
				
				System.out.println("COMPLETE VALUES - X: " + rawValues[0] + "Y: " + rawValues[1] + "Z: " + rawValues[2]);
				//System.out.println("GYRO VALUES - GX: " + rawValues[3] + "GY: " + rawValues[4] + "GZ: " + rawValues[5]);
			}
			
		}
		/*
		 * os.close();
		is.close();
		stream.close();
		*/
	}
	
	/**
	 * Returns index of gravity-aligned vector if valid command state
	 * (one vector aligned with gravity).
	 * Returns -1 if invalid command state.
	 */
	static int verifyAlignment(int[] values) {
		return -1;
		
	}
}
