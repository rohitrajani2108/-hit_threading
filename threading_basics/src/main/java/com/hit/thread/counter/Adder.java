package com.hit.thread.counter;

import java.util.concurrent.atomic.LongAdder;

public class Adder implements Counter
{
	private final LongAdder adder = new LongAdder();
	
	public long getCounter()
	{
		return adder.longValue();
	}
	
	public void increment()
	{
		adder.increment();
	}
}
