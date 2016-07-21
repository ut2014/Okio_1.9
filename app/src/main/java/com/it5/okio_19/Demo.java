package com.it5.okio_19;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSink;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by IT5 on 2016/7/21.
 */
public class Demo {

    public static void main(String[] args) {
        read_gzip();
    }

    public static void read_gzip() {
        Sink sink = null;
        BufferedSink bufferedSink = null;
        GzipSink gzipSink = null;
        try {
            File dest = new File("gzip.txt");
            sink = Okio.sink(dest);
            gzipSink = new GzipSink(sink);
            bufferedSink = Okio.buffer(gzipSink);
            bufferedSink.writeUtf8("android vs ios");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedSink);
        }
        Source source = null;
        BufferedSource bufferedSource = null;
        GzipSource gzipSource = null;
        try {
            File file = new File("gzip.txt");
            source = Okio.source(file);
            gzipSource = new GzipSource(source);
            bufferedSource = Okio.buffer(gzipSource);
            String content = bufferedSource.readUtf8();
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedSource);
        }
    }


    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}
