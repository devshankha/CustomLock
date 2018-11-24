import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyClass {
	int data;
	boolean isSet = false;
	Lock lock = new ReentrantLock();
	Condition valueSet = lock.newCondition();
	Condition valueNotSet = lock.newCondition();
	void put (int n) {
		lock.lock();
		try {
			while (isSet) {
				try {
					valueSet.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			data = n;
			isSet = true;
			valueNotSet.notify();
			
		} finally {
			lock.unlock();
			
		}
	}
	int get() {
		lock.lock();
		try {
			while (!isSet) {
				try {
					valueNotSet.await();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					
				}
			}
			isSet = false;
			valueSet.notify();
			return data;
			
		} finally {
			lock.unlock();
		}
		
	}
	

}
