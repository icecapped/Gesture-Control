package gesture;

import java.awt.event.KeyEvent;
import java.awt.Robot;
public class Gesture {

	protected int targX;
	protected int targY;
	protected int targZ;
	protected int xRange;
	protected int yRange;
	protected int zRange;
	
	int action; // KeyEvent constant
	
	Gesture(int targX, int targY, int targZ, int action){
		this.targX = targX;
		this.targY = targY;
		this.targZ = targZ;
		this.action = action;
		
		xRange = 5000;
		yRange = 5000;
		zRange = 5000;
	}
	
	Gesture(int targX, int targY, int targZ, int action, int xRange, int yRange, int zRange){
		this.targX = targX;
		this.targY = targY;
		this.targZ = targZ;
		this.action = action;
		
		this.xRange = xRange;
		this.yRange = yRange;
		this.zRange = zRange;
	}
}
