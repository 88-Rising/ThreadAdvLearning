public class UnsafeThread {

    public static volatile int COUNT;

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
