package com.chimera.chat.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class Handler extends SimpleChannelInboundHandler<String> {

    private static final List<Channel> channels = new ArrayList<>();
    private static int clientIndex = 1;
    private String clientName;

    @Override                                                                       //Событие подключения клиента
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключен" + ctx);
        channels.add(ctx.channel());
        clientName = "Химера-" + clientIndex;
        clientIndex++;
    }

    @Override                                                                      //Обработка данных, введенных клиентом
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Получено сообщение" + s);
        String out = String.format("[%s]: %s\n", clientName, s);                  //Очистка памяти
        for(Channel c: channels) {
            c.writeAndFlush(out);
        }
    }

    @Override                                                                                       //Обработка отключения клиента
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Пользователь" + clientName + "Покинул чат");
        channels.remove(ctx.channel());
        ctx.close();
    }

}
