
public class MyLock {
	private boolean isLocked= false;
	public synchronized boolean lock() throws InterruptedException {
		while (isLocked == false) {
			wait();
			
		}
		return true;
	}
	public synchronized void unlock() {
		isLocked = false;
		notify();
		
	}

}
