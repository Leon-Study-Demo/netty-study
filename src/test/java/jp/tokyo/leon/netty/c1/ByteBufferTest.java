package jp.tokyo.leon.netty.c1;

import jp.tokyo.leon.netty.util.ByteBufferUtils;
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


    @Test
    public void testByteBufferReadWrite() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 0x61); // ‘a’
        ByteBufferUtils.debugAll(byteBuffer);
        byteBuffer.put(new byte[]{0x62, 0x63, 0x64}); // a b c
        ByteBufferUtils.debugAll(byteBuffer);
//        System.out.println(byteBuffer.get());
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
        ByteBufferUtils.debugAll(byteBuffer);
        byteBuffer.compact();
        ByteBufferUtils.debugAll(byteBuffer);
        byteBuffer.put(new byte[]{0x65, 0x66});
        ByteBufferUtils.debugAll(byteBuffer);
    }

    @Test
    public void testByteBufferAllocate() {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());

        /*
         class java.nio.HeapByteBuffer        -java 堆内存   读写效率较低，受到 GC 的影响
         class java.nio.DirectByteBuffer      -直接内存，读写效率高（少一次拷贝），不会受到 GC 影响，分配效率低
         */
    }


    @Test
    public void testByteBufferReadRewind() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[]{'a', 'b', 'c', 'd'});
        byteBuffer.flip();

        // rewind 从头开始读
        byteBuffer.get(new byte[4]);
        ByteBufferUtils.debugAll(byteBuffer);
        byteBuffer.rewind();
        System.out.println((char) byteBuffer.get());
        ByteBufferUtils.debugAll(byteBuffer);

        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position 重置到 mark的位置

    }

    @Test
    public void testByteBufferReadMarkReset() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[]{'a', 'b', 'c', 'd'});
        byteBuffer.flip();

        // mark & reset
        // mark 做一个标记，记录position位置，reset是将position 重置到 mark的位置
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());

        byteBuffer.mark(); // 加标记 索引为2 的位置 ‘c’
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
        byteBuffer.reset();
        System.out.println((char) byteBuffer.get());
        System.out.println((char) byteBuffer.get());
    }


    @Test
    public void testByteBufferReadGetIndex() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[]{'a', 'b', 'c', 'd'});
        byteBuffer.flip();

        // get(i)不会改变读索引的位置
        System.out.println((char) byteBuffer.get(3));
        ByteBufferUtils.debugAll(byteBuffer);
    }



}
