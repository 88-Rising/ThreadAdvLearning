class Sington{
    private static final Sington sington1=new Sington();

    private Sington(){

    }

    public static Sington getInstance(){
        return sington1;
    }

    private static volatile Sington sington2=null;
/*
* 执行双重检查是因为，如果多个线程同时了通过了第一次检查，并且其中一个线程首先通过了第二次检查并实例化了对象，那么剩余通过了第一次检查的线程就不会再去实例化对象。
*这样，除了初始化的时候会出现加锁的情况，后续的所有调用都会避免加锁而直接返回，解决了性能消耗的问题。
* */
    public static Sington getInstance2(){
        if(sington2==null){
            synchronized (Sington.class) {
                if(sington2==null) {
                    sington2 = new Sington();
                }
            }
        }
        return sington2;
    }

}
/*
* shutDowm()方法：当线程池调用该方法时,线程池的状态则立刻变成SHUTDOWN状态。
*           此时，则不能再往线程池中添加任何任务，否则将会抛出RejectedExecutionException异常。
*           但是，此时线程池不会立刻退出，直到添加到线程池中的任务都已经处理完成，才会退出。
* shutDownNow()方法：根据JDK文档描述，大致意思是：执行该方法，线程池的状态立刻变成STOP状态，并试图停止所有正在执行的线程，不再处理还在池队列中等待的任务，当然，它会返回那些未执行的任务。
它试图终止线程的方法是通过调用Thread.interrupt()方法来实现的，但是大家知道，这种方法的作用有限，如果线程中没有sleep 、wait、Condition、定时锁等应用, interrupt()方法是无法中断当前的线程的。
所以，ShutdownNow()并不代表线程池就一定立即就能退出，它可能必须要等待所有正在执行的任务都执行完成了才能退出。
* */

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
                        if(COUNT<10000) {//提高效率 如果有很多线程进来的话进行判断之后避免多个线程去竞争锁
                            synchronized (UnsafeThread.class) {
                                if (COUNT < 10000) {//因为第一次判断和COUNT自加是不能保证原子性的
                                    COUNT++;
                                }
                            }
                        }
                    }
                }
            }).start();
        }
        while(Thread.activeCount()>1){
            Thread.yield();
        }
    }
}
