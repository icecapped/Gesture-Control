package gesture;
import java.awt.event.KeyEvent;

import gesture.Gesture;
public class GestureMap {

	static final Gesture upRightTranslation = new Gesture(8000, 0, 16000, KeyEvent.VK_RIGHT, 3000, 5000, 5000);
	static final Gesture upLeftTranslation = new Gesture(-8000, 0, 16000, KeyEvent.VK_LEFT, 3000, 5000, 5000);
	static final Gesture upForwardTranslation = new Gesture(0, -8000, 16000, KeyEvent.VK_UP, 5000, 3000, 5000);
	static final Gesture upBackwardTranslation = new Gesture(0, 8000, 16000, KeyEvent.VK_DOWN, 5000, 3000, 5000);
}
