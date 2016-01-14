package com.thd.wx.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.Random;

public class FishUtil {
    /**
     * 改方法用于获取指定位数的随机数
     *
     * @param num 随机数位数
     * @return 生成的num位随机数
     * @author TaoFaDeng@HF
     * @since 2015年4月30日8:34:43
     */
    public static String getConfirmCode(int num) {
        Random rm = new Random();
        // 获得随机数
        double tempCode = (1 + rm.nextDouble()) * Math.pow(10, num);
        NumberFormat confirmCodeFormat = NumberFormat.getIntegerInstance();
        confirmCodeFormat.setGroupingUsed(false);
        confirmCodeFormat.setMaximumIntegerDigits(num);
        confirmCodeFormat.setMinimumIntegerDigits(num);
        return confirmCodeFormat.format(tempCode);
    }

    /**
     * 输出图形验证码
     *
     * @param code     需要展示的验证码
     * @param width    图形验证码的长度
     * @param height   图形验证码的高度
     * @param response response对象
     * @since 2016年1月14日19:38:30
     */
    public static void generateSecCode(String code, int width, int height, HttpServletResponse response) {
        //HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response = ServletActionContext.getResponse();
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            ServletOutputStream out = response.getOutputStream();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            // 设定背景色
            graphics.setColor(randomColor(200, 250));
            graphics.fillRect(0, 0, width, height);

            // 设定字体
            Font mFont = new Font(null, Font.BOLD, width / 3);// 设置字体
            graphics.setFont(mFont);

            // 画边框
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, 0, width - 1, height - 1);

            // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
            graphics.setColor(randomColor(160, 200));
            // 生成随机类
            Random random = new Random();
            for (int i = 0; i < 155; i++) {
                int x2 = random.nextInt(width);
                int y2 = random.nextInt(height);
                int x3 = random.nextInt(12);
                int y3 = random.nextInt(12);
                graphics.drawLine(x2, y2, x2 + x3, y2 + y3);
            }

            // 将认证码显示到图象中
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //graphics.drawString(code, 6, 19);
            graphics.drawString(code, height / 3, width / 3);

            // 图象生效
            graphics.dispose();
            // 输出图象到页面
            // ImageIO.write(image, "JPEG", out);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Color randomColor(int fc, int bc) { // 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
