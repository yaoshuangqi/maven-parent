package com.quanroon.ysq.stream;

import com.google.common.collect.Lists;
import com.quanroon.ysq.vo.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 使用jdk1.8之stream进行操作
 * @createtime 2020/7/17 20:45
 */
public class Jdk18StreamTest {

    static ArrayList<User> arrayList = Lists.newArrayList();
    public static void test(){
        //采用自定义的构造器，初始化字段一定不能为null,请看实体类
        arrayList.add(User.defineOf("张三", 11, "女"));
        arrayList.add(User.defineOf("李四", 23, "女"));
        arrayList.add(User.defineOf("王五", 11, "男"));
        arrayList.add(User.defineOf("赵六", 18, "女"));

        //分组
        Map<String, List<User>> collect = arrayList.stream().collect(Collectors.groupingBy(User::getSex));

        collect.forEach((key, userList) ->{
            System.out.println(key + ":"+ userList.toString());
        });
        System.out.println("==========================================");

        //排序
        List<User> collect1 = arrayList.stream().sorted(Comparator.comparing(user -> user.getAge())).collect(Collectors.toList());
        collect1.forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================================");

        //过滤
        arrayList.stream().filter(user -> user.getSex().equals("男"))
                .forEach(user -> System.out.println(user.toString()));
        System.out.println("==========================================");
        //多条件去重
        arrayList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(user -> user.getAge() + ";" + user.getName()))), ArrayList::new))
                .forEach(user -> System.out.println(user.getName()));
        System.out.println("==========================================");
        //最小值
        Integer min = arrayList.stream().mapToInt(User::getAge).min().getAsInt();
        System.out.println("==========================================");
        //最大值
        Integer max = arrayList.stream().mapToInt(User::getAge).max().getAsInt();
        System.out.println("==========================================");
        //平均值
        Double average = arrayList.stream().mapToInt(User::getAge).average().getAsDouble();
        System.out.println("==========================================");
        //和
        Integer sum = arrayList.stream().mapToInt(User::getAge).sum();
        System.out.println("最小值:"+min+", 最大值"+max+", 平均值:"+average+", 和:"+sum);
        System.out.println("==========================================");
        //分组求和
        Map<String, IntSummaryStatistics> statisticsMap = arrayList.stream().collect(Collectors.groupingBy(User::getSex, Collectors.summarizingInt(User::getAge)));
        IntSummaryStatistics statistics1 = statisticsMap.get("男");
        IntSummaryStatistics statistics2 = statisticsMap.get("女");
        System.out.println(statistics1.getSum());
        System.out.println(statistics1.getAverage());
        System.out.println(statistics1.getMax());
        System.out.println(statistics1.getMin());
        System.out.println(statistics1.getCount());
        System.out.println(statistics2.getSum());
        System.out.println(statistics2.getAverage());
        System.out.println(statistics2.getMax());
        System.out.println(statistics2.getMin());
        System.out.println(statistics2.getCount());
        System.out.println("==========================================");
        //提取list中两个属性值，转为map
        Map<String, String> userMap = arrayList.stream().collect(Collectors.toMap(User::getName, User::getSex));
        System.out.println(userMap.toString());
        System.out.println("==========================================");
        //取出所有名字
        List<String> names = arrayList.stream().map(User::getName).collect(Collectors.toList());
        System.out.println(names.toString());

    }

    public static void main(String[] args) {
        test();
    }
}
