package com.quanroon.ysq.bubble_sort;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 冒泡排序改进版
 * 1.首先直到冒泡排序，不管已知数据是否存在顺序问题，我们都要执行n*(n-1)/2次
 * 2.设计一种程序，来改善次缺点，使用”岗哨“概念，可以提前中断程序
 * @date 2020/11/24 19:57
 */
public class BubbleSortImproveTest {

    //待排序数据 后三位已经是有序的了，就可以不用在遍历了
    int[] data = new int[]{4,6,2,7,8,9};


}
