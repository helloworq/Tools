package com.zlutil.tools.toolpackage.Netty.code.chapter2.Server.src.main.java.nia.chapter2.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Listing 2.1 EchoServerHandler
 *
 * 1.channelRead ()——对于每个传入的消息都要调用;
 * 2.channelReadComplete()——通知ChannelInboundHandler最后一次对channel-Read()的调用是当前批量读取中的最后一条消息;
 * 3.exceptionCaught ()——在读取操作期间，有异常抛出时会调用。
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Sharable//标示一个ChanneHandler可以被多个Channel安全地共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "服务器收到消息: " + in.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(in);//将接收到的消息写给发送者,而不冲刷出站消息
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)//将未决消息冲刷到远程节点,并且关闭该Channel
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();//关闭Channel
    }
}
