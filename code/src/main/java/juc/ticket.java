package juc;


/*
*
* 先创建一个资源类
*
*
* */
/*
*资源类i = 实例变量 加 实例方法
*
*
*
* */


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@FunctionalInterface
//隐式的加了这个注解
//
interface Foo{
    //public void saynthing();
    public int add(int x,int y);

    public default int mul(int x,int y){
        return x * y;
    }

    public default int mul2(int x,int y){
        return x/2;
    }

    public static int div(int x, int y){
        return x%y;
    }
    public static int div2(int x, int y){
        return x%y;
    }
    /*
    *
    * 语法想现象 default
    * */
}
public class ticket {
    static class Ticket{
        public Ticket(){

        }
        private int number = 30000;
        //    List list = new ArrayList();
        Lock lock = new ReentrantLock();

        public  void saleOne(){
            lock.lock();
            try {
                if(number>0){
                    System.out.println(Thread.currentThread().getName()+"\t卖出第"+number--+"\t还剩下"+number);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }
    public static void main(String[] args) { //主线程，一切程序的入口

        Ticket tickett = new Ticket();

        new Thread(()->{ for (int i = 0; i < 40000; i++) tickett.saleOne(); },"A").start();
//
//        new Thread(()->{ for (int i = 0; i < 40000; i++) tickett.saleOne(); },"B").start();
//        new Thread(()->{ for (int i = 0; i < 40000; i++) tickett.saleOne(); },"C").start();
//        函数式编程
//        函数式编程

//        Foo f = new Foo(){
//
//            @Override
//            public void saynthing() {
//
//            }
//
//            @Override
//            public int add(int x , int y) {
//                return x+y;
//            }
//        };

//        Foo foo = (x,y)->{ return x+y; };
//        System.out.println(foo.add(1,5));
//        System.out.println(foo.mul(4,5));
//        Foo.div(4,7);

       new Thread(new Runnable() {

           @Override
           public void run() {

           }
       },"A").start();

       new Thread(()->{ tickett.saleOne(); },"B").start();




    }

}
