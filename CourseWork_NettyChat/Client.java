package com.chimera.chat.client;

import java.io.*;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.handler.codec.*;
import io.netty.handler.codec.string.*;

public final class Client {

    public static void main(String[] args) throws Exception {
                                                                            //Настройка клиента
        EventLoopGroup group = new NioEventLoopGroup();                    //Пул потоков для обработки сетевых событий
        System.out.println("Клиент подключен + "\n" + "░CONNECTION░░░░░░░GUSE░░" + "\n" + " ░░░░░▄▀▀▀▄░░░░░░░" + "\n" + "▄███▀░◐░░░▌░░░░░░" +
                "\n" + "░░░░▌░░░░░▐░░░░░ " + "\n" + "░░░░▌░░░░▄▀▒▒▀▀▀▀▄" + "\n" + "░░░▐░░░░▐▒▒▒▒▒▒▒▒▀▀▄" + "\n" + "░░░▐░░░░▐▄▒▒▒▒▒▒▒▒▒▒▀▄" +
                "\n" + "░░░░▀▄░░░░▀▄▒▒▒▒▒▒▒▒▒▒▀▄" + "\n" + "░░░░░░▀▄▄▄▄▄█▄▄▄▄▄▄▄▄▄▄▄▀▄ " + "\n" + "░░░░░░░░░░░▌▌░▌▌░░░░░ " + "\n" +
                "░░░░░░░░░░░▌▌░▌▌░░░░░ " + "\n" + "░░░░░░░░░▄▄▌▌▄▌▌░░░░░");
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1234, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder", new StringDecoder()); //Входящий хэндлер - преобразует входной байтбуффер в строку
                            pipeline.addLast("encoder", new StringEncoder()); //Исходящий хэндлер - преобразование строки в байтбуффер
                            pipeline.addLast("handler", new ClientHandler());

                        }
                    });

            ChannelFuture f = b.connect("localhost", 1234).sync();             //Подключение к серверу

            Channel channel = f.channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true) {                                                     //Передача введенной строки серверу

                String line = in.readLine();
                ChannelFuture cf = channel.writeAndFlush(line + "\r\n");       
            }


        } finally {                                                           //Отключение клиента (закрытие пула потоков group)
            group.shutdownGracefully();
        }
    }
}
