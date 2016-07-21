package com.it5.okio_19;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by IT5 on 2016/7/21.
 */
public class Okio_Demo {
    /**
     * 读取文件
     */
    private static void read() {
        String filename = "test.txt";
        Source source = null;
        BufferedSource bSource = null;
        try {
            File file = new File(filename);
            //读文件
            source = Okio.source(file);
            //通过source拿到 bufferedSource
            bSource = Okio.buffer(source);
            String read = bSource.readString(Charset.forName("utf-8"));
            System.out.print(read);//显示文字
//            System.out.print(ByteString.of(read.getBytes()).md5());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bSource) {
                    bSource.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新建文件并写入数据
     */
    private static void create_writer() {
        String filename = "create.txt";
        boolean isCreate = false;
        Sink sink;
        BufferedSink bSink = null;
        try {
            //判断文件是否存在，不存在，则新建！
            File file = new File(filename);
            if (!file.exists()) {
                isCreate = file.createNewFile();
            } else {
                isCreate = true;
            }
            //写入操作
            if (isCreate) {
                sink = Okio.sink(file);
                bSink = Okio.buffer(sink);
                bSink.writeUtf8("1");
                bSink.writeUtf8("\n");
                bSink.writeUtf8("this is new file!");
                bSink.writeUtf8("\n");
                bSink.writeString("我是每二条", Charset.forName("utf-8"));
                bSink.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bSink) {
                    bSink.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 文件内容追加
     */
    public static void appendFile() {
        String filename = "create.txt";
        File file = new File(filename);
        try {
            //1.将文件读入，并构建写缓冲池
            BufferedSink
                    sink = Okio.buffer(Okio.appendingSink(file));
            //2.追加文本
            sink.writeUtf8("Hello world _ append");
            //3.关闭
            sink.flush();
//            sink.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *写buffer
     * 在okio中buffer是一个很重要的对象
     */
    public static void sinkFromOutputStream(){
        String filename = "create.txt";
        File file = new File(filename);
        //1.构建buffer对象
        Buffer data=new Buffer();
        //2.向缓冲中写入文本
        data.writeUtf8("afdsa");
        //3.可以连续追加，类似StringBuffer
        data.writeUtf8("b");
        //4.构建字节数组流对象
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //5.构建写缓冲池
//        Sink sink = Okio.sink(out);

        //6.向池中写入buffer
        try {
            Sink sink=Okio.sink(file);
            sink.write(data, 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读buffer
     */
    public static void sourceFromInputStream(){
        //1.构建字节数组流
            try {

                InputStream
                    in = new ByteArrayInputStream(("adasfdsaf").getBytes());
                //2.缓冲源
                Source source=Okio.source(in);
                //3.buffer
                Buffer sink = new Buffer();
                source.read(sink,in.read());
                //4.将数据读入buffer
                System.out.print(sink.readUtf8());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static void main(String... args) throws Exception {
//        read();
//        create_writer();
//        appendFile();
//        sinkFromOutputStream();
        sourceFromInputStream();
    }


     /*File file= new File("test.txt");
        if (file.exists()) {
            System.out.print("ss");
        }

        Properties prop = new Properties();*/
}
