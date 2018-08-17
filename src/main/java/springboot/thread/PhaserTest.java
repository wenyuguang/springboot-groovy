package springboot.thread;

import java.util.concurrent.Phaser;

public class PhaserTest {

	public static class PhaserTest_1 {
	    public static void main(String[] args) {
	        Phaser phaser = new Phaser(5);
	        
	        for(int i = 0 ; i < 5 ; i++){
	            Task_01 task_01 = new Task_01(phaser);
	            Thread thread = new Thread(task_01, "PhaseTest_" + i);
	            thread.start();
	        }
	    }
	    
	    static class Task_01 implements Runnable{
	        private final Phaser phaser;
	        
	        public Task_01(Phaser phaser){
	            this.phaser = phaser;
	        }
	        
	        @Override
	        public void run() {
	            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
	            //等待其他任务执行完成
	            phaser.arriveAndAwaitAdvance();
	            System.out.println(Thread.currentThread().getName() + "继续执行任务...");
	        }
	    }
	}
	
	public static class PhaserTest_5 {
	    public static void main(String[] args) {
	        Phaser phaser = new Phaser(1);        //相当于CountDownLatch(1) 
	        
	        //五个子任务
	        for(int i = 0 ; i < 3 ; i++){
	            Task_05 task = new Task_05(phaser);
	            Thread thread = new Thread(task,"PhaseTest_" + i);
	            thread.start();
	        }
	        
	        try {
	            //等待3秒
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        phaser.arrive();        //countDownLatch.countDown()
	    }
	    
	    static class Task_05 implements Runnable{
	        private final Phaser phaser;
	        
	        Task_05(Phaser phaser){
	            this.phaser = phaser;
	        }
	        
	        @Override
	        public void run() {
	            phaser.awaitAdvance(phaser.getPhase());        //countDownLatch.await()
	            System.out.println(Thread.currentThread().getName() + "执行任务...");
	        }
	    }
	}
	
	public static class PhaserTest_3 {
	    public static void main(String[] args) {
	        Phaser phaser = new Phaser(3){
	            /**
	             * registeredParties:线程注册的数量
	             * phase:进入该方法的线程数，
	             */
	        	@Override
	             protected boolean onAdvance(int phase, int registeredParties) { 
	                 System.out.println("执行onAdvance方法.....;phase:" + phase + "registeredParties=" + registeredParties);
	                 return phase == 3; 
	             }
	        };
	        
	        for(int i = 0 ; i < 3 ; i++){
	            Task_03 task = new Task_03(phaser);
	            Thread thread = new Thread(task,"task_" + i);
	            thread.start();
	        }
	        while(!phaser.isTerminated()){
	            phaser.arriveAndAwaitAdvance();    //主线程一直等待
	        }
	        System.out.println("主线程任务已经结束....");
	    }
	    
	    static class Task_03 implements Runnable{
	        private final Phaser phaser;
	        
	        public Task_03(Phaser phaser){
	            this.phaser = phaser;
	        }
	        
	        @Override
	        public void run() {
	            do{
	                try {
	                    Thread.sleep(500);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                System.out.println(Thread.currentThread().getName() + "开始执行任务...");
	                phaser.arriveAndAwaitAdvance();
	            }while(!phaser.isTerminated());
	        }
	    }
	}
}
