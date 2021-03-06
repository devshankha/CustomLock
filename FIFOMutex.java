


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

//This is an implementation of FifoMutex, the new thing is
//LockSupport.park and unpark is used. Code taken from below link
// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/LockSupp//ort.html
//

class FIFOMutex {
	   private final AtomicBoolean locked = new AtomicBoolean(false);
	   private final Queue<Thread> waiters
	     = new ConcurrentLinkedQueue<Thread>();

	   public void lock() {
	     boolean wasInterrupted = false;
	     Thread current = Thread.currentThread();
	     waiters.add(current);

	     // Block while not first in queue or cannot acquire lock
	     while (waiters.peek() != current ||
	            !locked.compareAndSet(false, true)) {
	        LockSupport.park(this);
	        if (Thread.interrupted()) // ignore interrupts while waiting
	          wasInterrupted = true;
	     }

	     waiters.remove();
	     if (wasInterrupted)          // reassert interrupt status on exit
	        current.interrupt();
	   }

	   public void unlock() {
	     locked.set(false);
	     LockSupport.unpark(waiters.peek());
	   }
	 }
