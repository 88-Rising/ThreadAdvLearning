public class UnsafeThread {

    public static volatile int COUNT;
/*
* 1.在线程较多的情况下，为保证线程安全的情况下synchronized锁性能会下降
* 2.如果同步代码块运行时间较长也会造成性能下降
*
* */
    public static void main(String[] args) {
        for(int i = 0;i<20;i++){
            new Thread(new Runnable() {
                public void run() {
                    for(int j=0;j<10000;j++){
                        if(COUNT<10000) {
                            synchronized (UnsafeThread.class) {
                                if (COUNT < 10000) {
                                    COUNT++;
                                }
                            }
                        }
                    }
                }
            }).start();
        }
    }
}
