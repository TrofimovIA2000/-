package com.company;

import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class EchoServer {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        while (true)
                {
        //
                    String input = dis.readUTF();

                    if(input.equals("End."))
                        break;

                    System.out.println("Полученное выражение:-" + input);
                    int result;



                    StringTokenizer st = new StringTokenizer(input);    // StringTokenizer чтобы разделить уравнение на операнды и операции
                    int oprnd1 = Integer.parseInt(st.nextToken());

                    String operation = st.nextToken();
                    int oprnd2 = Integer.parseInt(st.nextToken());


                    if (operation.equals("+")){                         // Выбор операции
                        result = oprnd1 + oprnd2;
                    }

                    else if (operation.equals("-")){
                        result = oprnd1 - oprnd2;
                    }

                    else if (operation.equals("*")){
                        result = oprnd1 * oprnd2;
                    }

                    else{
                        result = oprnd1 / oprnd2;
                    }

                    System.out.println("Отправка результата...");


                    dos.writeUTF(Integer.toString(result));            // отправление результата обработки сообщения клиенту.

        }

    }
}

