package open.cowin;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class AppScheduler {

	private static final Logger logger = Logger.getLogger(AppScheduler.class);
	
	public static void main(String args[]) {
		TimerTask timerTask = new App();
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 20*1000);
        logger.debug("Logger init");
        logger.debug("TimerTask started");
    }
}
