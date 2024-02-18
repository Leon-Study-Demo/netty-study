package jp.tokyo.leon.netty.c1;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author leon
 * @date 2024/2/19 00:53
 */
public class FileChannelTransferTest {

    @Test
    public void testFileChannelTransferTo() {
        try (
                FileChannel from = new FileInputStream("data.txt").getChannel();
                FileChannel to = new FileOutputStream("to.txt").getChannel()
        ) {
            // 效率高，底层利用操作系统的零拷贝， 最多2g数据
            long size = from.size();
            for (long left = size; left > 0; ) {
                left -= from.transferTo(size - left, left, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
