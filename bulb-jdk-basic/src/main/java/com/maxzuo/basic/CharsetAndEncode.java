package com.maxzuo.basic;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

/**
 * Java字符的编码和解码
 * <p>
 * Created by zfh on 2020/01/12
 */
public class CharsetAndEncode {

    /**
     * 1.编码字符集
     *   Java使用的unicode，由于编码字符集为每一个字符赋予一个数字，因此在java内部，字符可以认为就是一个16位的数字，
     *   因此以下方式都可以给字符赋值：
     */
    @Test
    public void testCharset () {
        char a = '中';
        char b = 0x4e2d;
        char c = 20013;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    /**
     * 2.字符编码方案（Character-encoding schema）
     *   将字符编码（数字）映射到一个字节数组的方案，因为在磁盘里，所有信息都是以字节的方式存储的。因此Java的16位字符
     *   必须转换为一个字节数组才能够存储。例如UTF-8字符编码方案，它可以将一个字符转换为1、2、3或者4个字节。一般认为，
     *   编码字符集和字符编码方案合起来被称之为字符集（Charset），这是一个术语，要和前面的字符集合（Character set）
     *   区分开。
     */
    @Test
    public void testCharacterEncodeSchema () {
        String str = "hello world";
        // 从数字到字节数组，这个转换被称之为编码
        byte[] tmpBytes = str.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(tmpBytes));

        // 编码方式二
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(str);
        byte[] tmpBytes2 = new byte[byteBuffer.remaining()];
        byteBuffer.get(tmpBytes2);
        System.out.println(Arrays.toString(tmpBytes2));

        // 解码的两种方式
        System.out.println(new String(tmpBytes, StandardCharsets.UTF_8));

        ByteBuffer buffer = ByteBuffer.wrap(tmpBytes);
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
        System.out.println(charBuffer.toString());
    }

    /**
     * 3.默认的字符集
     *   Java的默认字符集，可以在两个地方设定，一是执行java程序时使用-Dfile.encoding参数指定，例如-Dfile.encoding=UTF-8
     *   就指定默认字符集是UTF-8。二是在程序执行时使用Properties进行指定，如下：
     *   注意：这两种方法如果同时使用，则程序开始时使用参数指定的字符集，在Properties方法后使用Properties指定的字符集。
     *   如果这两种方法都没有使用，则使用操作系统默认的字符集。
     */
    @Test
    public void testDefaultCharset() {
        Properties properties = System.getProperties();
        properties.put("file.encoding", "UTF-8");
        System.out.println(properties.get("file.encoding"));
    }

    /**
     * 4.大小端字节序
     *   字节序就是数据在内存中存放的顺序，多于一个字节的数据在内存中存放时有两种选择，即Big Endian和Little Endian。
     *   Little-Endian就是低位字节排放在内存的低地址端，高位字节排放在内存的高地址端。
     *   Big-Endian就是高位字节排放在内存的低地址端，低位字节排放在内存的高地址端。
     *   Big Endian和Little Endian和芯片类型以及操作系统都有关系。但是由于Java是平台无关的，所以Java被设计为Big Endian的。
     *   但是当Java中的字符进行编码时，就要注意其字节序了。
     *
     * <pre>
     *   内存地址
     *       内存地址只是一个编号，代表一个内存空间。那么这个空间是多大呢？原来在计算机中存储器的容量是以字节为基本单位的。
     *       也就是说一个内存地址代表一个字节（8bit）的存储空间。
     *
     *       内存是把8个bit排成1组，每1组成为1个单位，大小是1byte(字节），CPU每一次只能访问1个byte，而不能单独去访问具体的1个bit。
     *       1个byte（字节）就是内存的最小的IO单位。
     *
     *       32位系统里内存地址长度是32位，所以32位的地址范围就是从 0000 0000 0000 0000 0000 0000 0000 0000 到 1111 1111 1111
     *       1111 1111 1111 1111 1111（Ox00000000 ~ OxFFFFFFFF)，也就是可以寻址2^32个地址，每个地址对应一个8bit的的内存单位。
     *
     *       例如经常说32位的操作系统最多支持4GB的内存空间，也就是说CPU只能寻址2的32次方（4GB），注意这里的4GB是以Byte为单位的，不是
     *       bit。也就是说有4G = 4*1024M（Byte）= 4*1024*1024Kb(Byte) = 4*1024*1024*1024Byte(8bit)，即2的32次方个8bit单位。
     *
     *       总结：内存地址是内存当中存储数据的一个标识，并不是数据本身，通过内存地址可以找到内存当中存储的数据。
     *
     *
     *  高位/低位字节
     *       一般一个16位（双字节）的数据，比如：0xFF1A（16进制），那么高位字节就是FF，低位是1A
     *       如果是32位的数据，比如：0x3F68415B，高位字（不是字节）是3F68，低位字是415B。
     *
     *       例如定义一个unsigned short型变量在0x1234 5678，那么这个变量的地址就是0x1234 5678，占用0x1234 5678与0x1234 5679两字节
     *       存储空间，其中0x1234 5678是低字节、0x1234 5679是高字节。
     *
     * 	字节序
     * 	     一个字节中还包含8个bit (bit = binary digit)，在一个32位的CPU中“字长”为32个bit，也就是4个byte。在这样的CPU中，总是以
     *       4字节对齐的方式来读取或写入内存，那么同样这4个字节的数据是以什么顺序保存在内存中的呢？
     *
     *       字节序包括：大端序和小端序，为什么要这么麻烦还要分门别类呢？举个例子，255用二进制表达就是1111 1111，再加1就是1 0000 0000，
     *       多了一个1出来，显然我们需要再用额外的一个字节来存放这个1，但是这个1要存放在第一个字节还是第二个字节呢？这时候因为人们选择的
     *       不同，就出现了大端序和小端序的差异。
     *
     *       大端序的高字节存在低地址，低字节存在高地址，小端序相反，网络序一般为大端序。
     *
     *       在读内存时一般从低字节向高字节读取。
     *
     *       如一个long型数据0x12345678
     *       大端字节序：
     * 	     内存低地址--> 0x12
     *                    0x34
     * 　　　　　　         0x56
     *       内存高地址--> 0x78
     *
     *       小端字节序：
     *       内存低地址--> 0x78
     * 　　　　　　　       0x56
     * 　　　　　　　       0x34
     *       内存高地址--> 0x12
     *  </pre>
     */
    @Test
    public void testBigEndianAndLittleEndian () {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.asIntBuffer().put(1);
        System.out.println(Arrays.toString(buffer.array()));

        // 小端序
        ByteBuffer buffer2 = ByteBuffer.allocate(4);
        buffer2.order(ByteOrder.LITTLE_ENDIAN);
        buffer2.asIntBuffer().put(1);
        System.out.println(Arrays.toString(buffer2.array()));
    }

    /**
     * 5.Java中内码和外码的区分
     *   内码：char或String在内存里使用的编码方式，内码使用的是UTF-16.
     *
     *   当运行java字节码时，读入到内存里的字符或者字符串都用char或char[]表示，而char是采用utf-16的但不是真正的utf-16，
     *   为什么这样说? char在java里一直是16位的也就是说只能表示带utf-16的0x0000-0xFFFF为止，当对于超过16位的utf-16字符
     *   来说(比如表情符号)就需要多个char。
     *
     *   补充：
     *     UTF-16编码一个字符对于U+0000-U+FFFF范围内的字符采用2字节进行编码，而对于字符的码点大于U+FFFF的字符采用四字节进行编码。
     *
     *   外码：除了内码都可以认为是“外码”。（包括class文件的编码）
     *
     *   java源文件 .java 和编译后的 .class 文件，源文件可以采用多种编码格式如UTF-8或GBK。默认是javac 按照系统默认的编码
     *   格式读取java源文件，然后以utf-8的格式输出到 .class 文件中，换句话说，在默认情况下unix平台，javac用utf-8格式读取
     *   java源文件然后以utf-8格式写.class；在默认情况下windows平台，javac用gbk格式读取java源文件然后以utf-8格式写.class。
     *
     *
     *  正题：String.length()返回字符串中的 Unicode 代码单元的数目
     *
     *  1）代码单元指一种转换格式（UTF）中最小的一个分隔，称为一个代码单元（Code Unit）。因此，一种转换格式只会包含整数个
     *     单元。UTF-X 中的数字 X 就是各自代码单元的位数。
     *  2）UTF-16的16指的就是最小为16位一个单元，也即两字节为一个单元，UTF-16可以包含一个单元和两个单元，对应即是两个字节
     *     和四个字节。
     */
    @Test
    public void testStringLength () {
        String str = "hello world";
        System.out.println(str.length());

        // 四个字节emoji表情，长度为两个单元
        String emoji = "😂";
        System.out.println(emoji.length());
    }

    /**
     * 表情字符转编码
     */
    @Test
    public void testEmojiEncode () throws UnsupportedEncodingException {
        String emoji = "😂";
        byte[] bytes = emoji.getBytes("UTF-32");
        System.out.println(Arrays.toString(bytes));
        System.out.println(getBytesCode(bytes));
    }

    private static String getBytesCode(byte[] bytes) {
        StringBuilder code = new StringBuilder();
        for (byte b : bytes) {
            code.append("\\0x").append(Integer.toHexString(b & 0xff));
        }
        return code.toString();
    }

    /**
     * 编码转emoji表情
     */
    @Test
    public void testEmojiDecode () throws UnsupportedEncodingException {
        // 16进制转10进制
        int emojiCode = Integer.valueOf("1f602", 16);
        byte[] emojiBytes = int2bytes(emojiCode);
        String emojiChar = new String(emojiBytes, "UTF-32");
        System.out.println(emojiChar);
    }

    private static byte[] int2bytes(int num){
        byte[] result = new byte[4];
        result[0] = (byte)((num >>> 24) & 0xff);
        result[1] = (byte)((num >>> 16) & 0xff );
        result[2] = (byte)((num >>> 8) & 0xff );
        result[3] = (byte)((num) & 0xff );
        return result;
    }
}
