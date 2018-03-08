package jvm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @since 2018/3/5 17:29
 * Description
 */
@RunWith(JUnit4.class)
public class BlockingQueueTest {

    @Test
    public void queueTest() {
        ArrayBlockingQueue<Integer> integerArrayBlockingQueue
                = new ArrayBlockingQueue<>(5);
        integerArrayBlockingQueue.offer(5);
        integerArrayBlockingQueue.offer(5);
        integerArrayBlockingQueue.offer(5);
        integerArrayBlockingQueue.offer(5);
        integerArrayBlockingQueue.offer(5);
        integerArrayBlockingQueue.offer(6);

        assert 5 == integerArrayBlockingQueue.peek();
    }

}
