class Producer extends Thread {
	 public Producer(MyQueue queue) {
		super();
		this.queue = queue;
		this.start();
	}


	MyQueue queue ;
	 
	
	public void run() {
		for (int i=0 ; i <=5;i++) {
			
				//System.out.println("In Producer loop with val "+i);
				queue.set(i);
				//Thread.sleep(900);
			
		}
		
	}
}
class Consumer extends Thread {
	MyQueue queue ;
	 public Consumer(MyQueue queue) {
			super();
			this.queue = queue;
			this.start();
		}
	 
	
	public void run() {
		for (int i=0 ; i <=5;i++) {
			
				//System.out.println("In Consumer loop with val "+i);
				queue.get();
				//Thread.sleep(900);
			 
		}
		
	}
	
}
public class MyQueue {
	int myValue;
	 Semaphore semProduce = new Semaphore(1);
	 Semaphore semConsume = new Semaphore(0);
	public void set  (int n)  {
		//System.out.println("In SET trying to acquire semaphore");
		try {
		semProduce.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
		myValue = n;
		System.out.println("Setting the value "+myValue);
		semConsume.release();
		
		
	}
	public  int get()  {
		//System.out.println("In GET trying to acquire semaphore");
		try {
		semConsume.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int k = myValue;
		System.out.println("Reading the value "+myValue);
		semProduce.release();
		return k;
		
		
	}
	public static void main(String[] args) {
		MyQueue f = new MyQueue();
		
		Thread producer = new Producer(f);
		Thread consumer = new Consumer(f);
		
		//producer.start();
		//consumer.start();
		
	}



}
