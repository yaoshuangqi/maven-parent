package com.quanroon.ysq.bloom_filter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 本地测试.google.guava中集成的布隆过滤器，根据实验可以看出：
 * 1.从元素角度看，当元素存在，则在布隆过滤器中一定存在，如果不存在，则在布隆过滤器中可能存在
 * 2.从容器角度看，当布隆过滤器中判断不存在，则元素一定不存在，如果判断存在，则可能不存在
 * @date 2020/8/19 20:24
 */
public class BloomFilterTest {

    //插入多少数据
    private static final int insertions = 1000000;

    //期望的误判率，如果你误判率设置的越小，那么布隆过滤器底层数据的容量numBits就需要越大，对cpu要求更高，因此根据实际情况而定，
    private static double fpp = 0.02;

    public static void main(String[] args) {

        //初始化一个存储string数据的布隆过滤器,默认误判率是0.03
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), insertions, fpp);

        //用于存放所有实际存在的key，用于是否存在
        Set<String> sets = new HashSet<String>(insertions);

        //用于存放所有实际存在的key，用于取出
        List<String> lists = new ArrayList<String>(insertions);

        //插入随机字符串
        for (int i = 0; i < insertions; i++) {
            String uuid = UUID.randomUUID().toString();
            bf.put(uuid);
            sets.add(uuid);
            lists.add(uuid);
        }

        int rightNum = 0;
        int wrongNum = 0;

        for (int i = 0; i < 10000; i++) {
            // 0-10000之间，可以被100整除的数有100个（100的倍数）
            String data = i % 100 == 0 ? lists.get(i / 100) : UUID.randomUUID().toString();

            //这里用了might,看上去不是很自信，所以如果布隆过滤器判断存在了,我们还要去sets中实锤
            if (bf.mightContain(data)) {
                if (sets.contains(data)) {
                    rightNum++;
                    continue;
                }
                wrongNum++;
            }
        }

        BigDecimal percent = new BigDecimal(wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        BigDecimal bingo = new BigDecimal(9900 - wrongNum).divide(new BigDecimal(9900), 2, RoundingMode.HALF_UP);
        System.out.println("在100W个元素中，判断100个实际存在的元素，布隆过滤器认为存在的：" + rightNum);
        System.out.println("在100W个元素中，判断9900个实际不存在的元素，误认为存在的：" + wrongNum + "，命中率：" + bingo + "，误判率：" + percent);
    }
}
