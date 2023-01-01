package com.examples.testserver1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

public class FileGenerator implements Runnable{
    public String prefix = "example";
    public String table_name, dbname,user,password;
    Connection conn = null;
    public File createFile(final String prefix, long l) throws IOException {
        String folder = "D:\\sub-test1\\";
        File file = new File(folder + prefix + ".zip");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.setLength(ThreadLocalRandom.current().nextLong(1048576 * 500, 1048576L * 4500));
        raf.close();
        return file;
    }
    public FileGenerator(String table_name, String dbname, String user, String password, String prefix, Connection conn) {
        this.table_name = table_name;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.prefix = prefix;
        this.conn = conn;
    }
    @Override
        public void run() {
            DBFunctions db = new DBFunctions();
                if (conn != null) {
                    String message;
                    int n = 0;
                    while (true) {
                        if(Thread.activeCount()<80) {
                            File f = null;
                            System.out.println(System.currentTimeMillis());
                            BasicFileAttributes bc = null;
                            try {
                                f = createFile(prefix + n, ThreadLocalRandom.current().nextLong(1048576 * 500, 1048576L * 4500));
                                bc = Files.readAttributes(Paths.get(f.getAbsolutePath()), BasicFileAttributes.class);
                                message = bc.creationTime() + " " + bc.size();
                                db.insert_row(conn, table_name, f.getName(), f.getAbsolutePath(), bc.creationTime().toString(), bc.size() + "");
                                new FileSender(f.getAbsolutePath());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                Thread.currentThread().sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            n++;
                        }
                    }
            } else {
                    out.println("Connection failed");
                }
        }

}
