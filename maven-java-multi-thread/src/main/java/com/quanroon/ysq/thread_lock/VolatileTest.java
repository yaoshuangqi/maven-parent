package com.quanroon.ysq.thread_lock;

import java.util.HashSet;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 相比Sychronized（重量级锁，对系统性能影响较大），volatile提供了另一种解决可见性和有序性问题的方案。
 * 对于原子性，需要强调一点，也是大家容易误解的一点：对volatile变量的单次读/写操作可以保证原子性的，
 * 如long和double类型变量，但是并不能保证i++这种操作的原子性，因为本质上i++是读、写两次操作
 * @createtime 2020/8/15 13:52
 */
public class VolatileTest {

    //volatile的使用
    //1、防重排序,这种情况，我们在单例模式下采用了双重检查加锁机制进行，如下

    //使用它必须满足如下两个条件：
    //1.对变量的写操作不依赖当前值；
    //2.该变量没有包含在具有其他变量的不变式中。

    //volatile经常用于两个两个场景：状态标记两、double check


    public static void main(String[] args) {
        HashSet hashSet = new HashSet();//对下面的str1,str2 hashSet只会存存一个，因为hashSet中在比较时，会去寻找对应的类是否重新了equal和hashCode的。对于String,肯定是重写了
        String str1 = "ABC";
        String str2 = new String("ABC");
        String str3 = new String("ABC");
        String str4 = "ABC1";
        System.out.println(str1.equals(str2) + "---");
        System.out.println(str1 == str2 + "---");
        System.out.println(str1.hashCode()+ "---" + str2.hashCode()+"===="+str3.hashCode());
        System.out.println(str2 == str3);

        hashSet.add(str1);
        hashSet.add(str2);
        hashSet.add(str3);

        System.out.println(hashSet.toString());
        //https://www.cnblogs.com/aspirant/p/9193112.html
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";
        System.out.println(s3 == s1 + s2);           // false  s1+s2 是先在常量池中开辟一个内存，然后将s1,s2地址拼接后，生成新地址，这样，跟s3地址肯定不同了
        System.out.println(s3 == "hello" + "world"); //true 先是直接拼接这两个常量，然后在常量池中找是否存在，存在，则给出地址。所以与s3地址相同
        /*总结
        String s = new String(“hello”)会创建2（1）个对象，String s = “hello”创建1（0）个对象。
        注：当字符串常量池中有对象hello时括号内成立！
        字符串如果是变量相加，先开空间，在拼接。
        字符串如果是常量相加，是先加，然后在常量池找，如果有就直接返回，否则，就创建。*/
    }
}

class Singleton{
    private static volatile Singleton singleton;
    /**
    * @description 构造函数私有，禁止外部实例化
    * @author quanroong.ysq
    * @createtime 2020/8/15 14:05
    * @version 1.0.0
    */
    private Singleton() {}

   /**
   * @description 并发环境下的单例实现方式，双重检查加锁获取单例
   * @author quanroong.ysq
   * @createtime 2020/8/15 14:04
   * @version 1.0.0
   */
    public Singleton getSingleton(){
        //为什么要进行双重检查，其主要目的是在对象实例化时，多线程环境下就可能将一个未初始化的对象引用暴露出来，从而导致不可预料的结果。
        // 因此，为了防止这个过程的重排序，我们需要将变量设置为volatile类型的变量
        if(singleton == null){
            synchronized (singleton){//多线程并发情况下
                if(singleton == null)
                    singleton = new Singleton();
            }
        }
        return singleton;
    }
}