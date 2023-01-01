package com.example.main;

import java.io.File;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.util.EnumSet;

public class ServeOneJabber extends Thread{
    SocketChannel socketChannel;
    Connection conn ;
    SocketAddress socketAddress ;
    public ServeOneJabber(SocketChannel socketChannel, Connection conn, SocketAddress socketAddress){
        this.socketChannel = socketChannel;
        this.conn = conn;
        this.socketAddress = socketAddress;
        start();
    }
    public void run(){
        try {
            readFileFromSocketChannet(socketChannel);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFileFromSocketChannet(SocketChannel socketChannel) throws IOException, InterruptedException {
        String filename = "file"+Thread.currentThread().getName().substring(5);
        Path path = Paths.get("C:\\serverpage\\"+filename+".zip");
        DBFunctions db = new DBFunctions();
        db.insert_row(conn, "maintest", filename, String.valueOf(path), String.valueOf(System.currentTimeMillis()), socketChannel.getRemoteAddress().toString());

        FileChannel fileChannel = FileChannel.open(path,
                EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE)
        );
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(buffer) >0) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("File Transfer started ");
        if(!buffer.hasRemaining()){
            System.out.println("File Transfer completed ");
        }

    }
}
