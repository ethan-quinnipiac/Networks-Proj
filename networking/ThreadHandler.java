package networking;

/**Created by Kyle Macdonald
 * Creates the threads necessary for running the different parts of the application layer
 */


//Threads class
class ThreadApp implements Runnable {
    private int threadNum;

    public ThreadApp(int threadNum) {
        this.threadNum = threadNum;
    }

    //assigns the thread to whatever function based on threadNum
    public void run() {
        if (threadNum == 1) {
            threadA();
        } else if (threadNum == 2) {
            threadB();
        } else if (threadNum == 3) {
            threadC();
        } else if (threadNum == 4) {
            threadD();
        }
    }


    //TODO ASSIGN THE DIFFERENT THREADS TO APP LAYER FUNCTIONALITY
    private void threadA() {
        System.out.println("Thread A is running in Thread: " + Thread.currentThread().getName());
    }

    private void threadB() {
        System.out.println("Thread B is running in Thread: " + Thread.currentThread().getName());
    }

    private void threadC() {
        System.out.println("Thread C is running in Thread: " + Thread.currentThread().getName());
    }

    private void threadD() {
        System.out.println("Thread D is running in Thread: " + Thread.currentThread().getName());
    }
}

//USE THIS WHEN CREATING THE THREADS IN MAIN
public class ThreadHandler {
    public static void createThreads() {
        Thread[] threads = new Thread[4];

        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new ThreadApp(i + 1));
            threads[i].start();
        }
    }
}
