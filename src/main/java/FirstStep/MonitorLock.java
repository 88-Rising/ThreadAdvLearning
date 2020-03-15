package FirstStep;

public class MonitorLock {
    /*
    * synchronized实现原理：
    *   mointor机制：monitorenter，monitorexit还有一个计数器进入的时候+1出去的时候-1当为0的时候释放锁
    *
    * */
    private static int SUM;

    public static synchronized void increment(){
        SUM++;
    }

    public static synchronized void decrement(){
        SUM--;
    }

    public static void main(String[] args) {
        for(int i=0;i<20;i++){
            new Thread(new Runnable() {
                public void run() {
                    synchronized (MonitorLock.class){
                        increment();
                        decrement();
                    }
                }
            }).start();
        }
        System.out.println(SUM);
    }

}
