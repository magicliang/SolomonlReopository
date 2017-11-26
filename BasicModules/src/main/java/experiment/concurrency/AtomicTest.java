package experiment.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicTest {

    private Integer lock = new Integer(0);

    private Integer counter = 0;

    public static void main(String[] args) {
        final ExecutorService exService = Executors.newFixedThreadPool(2);//newSingleThreadExecutor();
        AtomicTest test = new AtomicTest();

        exService.submit(test.new Task());
        // 因为多了一行代码，上面的代码即使加了锁，也是会出现意料之外的行为的。
        exService.submit(test.new Task());
    }

    public class Task implements Runnable {
        @Override
        public void run() {
            /**
             *  这个地方为什么不能用 counter做 lock，而必须使用其他lock。因为在里面对 counter 做了++操作，实际上counter 的
             *  引用和状态都发生了变化。要记住 integer 是不可变对象。我不太明白到底为什么这个地方wait没有报错引发程序员的警觉。
             */
            synchronized (lock) {
                System.out.println("Thread number is: " + Thread.currentThread().getId() + ", counter is: " + counter);
                counter++;
                try {
                    /**
                     * wait 会先破坏掉可重入性，也就是最终破坏掉了原子性。wait是 synchronized 的缺口。切换掉可以引用非栈封闭的
                     * 变量的函数上下文，就不是栈封闭的了。
                     */
                    lock.wait(1);//正常情况下这种计时等候都可以自动解开，不需要notify，否则可能引起waiting而不是time_waiting。这是预料不到的bug。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread number is: " + Thread.currentThread().getId() + ", counter is: " + counter);
            }
        }
    }
}
