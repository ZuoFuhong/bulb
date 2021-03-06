package com.maxzuo.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Java位逻辑运算符（Java整数都有符号）
 * <p>
 * Created by zfh on 2019/08/07
 */
class BitwiseLogicalOperatorExample {

    @DisplayName("二进制转换二进制")
    @Test
    void testDecimalToBinary () {
        /*
           将10进制的数 8 转换为 2进制，计算过程如下：
                 |
               2 | 8      余数
                 |___
                 |
               2 | 4       0
                 |___
                 |
               2 | 2       0
                 |___
                 |
               2 | 1       0
                 |___

                   0       1

            结果：1000
         */
        String s = Integer.toBinaryString(8);
        System.out.println(s);
    }

    @DisplayName("二进制转10进制")
    @Test
    void testBinaryToDecimal () {
        /*
            将二进制数 1000 转换为 十进制，计算过程如下：

                       1                    0                    0                    0
                       |                    |                    |                    |
                       |                    |                    |                    |
                       |                    |                    |                    |
                       |                    |                    |                    |
               1 * Math.pow(2, 3) + 0 * Math.pow(2, 2) + 0 * Math.pow(2, 1) + 0 * Math.pow(2, 0)
         */
        double value = 1 * Math.pow(2, 3) + 0 * Math.pow(2, 2) + 0 * Math.pow(2, 1) + 0 * Math.pow(2, 0);
        System.out.println((int)value);
    }

    @DisplayName("左位移运算符")
    @Test
    void testLeftBitMoveOperator () {
        /*
            左移位运算符为 <<，其运算规则是：按二进制形式把所有的数字向左移动对应的位数，高位移出（舍弃），低位的空位补零。

                 移位前    0000 0000 0000 0000 0000 0000 0000 1001

                 移位后  0 0000 0000 0000 0000 0000 0000 0001 0010
                        |                                       |
                        |                                       |
                       舍弃           左移1位计算过程              补0
         */
        System.out.println(9 << 1);

        /*
               移位之前    1000 0000 0000 0000 0000 0000 0000 1001

               求其补码    1111 1111 1111 1111 1111 1111 1111 0111

               补码移位  1 1111 1111 1111 1111 1111 1111 1110 1110
                        |                                       |
                        |                                       |
                       舍弃           左移1位计算过程              补0

                 移位结果  1111 1111 1111 1111 1111 1111 1110 1110
                 求其反码  1111 1111 1111 1111 1111 1111 1110 1101
                 求其原码  1000 0000 0000 0000 0000 0000 0001 0010
         */
        System.out.println(-9 << 1);
    }

    @DisplayName("右位移运算符")
    @Test
    void testRightBigMoveOperator () {
        /*
            右位移运算符为 >>，其运算规则是：按二进制形式把所有的数字向右移动对应的位数，低位移出（舍弃），如果最高位是0就补0，如果最高位是1就补1。

                移位前     0000 0000 0000 0000 0000 0000 0000 1001

                移位后     0000 0000 0000 0000 0000 0000 0000 0100 1
                          |                                       |
                          |                                       |
                         补0          右移动1位计算过程             舍弃
        */
        System.out.println(9 >> 1);

        /*

                移位前     1000 0000 0000 0000 0000 0000 0000 1001

                取反码     1111 1111 1111 1111 1111 1111 1111 0110

                取补码     1111 1111 1111 1111 1111 1111 1111 0111

                移位后     1111 1111 1111 1111 1111 1111 1111 1011 1
                          |                                       |
                          |                                       |
                         补1           右移动1位计算过程            舍弃

               取反码      1000 0000 0000 0000 0000 0000 0000 0100
               求原码      1000 0000 0000 0000 0000 0000 0000 0101
         */
        System.out.println(-9 >> 1);

        /*
            无符号右移动，在移动位的时候与右移运算符的移动方式一样的，区别只在于补位的时候不管是0还是1，都补0（只对32位和64位有意义）

                移位前     1000 0000 0000 0000 0000 0000 0000 1001

                取反码     1111 1111 1111 1111 1111 1111 1111 0110

                取补码     1111 1111 1111 1111 1111 1111 1111 0111

                移位后     0111 1111 1111 1111 1111 1111 1111 1011 1
                          |                                       |
                          |                                       |
                         补0          右移动1位计算过程             舍弃
         */
        System.out.println(-9 >>> 1);

        System.out.println(Integer.MAX_VALUE - 4);
    }

    /**
     * 位运算缩写语法
     */
    @Test
    void testAbbreviationBigOperator () {
        int a = 8;
        // 拆开就是：a = a << 1;
        a <<= 1;
        System.out.println(a);

        a >>=1;
        System.out.println(a);

        a |= 1;
        // 拆开就是：a = a | 1;
        System.out.println(a);
    }
}
