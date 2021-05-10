package open.cowin;

import java.util.Timer;
import java.util.TimerTask;

public class AppScheduler {

	public static void main(String args[]) {
		TimerTask timerTask = new App();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 60*1000);
        System.out.println("TimerTask started");
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.activeCount());
    }
}
