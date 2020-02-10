package gesture;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import gesture.Gesture;

public class Recognition {

	int sustainReq = 1; //number of ticks required where data matches requirement for action to occur
	int currentReq = 0; //current progress in action in ticks
	
	static final int minDelay = 1000; // milliseconds between actions minimum
	long timestamp = System.currentTimeMillis();
	
	int currentGesture = -1; //index in arraylist of current gesture
	ArrayList<Gesture> gestures;
	
	boolean hasGesture = false;
	
	public Recognition() {
		gestures = new ArrayList<>();
		gestures.add(GestureMap.upLeftTranslation);
		gestures.add(GestureMap.upRightTranslation);
	}
	
	public Recognition(ArrayList<Gesture> al) {
		gestures = al; // direct reference
	}
	
	public int checkAction(int x, int y, int z) {
		if(System.currentTimeMillis() < (timestamp + minDelay)) {
			System.out.println("LIMIT BLOCKED");
			return -1;
		}
		
		for(int i = 0; i < gestures.size(); i++) { // Gesture recognition
			Gesture g = gestures.get(i);
			
			if( // current gesture is confirmed
					x < (g.targX + g.xRange) && x > (g.targX - g.xRange) &&
					y < (g.targY + g.yRange) && y > (g.targY - g.yRange) &&
					z < (g.targZ + g.zRange) && z > (g.targZ - g.zRange)
					) {
				if(currentReq != 0) { // already a gesture happening
					if(currentGesture != i) {// two different gestures
						currentGesture = i;
						currentReq = 0;
					}
				}
				currentGesture = i;
				currentReq++;
				hasGesture = true;
			}
		}
		if(hasGesture == false) {//no gesture found 
			currentReq = 0;
			currentGesture = -1;
		}
		
		if(currentReq == sustainReq) {
			timestamp = System.currentTimeMillis();
			currentReq = 0;
			return gestures.get(currentGesture).action;
		}
		return -1;
	}
}
