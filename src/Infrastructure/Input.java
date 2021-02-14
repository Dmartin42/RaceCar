package Infrastructure;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Input extends KeyAdapter {
	public Car raceCar1 = RaceTrack.player1;
	public Car raceCar2 = RaceTrack.player2;
	public static boolean time = true;
	public List<Integer> pressedKeys = new ArrayList<>();
	public static int key;
	private final double ACCELERATION = 5 ,ROTATION = 20; 


	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		super.keyPressed(k);
		key = k.getKeyCode();
		// System.out.println("Velocity:"+RaceTrack.raceCar.getVelocity());
		if (key == (KeyEvent.VK_ESCAPE)) {
			Runner.frame.dispose();
			int currentMin = Calendar.getInstance().get(Calendar.MINUTE),
					currentSec = Calendar.getInstance().get(Calendar.SECOND),
					currentHour = Calendar.getInstance().get(Calendar.HOUR);
			String timeLasted = "" + String.valueOf(currentHour - Runner.hourStart) + ":"
					+ String.valueOf(currentMin - Runner.minStart) + ":" + String.valueOf( currentSec - Runner.secStart );
			System.out.println(
					"---------Program Terminated " + Calendar.getInstance().getTime() + " [" + timeLasted + "]");
		
			System.exit(0);
			
			
		} else if (!pressedKeys.contains(key)){
			pressedKeys.add(key);

		}
		

	}

	public void keyReleased(KeyEvent r) {
		super.keyReleased(r);
		pressedKeys.remove(Integer.valueOf(r.getKeyCode()));
		raceCar1.setAcc(0);
		raceCar2.setAcc(0);
	}

	/** allows for multiple keys to be pressed simultaneously 
	 * 
	 */
	public void keyActions() {
		if (pressedKeys.contains(KeyEvent.VK_W)) {
			raceCar1.setAcc(ACCELERATION);
		}

		if (pressedKeys.contains(KeyEvent.VK_D)) {
			raceCar1.setRotation(raceCar1.getRotation() + ROTATION);	
			raceCar1.rotation(ROTATION);
		}

		if (pressedKeys.contains(KeyEvent.VK_A)) { 
			raceCar1.setRotation(raceCar1.getRotation()- ROTATION);
			raceCar1.rotation(-ROTATION);
		}

		if (pressedKeys.contains(KeyEvent.VK_S)) //Reverse
		{
			raceCar1.setAcc(-ACCELERATION);
		}

		if (pressedKeys.contains(KeyEvent.VK_X)) {
			raceCar1.setVelY(0);
			raceCar1.setVelX(0);
		}

		if(pressedKeys.contains(KeyEvent.VK_UP)) {
			raceCar2.setAcc(ACCELERATION);
		}
		if(pressedKeys.contains(KeyEvent.VK_DOWN)) {
			raceCar2.setAcc(-ACCELERATION);
		}
		if(pressedKeys.contains(KeyEvent.VK_LEFT)) {
			raceCar2.setRotation(raceCar2.getRotation()-ROTATION);
			raceCar2.rotation(-ROTATION);
		}
		if(pressedKeys.contains(KeyEvent.VK_RIGHT)) {
			raceCar2.setRotation(raceCar2.getRotation()+ROTATION);
			raceCar2.rotation(ROTATION);
		}
		
		
	
	}

}
