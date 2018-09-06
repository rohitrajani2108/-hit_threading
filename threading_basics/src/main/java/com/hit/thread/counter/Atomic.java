package com.hit.thread.counter;

import java.util.concurrent.atomic.AtomicLong;

public class Atomic implements Counter
{
	private final AtomicLong atomic = new AtomicLong();
	
	public long getCounter()
	{
		return atomic.get();
	}
	
	public void increment()
	{
		atomic.incrementAndGet();
	}
}
