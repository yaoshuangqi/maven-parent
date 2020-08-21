package com.quanroon.ysq.bloom_filter.base_redis;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 我们项目往往是分布式的，我们还可以把数据放在redis中，用redis来实现布隆过滤器，
 * 这就需要我们自己设计映射函数，自己度量二进制向量的长度
 * 模拟BloomFilter逻辑实现
 * @date 2020/8/20 15:05
 */
public class RedisToBloomFilterHelper<T> {

    /**元素进行hash函数算法次数*/
    private int numHashFunctions;
    /**布隆底层位数组大小*/
    private int bitSize;
    /**类型转换函数*/
    private Funnel<T> funnel;
    /**
    * @Description:
    * @Author: quanroon.yaosq
    * @Date: 2020/8/20 15:37
    * @Param: [expectedInsertions]期待添加多少数据
    * @Return:
    */
    public RedisToBloomFilterHelper(){}

    public RedisToBloomFilterHelper(int expectedInsertions) {
        this.funnel = (Funnel<T>) Funnels.stringFunnel(Charset.defaultCharset());
        bitSize = optimalNumOfBits(expectedInsertions, 0.03);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public RedisToBloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }
    /**
    * @Description: hash算法,与bit数组
    * @Author: quanroon.yaosq
    * @Date: 2020/8/20 20:32
    * @Param: [value]
    * @Return: int[]
    */
    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    public static void main(String[] args) {
        RedisToBloomFilterHelper<String> bloomFilterHelper = new RedisToBloomFilterHelper<String>(100);
        int[] ree33s = bloomFilterHelper.murmurHashOffset("ree33");
        System.out.println("count: " +ree33s.length +"====");
        Arrays.stream(ree33s).forEach(i -> System.out.println(i));
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }
}
