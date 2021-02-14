package Infrastructure;

import java.util.Timer;
import java.util.TimerTask;
public class Timers {
	private int seconds = 5;
	
	private void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	private boolean finished;
	private int minutes = 0;

	

	public int getSeconds() {
		return seconds;
	}
		public void countDown(int startingSeconds) {
			setSeconds(startingSeconds);
			 final Timer timer = new Timer();
		        timer.scheduleAtFixedRate(new TimerTask() {
		            public void run() {
							System.out.println("Game begins in " + seconds);
							setSeconds(getSeconds() - 1);
							if (seconds <=  0) {
								timer.cancel();
								setFinished(true);
							}
		            }
		        }, 0, 1000);
		}
		public void countUp() {

			 final Timer timer = new Timer();
		        timer.scheduleAtFixedRate(new TimerTask() {
		            public void run() {
							setSeconds(getSeconds() + 1);
							if(RaceTrack.finished()) {
								timer.cancel();
							}
							if(seconds%60==0&&seconds!=0) {
								setMinutes(getMinutes() + 1);
								setSeconds(0);
							}
		            }
		        }, 0, 1000);
		}
		/**
		 * @return the minutes
		 */
		public int getMinutes() {
			return minutes;
		}
		/**
		 * @param minutes the minutes to set
		 */
		private void setMinutes(int minutes) {
			this.minutes = minutes;
		}
		public boolean isFinished() {
			return finished;
		}
		public void setFinished(boolean finished) {
			this.finished = finished;
		}

	}

