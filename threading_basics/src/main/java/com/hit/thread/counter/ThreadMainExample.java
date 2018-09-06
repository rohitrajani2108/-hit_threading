package com.hit.thread.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class ThreadMainExample {

	public static void main(String[] args)  {

		ThreadMainExample threadMain = new ThreadMainExample();

		////////////// adder example
//		 LongAdder adder = null;
//		 try {
//		 adder = threadMain.longAdderExample();
//		 } catch (InterruptedException e) {
//		 e.printStackTrace();
//		 }
//		
//		 System.out.println(adder.sum());
		

		///////////////// volatile example

		// new ChangeListener().start();
		// new ChangeMaker().start();

		//////////////// Even Odd example
	 // threadMain.new OddNumberPrinter().start();
		// threadMain.new EvenNumberPrinter().start();
			String hello = "hello";
		    for(int i=0 ; i<2; i++) {
		    	 logNotificationStatsInInterval(2, hello + i);
		    }
		    hello = "night";
		    
		    
		    
	}
	
	public static void  logNotificationStatsInInterval(long intervalTime, final String message){
		Runnable runnable = new Runnable() {
			int preCount = 0;
			int count = 0;
		      public void run() {
		        // task to run goes here
		    	  System.out.println(message);
		      }
		    };
		    ScheduledExecutorService service = Executors
		                    .newSingleThreadScheduledExecutor();
		    service.scheduleAtFixedRate(runnable, 0, intervalTime, TimeUnit.SECONDS);
	}
	
	class Count implements Runnable {
		int  count;
		
		public int getCount() {
			return count;
		}
		
	    public void run() {
	    	while(true) {
	    		
	    		++count;
	    		try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    }

	}
	public LongAdder longAdderExample() throws InterruptedException {
		LongAdder counter = new LongAdder();

		ExecutorService executorService = Executors.newFixedThreadPool(8);

		int numberOfThreads = 4;
		int numberOfIncrements = 100;

		Runnable incrementAction = () -> IntStream.range(0, numberOfIncrements).forEach(i -> counter.increment());

		for (int i = 0; i < numberOfThreads; i++) {
			executorService.execute(incrementAction);
		}

		//Thread.sleep(10);
		//executorService.shutdown();
		return counter;

	}
	
	
	public static void timeMili(){
		boolean isleft = true;
		boolean isright = true;
		
		for(int i=0; i < 200000 ; i++) {
			if(isleft != isright) {
				System.out.println("hurray ");
			}else {
				System.out.println("no wayyyy ");
		}
		}
		
	}

	/*volatile variable example*/
	private static volatile int MY_INT = 0;

	static class ChangeListener extends Thread {
		@Override
		public void run() {
			int local_value = MY_INT;
			while (local_value < 5) {
				if (local_value != MY_INT) {
					System.out.println("Got Change for MY_INT : " + MY_INT);
					local_value = MY_INT;
				}
				// System.out.println("running");
			}
		}
	}

	static class ChangeMaker extends Thread {
		@Override
		public void run() {

			int local_value = MY_INT;
			while (MY_INT < 5) {
				int add = local_value + 1;
				System.out.println("Incrementing MY_INT to " + add);
				MY_INT = ++local_value;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*EVEN ODD number print*/
	
	private  int MY_NUMBER = 1;
	 class OddNumberPrinter extends Thread {
		@Override
		public void run() {
			while (true) {
				if (MY_NUMBER %2 == 1) {
					System.out.println("Got OddNumberPrinter for MY_INT : " + MY_NUMBER);
					MY_NUMBER = MY_NUMBER + 1;
					//System.out.println(MY_NUMBER);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	 class EvenNumberPrinter extends Thread {
		@Override
		public void run() {
			while (true) {
				if(MY_NUMBER % 2 == 0) {
					System.out.println("Got EvenNumberPrinter for MY_INT >>>> : " + MY_NUMBER);
					MY_NUMBER = MY_NUMBER + 1;
					//System.out.println(MY_NUMBER);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	 
	 
	 
	 /**
	  * Fair lock on thread for avoiding Nested monitor lock, slipped condition & Missed signal
	  * @author rrajani
	  *
	  */
	 
	public class FairLock {
		private boolean isLocked = false;
		private Thread lockingThread = null;
		private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

		public void lock() throws InterruptedException {
			QueueObject queueObject = new QueueObject();
			boolean isLockedForThisThread = true;
			synchronized (this) {
				waitingThreads.add(queueObject);
			}

			while (isLockedForThisThread) {
				synchronized (this) {
					isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
					if (!isLockedForThisThread) {
						isLocked = true;
						waitingThreads.remove(queueObject);
						lockingThread = Thread.currentThread();
						return;
					}
				}
				try {
					queueObject.doWait();
				} catch (InterruptedException e) {
					synchronized (this) {
						waitingThreads.remove(queueObject);
					}
					throw e;
				}
			}
		}

		public synchronized void unlock() {
			if (this.lockingThread != Thread.currentThread()) {
				throw new IllegalMonitorStateException("Calling thread has not locked this lock");
			}
			isLocked = false;
			lockingThread = null;
			if (waitingThreads.size() > 0) {
				waitingThreads.get(0).doNotify();
			}
		}
	}

	public class QueueObject {

		private boolean isNotified = false;

		public synchronized void doWait() throws InterruptedException {
			while (!isNotified) {
				this.wait();
			}
			this.isNotified = false;
		}

		public synchronized void doNotify() {
			this.isNotified = true;
			this.notify();
		}

		public boolean equals(Object o) {
			return this == o;
		}
	}

	
	

	public int printCurlyParanthesis(int n) {
		int i, j;
		for (i = n; i > 0; i--) {
			for (j = 0; j < i; j++) {
				System.out.print("{");
			}
			for (j = 0; j < i; j++) {
				System.out.print("}");
			}
			printCurlyParanthesis(n - i);
			System.out.println("");
		}
		return 0;
	}

	
	
	static class Check12 extends Thread {
		
		@Override
		public void run() {
			synchronized (this) {
				
			}
		}
	}
	
	
}
