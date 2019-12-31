package com.msj.android_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.msj.android_project.activity.BaseActivity;
import com.msj.android_project.utils.ImageTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image2PDFActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2_pdf);
        initNavBar(true,"好好学习");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image2pdf();
            }
        });

    }

    public void image2pdf(){
        //获取指定目录下的所有文件名称
        try {
            List<File> files = new ArrayList<>();
            File imgDir;
            File targetFile = new File("./pdfTarget/my_doc.pdf");
            String[] str = this.getAssets().list("help_IMG");
            if (str.length > 0) {//如果是目录
                for (String string : str) {
                    String path = "help_IMG/" + string;
                    imgDir = new File(path);
                    files.add(imgDir);
                }
            }
            ImageTools.createBlankPdf(targetFile, files.size());
            ImageTools.fillImg2Pdf(targetFile, files);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
