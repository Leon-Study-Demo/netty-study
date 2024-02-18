package jp.tokyo.leon.netty.c1;

import jp.tokyo.leon.netty.util.ByteBufferUtils;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author leon
 * @date 2024/2/19 00:04
 */
public class ByteBufferStringTest {

    @Test
    public void testByteStringToByteBuffer() {
        // 1. 字符串先转为字符数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.put("hello".getBytes());
        ByteBufferUtils.debugAll(byteBuffer);

        // 2. charset
        ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtils.debugAll(byteBuffer1);

        // 3. wrap
        ByteBuffer byteBuffer2 = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtils.debugAll(byteBuffer2);

    }

    @Test
    public void testByteByteBufferToString() {

        // 1. charset
        ByteBuffer byteBuffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtils.debugAll(byteBuffer1);

        String string = StandardCharsets.UTF_8.decode(byteBuffer1).toString();
        System.out.println(string);

    }
}
