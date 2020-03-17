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
