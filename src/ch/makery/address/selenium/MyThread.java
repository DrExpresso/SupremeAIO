package ch.makery.address.selenium;

import java.io.IOException;

import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;

public class MyThread extends Thread implements Runnable {
	
	public final static MyThread thread = new MyThread();
	private Selenium sel;
	private Request req;
	private volatile boolean isRunning = true;
	private SupremeBotOverviewController controller =  new SupremeBotOverviewController();
	public int tasksNo = keywordInfo.getKeywordInfo().getTotalTasks();
	public String mode = keywordInfo.getKeywordInfo().getMode();
	
	public static MyThread getThread() {
		return thread;
	}
	
	public MyThread() {
		//Initialize Testing[Selenium] and Request Thread
		sel = new Selenium();
		req = new Request();
	}
	
	public void killThread() {
		isRunning = false;
	}

	public void run() {
		if (mode.contains("Browser")) {
			while(isRunning == true) {
						try {
							sel.main(null);	
						} catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							killThread();
						}
			}
		} else if (mode.contains("Request")) {
			while(isRunning) {
						try {
							req.main(null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							killThread();
						}				
			}
		}
	}
	
	public void main(String[] args) {
		//Start threads depending on the amount of tasks in the table
		for (int i =0 ; i < tasksNo; i++) {
			Thread t1 = new MyThread();
			t1.start();
		}

	}
}
