
public class Semaphore {
	private int permits;

	public Semaphore(int permit) {
		
		this.permits = permit;
	}
	synchronized public void acquire()throws InterruptedException {
		while (permits == 0)
			wait();
		permits--;
		
	}
	synchronized public void release() {
		permits++;
		notify();
		
	}
	

}
