package com.chimera.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerApp {

    public static void main(String[] args){

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);        //пул потоков для инициализации подключающихся клиентов
        EventLoopGroup workerGroup = new NioEventLoopGroup();               //пул потоков для обработки данных

        try {                                                               //Настройка сервера
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)                                  //Использование двух пулов потоков для обработки клиентов
                    .channel(NioServerSocketChannel.class)                  //Использование стандартного сокета NIO
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new Handler()); // добавление
                        }
                    });
            ChannelFuture future = b.bind(1234).sync();             //Запуск сервера
            future.channel().closeFuture().sync();                         //Ожидание остановки работы сервера
        }

        catch(Exception e){
            e.printStackTrace();
        }

        finally{                                                           //Закрытие пулов потоков bossGroup и workerGroup
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
