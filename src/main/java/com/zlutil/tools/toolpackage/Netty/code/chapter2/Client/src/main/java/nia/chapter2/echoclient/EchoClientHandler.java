package com.zlutil.tools.toolpackage.Netty.code.chapter2.Client.src.main.java.nia.chapter2.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

/**
 * Listing 2.3 ChannelHandler for the client
 *
 * channelActive ()——在到服务器的连接已经建立之后将被调用;
 * channelRead0 () ——当从服务器接收到一条消息时被调用;
 * exceptionCaught ()——在处理过程中引发异常时被调用。
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable
public class EchoClientHandler
        extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty 运行中!",
                CharsetUtil.UTF_8));//当被通知Channel是活跃的时候,发送一条消息
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println(
                "客户端收到消息: " + in.toString(CharsetUtil.UTF_8));
        //记录已接受消息的转储
        ctx.writeAndFlush("我是客户端，我发送这条消息给服务端，当前时间: "+LocalDateTime.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();//关闭Channel
    }
}
