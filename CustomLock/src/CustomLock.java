
interface  CustomLock {
	void lock() throws InterruptedException;
	void unLock();
	boolean tryLock();

}
class ReentrantLock implements CustomLock {
	int lockCount = 0;
	long currentThread;

	@Override
	public void lock() throws InterruptedException {
		if ((lockCount != 0 && Thread.currentThread().getId() == currentThread)) {
			lockCount++;
			return;
		}
		if (lockCount == 0) {
			lockCount++;
			currentThread = Thread.currentThread().getId();

		}
		if (lockCount != 0) {
			lockCount++;
			wait();
		}

	}

	@Override
	public void unLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}
	
}