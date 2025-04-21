// 双线程交替打印1-100（奇偶分开）

public class Main {
    private static int number = 1;
    private static final int MAX = 100;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number > MAX) {
                        lock.notifyAll(); // 防止另一个线程死锁
                        break;
                    }
                    if (number % 2 == 1) {
                        System.out.println("奇数线程打印: " + number);
                        number++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        Thread evenThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number > MAX) {
                        lock.notifyAll(); // 防止另一个线程死锁
                        break;
                    }
                    if (number % 2 == 0) {
                        System.out.println("偶数线程打印: " + number);
                        number++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        });

        oddThread.start();
        evenThread.start();
    }
}
