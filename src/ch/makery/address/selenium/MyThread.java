package ch.makery.address.selenium;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.parser.ParseException;

import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyThread extends Thread implements Runnable {
	
	//Main controller for reference
	private SupremeBotOverviewController controller;
	
	private Selenium sel;
	private Request req;
	private volatile boolean isRunning = true;
	public int tasksNo = keywordInfo.getKeywordInfo().getTotalTasks();
	public String mode = keywordInfo.getKeywordInfo().getMode();
	private String startTime = keywordInfo.getKeywordInfo().getStartTimer();
	private boolean hasRunStarted = keywordInfo.getKeywordInfo().getHasRunStarted();
	


	public MyThread(SupremeBotOverviewController passableController) {
		this.controller = passableController;
		//Initialize Testing[Selenium] and Request Thread
		sel = new Selenium(controller);
		req = new Request(controller);
		
	}
	

	public void killThread() {
		isRunning = false;
	}

	private class schedulerDispatch extends TimerTask {
		public void run() {
			if (mode.contains("Browser")) {
				while(isRunning == true) {
							try {
								sel.main(null);	
							} catch (InterruptedException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								killThread();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
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
	}
	
	public void run() {
		if (mode.contains("Browser")) {
			while(isRunning == true) {
						try {
							sel.main(null);	
						} catch (InterruptedException | IOException e) {
							e.printStackTrace();
							killThread();
						} catch (ParseException e) {
							e.printStackTrace();
						}
			}
		} else if (mode.contains("Request")) {
			while(isRunning) {
						try {
							req.main(null);
						} catch (IOException e) {
							e.printStackTrace();
							killThread();
						}				
			}
		}
	}
	
	public void main(String[] args) throws java.text.ParseException {
		//Start threads depending on the amount of tasks in the table
//		System.out.println(startTime);
		//If timer is explicitly set then use Start Timer variable or else just run tasks
		
		
		if (hasRunStarted == true){
			DateFormat year = new SimpleDateFormat("yyyy-MM-dd ");
			Date yearDate = new Date();
			System.out.println(year.format(yearDate)); //2016/11/16 12:08:43 -- TEST
			
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormatter.parse(year.format(yearDate) + startTime);
			
		
			for (int i =0 ; i < tasksNo; i++) {
				Timer timer = new Timer();
				timer.schedule(new schedulerDispatch(), date);
				controller.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Tasks - Waiting for countdown: " + date + "\n");
			}
		} else if (hasRunStarted == false){
			for (int i =0 ; i < tasksNo; i++) {
				Thread t1 = new MyThread(controller);
				t1.start();
				controller.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Tasks started \n");
			}
		
	}
}
}
