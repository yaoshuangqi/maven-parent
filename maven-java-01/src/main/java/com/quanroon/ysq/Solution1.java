package com.quanroon.ysq;

import java.util.Arrays;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/11/7 23:10
 */
public class Solution1 {

    public static void main(String[] args) {
        int[] nums = new int[]{3,30,34,5,9};

        Integer [] x  =  Arrays.stream(nums).boxed().toArray(Integer[]::new);

        new Solution1().largestNumber(nums);





    }

    public String largestNumber(int[] nums) {
        // Get input integers as strings.
        String[] asStrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            asStrs[i] = String.valueOf(nums[i]);
        }

//        // Sort strings according to custom comparator.
//        Arrays.sort(asStrs, new LargerNumberComparator());
//
//        // If, after being sorted, the largest number is `0`, the entire number
//        // is zero.
//        if (asStrs[0].equals("0")) {
//            return "0";
//        }
//
//        // Build largest number from sorted array.
//        String largestNumberStr = new String();
//        for (String numAsStr : asStrs) {
//            largestNumberStr += numAsStr;
//        }
//
//        return largestNumberStr;

        return "";
    }
}
