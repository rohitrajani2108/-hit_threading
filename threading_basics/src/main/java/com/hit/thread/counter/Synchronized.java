package com.hit.thread.counter;

public class Synchronized implements Counter
{
	private Object lock = new Object();
	
	private int counter;
	
	public long getCounter()
	{
		synchronized (lock)
		{
			return counter;
		}
	}
	
	public void increment() 
	{
		synchronized (lock)
		{
			++counter;
		}
	}
}
