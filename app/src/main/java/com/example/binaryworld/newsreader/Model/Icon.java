package com.example.binaryworld.newsreader.Model;

/**
 * Created by BinaryWorld on 14-Nov-17.
 */

public class Icon {

    private String url;
    private int width,height,bytes;
    private String format,shalSum;
    private Object error;

    public Icon() {

    }

    public Icon(String url, int width, int height, int bytes, String format, String shalSum, Object error) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.bytes = bytes;
        this.format = format;
        this.shalSum = shalSum;
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBytes() {
        return bytes;
    }

    public String getFormat() {
        return format;
    }

    public String getShalSum() {
        return shalSum;
    }

    public Object getError() {
        return error;
    }
}
