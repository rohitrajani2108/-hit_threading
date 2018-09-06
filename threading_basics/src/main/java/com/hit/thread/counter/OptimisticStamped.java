package com.hit.thread.counter;

import java.util.concurrent.locks.StampedLock;

public class OptimisticStamped implements Counter {

	private StampedLock rwlock = new StampedLock();
	
	private long counter;
	private long success;

	private long total;
	
	
	public long getCounter()
	{
	    total++;

	    long stamp = rwlock.tryOptimisticRead();
	    long value = counter;
	    if (rwlock.validate(stamp)) {
	        success++;
	        return value;
	    } else {
	        // the value is dirty, repeat the optimistic read, or fallback to a full read lock
	    }
		return value;
	}
	
	public void increment()
	{
		long stamp = rwlock.writeLock();
		
		try
		{	
			++counter;
		}
		finally
		{
			rwlock.unlockWrite(stamp);
		}
	}

	public long getSuccess() {
		return success;
	}
	

	public long getTotal() {
		return total;
	}
}
