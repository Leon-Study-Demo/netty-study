package jp.tokyo.leon.netty.c1;

import jp.tokyo.leon.netty.util.ByteBufferUtils;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author leon
 * @date 2024/2/19 00:25
 */
public class ByteBufferExamTest {
    /*
        网络上有多条数据发送给服务端，数据之间用 \n 进行分隔
        但由于某种原因这些数据进行了重新组合，例如原始数据有3条
        Hello,world\n
        I'm zhangsan\n
        How are you?\n
        变成了下面两个ByteBuffer(黏包，半包)
        Hello,world\nI'm zhangsan\nHo
        w are you?\n
        现在要求你写程序，将错乱的数据恢复成按 \n 分隔的数据
     */

    @Test
    public void testByteBufferExam() {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    byte b = source.get();
                    target.put(b);
                }
                ByteBufferUtils.debugAll(target);
            }
        }

        source.compact();
    }
}
