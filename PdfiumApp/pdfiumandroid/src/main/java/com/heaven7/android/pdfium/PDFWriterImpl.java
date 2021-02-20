package com.heaven7.android.pdfium;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PDFWriterImpl implements PDFWriter {

    private final File file;

    public PDFWriterImpl(File file) {
        this.file = file;
    }

    @Override
    public void write(ByteBuffer buffer) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            byte[] arr;
            if(buffer.hasArray()){
                arr = buffer.array();
                out.write(arr, buffer.arrayOffset(), buffer.capacity());
            }else {
                arr = new byte[buffer.capacity()];
                buffer.get(arr);
                out.write(arr);
            }
            out.flush();
            System.out.println("write pdf file ok: " + file);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
