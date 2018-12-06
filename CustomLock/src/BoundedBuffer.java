/**
  * A bounded buffer of slots.
  * Designed for multiple producer threads and multiple consumer threads.
  * A consumer blocks if the bounded buffer is empty until a slot is
  * filled by a producer.
  * A producer blocks if the bounded buffer is full until a slot is
  * emptied by a producer.
  *
 */



import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	String arr[];
	int startIndex;
	int endIndex;
	int count;
	int MAXSIZE;
	Lock lock = new ReentrantLock();
	Condition full = lock.newCondition();
	Condition empty = lock.newCondition();

	BoundedBuffer(int s) {
		arr = new String[s];
		startIndex = 0;
		count = 0;
		endIndex = -1;
		MAXSIZE = s;

	}

	public void insert(String s) {
		lock.lock();
		try {
			while (count == MAXSIZE)
				try {
					full.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			arr[startIndex] = s;
			startIndex = (startIndex + 1) % MAXSIZE;
			count++;
			empty.signal();
		} finally {
			lock.unlock();
		}

	}

	public String remove(String s) {
		lock.lock();
		try {
			while (count == 0)
				try {
					empty.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			endIndex = (endIndex + 1) % MAXSIZE;
			String val = arr[endIndex];
			full.signal();
			return val;
		} finally {
			lock.unlock();
		}

	}

}
