package com.heaven7.android.pdfium.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    PdfiumCore pdfiumCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdfiumCore = new PdfiumCore(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //pdfiumCore.newDocument()
                File f = null;
                try {
                    f = FileUtils.fileFromAsset(getApplicationContext(), "sample.pdf");
                    ParcelFileDescriptor pfd = ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_ONLY);
                    PdfDocument pdfDocument = pdfiumCore.newDocument(pfd);
                    System.out.println(pdfDocument);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
