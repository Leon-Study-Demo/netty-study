package jp.tokyo.leon.netty.c1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author leon
 * @date 2024/2/18 22:59
 */
@Slf4j
public class ByteBufferTest {

    @Test
    public void testByteBuffer() {
        // FileChannel
        // 1. 输入输出流
        // 2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                // 从channel读取，向 buffer 写
                int length = channel.read(byteBuffer);
                log.debug("读取到的字节数 {}", length);
                if (length == -1) {
                    break;
                }
                byteBuffer.flip(); //读模式
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get(); //一次读一个字节
                    log.debug("实际字节 {}", (char)b);
                }
                byteBuffer.clear();
                byteBuffer.compact();
            }

        } catch (IOException e) {
        }
    }

}
