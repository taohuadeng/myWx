package com.thd.wx.study;

import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.sl.usermodel.Line;
import org.apache.poi.sl.usermodel.StrokeStyle;
import org.apache.poi.sl.usermodel.TextParagraph;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;

import org.apache.poi.xslf.usermodel.XMLSlideShow;

/**
 * Created by taofadeng on 16/3/24.
 */
public class PPTX {
    public static void main(String[] args) {

        try {
            //newPresentation();

            //changePageSize();
            //getShapes();


            XMLSlideShow xmlSlideShow = new XMLSlideShow(new FileInputStream("test01.pptx"));
            xmlSlideShow.getSlides().get(0).getXmlObject().getTransition().isSetAdvClick();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //如何获得形状包含在一个特定的幻灯片
    private static void getShapes() throws IOException {
        HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl("slideshow.ppt"));
        // get slides
        for (HSLFSlide slide : ppt.getSlides()) {
            for (HSLFShape sh : slide.getShapes()) {
                // name of the shape
                String name = sh.getShapeName();

                // shapes's anchor which defines the position of this shape in the slide
                //java.awt.Rectangle2D anchor = sh.getAnchor();
                Rectangle2D anchor = sh.getAnchor();

                if (sh instanceof Line) {
                    Line line = (Line) sh;
                    // work with Line
                } else if (sh instanceof HSLFAutoShape) {
                    HSLFAutoShape shape = (HSLFAutoShape) sh;
                    // work with AutoShape
                } else if (sh instanceof HSLFTextBox) {
                    HSLFTextBox shape;
                    shape = (HSLFTextBox) sh;
                    // work with TextBox
                } else if (sh instanceof HSLFPictureShape) {
                    HSLFPictureShape shape = (HSLFPictureShape) sh;
                    // work with Picture
                }
            }
        }
    }

    //如何检索或更改幻灯片大小
    private static void changePageSize() throws IOException {
        HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl("slideshow.ppt"));
        //retrieve page size. Coordinates are expressed in points (72 dpi)
        java.awt.Dimension pgsize = ppt.getPageSize();
        int pgx = pgsize.width; //slide width
        int pgy = pgsize.height; //slide height

        //set new page size
        ppt.setPageSize(new java.awt.Dimension(1024, 768));
        //save changes
        FileOutputStream out = new FileOutputStream("slideshow2.ppt");
        ppt.write(out);
        out.close();
    }

    //新演示文稿
    private static void newPresentation() throws IOException {
        //create a new empty slide show
        HSLFSlideShow ppt = new HSLFSlideShow();

        //add first slide
        HSLFSlide s1 = ppt.createSlide();

        //add second slide
        HSLFSlide s2 = ppt.createSlide();

        //save changes in a file
        FileOutputStream out = null;
        out = new FileOutputStream("slideshow.ppt");
        ppt.write(out);
        out.close();
    }
}
