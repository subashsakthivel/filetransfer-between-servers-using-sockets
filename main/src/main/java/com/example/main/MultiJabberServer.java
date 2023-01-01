package com.example.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;


public class MultiJabberServer extends Thread {
    private Connection conn = null;
    public MultiJabberServer( Connection conn) throws IOException {
        System.out.println("Server started");
        this.conn = conn;
        start();
    }

    public void  run(){
        try {
            createSocketChannel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSocketChannel() throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));

        try {
            while(true) {
                client = serverSocket.accept();
                try {
                    new ServeOneJabber(client, conn, client.getLocalAddress());
                } catch (Exception e) {
                    client.close();
                }
                System.out.println("connection established.." + client.getRemoteAddress()+ " " + client.getLocalAddress());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
