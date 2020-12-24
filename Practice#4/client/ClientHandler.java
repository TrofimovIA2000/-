package com.chimera.chat.client;

import io.netty.channel.*;
import io.netty.channel.ChannelHandlerContext;



public class ClientHandler extends SimpleChannelInboundHandler<String>{

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String s) {
        System.out.println(s);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}