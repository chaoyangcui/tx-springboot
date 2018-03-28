package deadlock;

/**
 * Created by Intellij IDEA.
 *
 * @author Eric Cui
 * @since 2018/3/26 23:36
 */
public class DeadLockTest {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        DeadLockTest deadLockTest = new DeadLockTest();
        new Thread(deadLockTest::methodA, "Thread-A").start();
        new Thread(deadLockTest::methodB, "Thread-B").start();
    }

    private void methodA() {
        Thread currentThread = Thread.currentThread();
        synchronized (lock1) {
            System.out.println(currentThread.getName() + " get lock1");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignore) {}
            synchronized (lock2) {
                System.out.println(currentThread.getName() + " get lock2");
            }
        }
    }

    private void methodB() {
        Thread currentThread = Thread.currentThread();
        synchronized (lock2) {
            System.out.println(currentThread.getName() + " get lock2");
            synchronized (lock1) {
                System.out.println(currentThread.getName() + " get lock1");
            }
        }
    }
}
