package com.hit.thread.counter;

public class Dirty implements Counter
{
	private long counter;
	
	public long getCounter()
	{
		return counter;
	}
	
	public void increment() 
	{
		++counter;
	}
}
