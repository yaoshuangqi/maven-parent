package com.quanroon.ysq;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        ConcurrentHashMap hashMap = new ConcurrentHashMap();
        //对上述数据输出一个满二叉树
        //原始数组
        int data[] = {6,3,5,9,7,8,4,2};
        //存储满二叉树的数据，设置为16
        int btree[] = new int[16];
        int level;
        for (int i=0; i<8; i++){
            for (level = 1; btree[level] != 0;){
                if(data[i] > btree[level])
                    level = level*2+1;
                else
                    level = level*2;
            }
            btree[level]=data[i];
        }
        //输出满二叉树 0 6 3 9 2 5 7 0 0 0 4 0 0 8 0 0
        Arrays.stream(btree).forEach( ii -> System.out.print(ii+" "));
    }
}
