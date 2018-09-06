package com.hit.thread.counter;

public class Volatile implements Counter
{
	private volatile long counter;
	
	public long getCounter()
	{
		return counter;
	}
	
	public void increment() 
	{
		++counter;
	}
}