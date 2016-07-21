package com.it5.okio_19;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IT5 on 2016/7/21.
 */
public class GzipUtil {
    /**
     * GZIP压缩     *     * @param data     * @return
     */
    public static byte[] gzip(byte[] data) throws Exception {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream zos;
        BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(data));
        byte[] buf = new byte[512];
        int len;
        try {
            zos = new GZIPOutputStream(out);
            while ((len = bis.read(buf)) != -1) {
                zos.write(buf, 0, len);
                zos.flush();
            }
            bis.close();
            zos.close();
            return out.toByteArray();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    /**
     * Gzip解压缩     * @param b     * @return
     */
    public static byte[] unGzip(byte[] b) {
        if (b == null || b.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        try {
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            Log.e("d_error: ", "uncompress error", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
        return null;
    }
}