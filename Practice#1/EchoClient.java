package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        Socket echoSocket = new Socket(args[0], Integer.parseInt(args[1]));

        Scanner in = new Scanner(System.in);

        DataInputStream dis = new DataInputStream(echoSocket.getInputStream());
        DataOutputStream dos = new DataOutputStream(echoSocket.getOutputStream());

        while (true)

        {


            System.out.print("Введите сообщение в форме: ");                        // Ввод выражения
            System.out.println("'<Операнд> <Оператор> <Операнд>'");

            String inp = in.nextLine();
            if (inp.equals("End."))
                break;


            dos.writeUTF(inp);                                                      // отправка сообщения на сервер


            String ans = dis.readUTF();                                             // прием ответа от сервера
            System.out.println("Ответ=" + ans);

        }

    }
}
