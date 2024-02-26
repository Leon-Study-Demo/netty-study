package jp.tokyo.leon.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author leon
 * @date 2024/2/27 00:20
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装netty 组件，启动服务器
        new ServerBootstrap()
                // 2.BossEventLoop，WorkerEventLoop（selector，thread），group
                .group(new NioEventLoopGroup())
                // 3.选择服务器的ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
                // 4.Boss负责处理连接 worker（child）负责读写，决定了 worker（child）能执行哪些操作（handler）
                .childHandler(
                        // 5.客户端读写的通道 ChannelInitializer 初始化，负责添加别的 handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 6.添加具体的handler
                        nioSocketChannel.pipeline().addLast(new StringDecoder()); // 将 ByteBuf 转化为字符串
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自定义handler
                            @Override // 读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 打印转化的字符串
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
