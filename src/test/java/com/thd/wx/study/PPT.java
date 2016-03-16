package com.thd.wx.study;


import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.record.ColorSchemeAtom;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PPT {
    //直接抽取幻灯片的全部内容
    public static String readDoc1(InputStream is) throws IOException{
        PowerPointExtractor extractor=new PowerPointExtractor(is);
        return extractor.getText();
    }

    //一张幻灯片一张幻灯片地读取
    public static void readDoc2(InputStream is) throws IOException{
        HSLFSlideShow ss=new HSLFSlideShow(is);
        List<HSLFSlide> slides=ss.getSlides();
        for (HSLFSlide slide : slides) {
            //读取一张幻灯片的标题
            String title=slide.getTitle();
            System.out.println("标题:"+title);
            ColorSchemeAtom colorScheme = slide.getColorScheme();
            int fillsColourRGB = colorScheme.getFillsColourRGB();
            //读取一张幻灯片的内容(包括标题)

            /*slide.get
            TextSpecInfoRun
            TextRun[] runs=slide.getTextSpecInfoRun;
            for(int j=0;j<runs.length;j++){
                System.out.println(runs[j].getText());
            }*/
        }
        /*for(int i=0;i<slides.length;i++){
            //读取一张幻灯片的标题
            String title=slides[i].getTitle();
            System.out.println("标题:"+title);
            //读取一张幻灯片的内容(包括标题)
            TextRun[] runs=slides[i].getTextRuns();
            for(int j=0;j<runs.length;j++){
                System.out.println(runs[j].getText());
            }
        }*/
    }

    public static void main(String[] args){
        File file = new File("D:/linux命令行入门_开发定制版.ppt");
        try{
            FileInputStream fin=new FileInputStream(file);
            String cont=readDoc1(fin);
            System.out.println(cont);
            fin.close();
            fin=new FileInputStream(file);
            readDoc2(fin);
            fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
