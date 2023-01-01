package com.examples.testserver1;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender extends Thread{

    private final String filePath;
    private final SocketChannel socketChannel;
    public FileSender(String filePath) throws IOException {
        this.filePath = filePath;
        this.socketChannel = CreateChannel();
        System.out.println("server connected for... " + filePath);
        start();
    }

    public void run() {
        try {
            sendFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendFile() throws IOException {
        Path path = Paths.get(filePath);
        FileChannel inChannel = FileChannel.open(path);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.write(ByteBuffer.wrap(filePath.getBytes()));

        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
        System.out.println("File Transfer completed for " + filePath);
//        if(!socketChannel.isOpen()) {
//            boolean result = Files.deleteIfExists(path);
//            if (result) System.out.println("file deleted");
//            else System.out.println("file not exists");
//        }else {
//            System.out.println("socket running...");
//        }
    }
    private SocketChannel CreateChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 9000);
        socketChannel.connect(socketAddress);
        return socketChannel;
    }
}
