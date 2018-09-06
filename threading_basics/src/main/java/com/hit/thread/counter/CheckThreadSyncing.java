package com.hit.thread.counter;

public class CheckThreadSyncing extends Thread {
	
	String name = null;
		
	    public CheckThreadSyncing(String name) {
	        this.name  = name;
	    }

	    @Override
	    public void run() {
	    	synchronized (name) {
	    		System.out.println("1");
	    		name.notifyAll();
	  	        System.out.println("2");
			}
	      
	    }
	    
	    
	    public static void main(String[] args){
	    	Thread t1 = new CheckThreadSyncing("t1");
		    Thread t2 = new CheckThreadSyncing("t2");
		    Thread t3 = new CheckThreadSyncing("t3");
		    Thread t4 = new CheckThreadSyncing("t4");
		    Thread t5 = new CheckThreadSyncing("t5");
	        t1.start();
	        t2.start();
	        t3.start();
	        t4.start();
	        t5.start();
	    }
}
