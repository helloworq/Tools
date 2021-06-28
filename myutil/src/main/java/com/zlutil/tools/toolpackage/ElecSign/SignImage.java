package com.zlutil.tools.toolpackage.ElecSign;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;


public class SignImage {


    /**
     * @param userName   String 医生名字
     * @param companyName String 医生名称
     * @param date         String 签名日期
     *                     <p>
     *                     图片高度
     * @param jpgname      String jpg图片名
     * @return
     */

    public static boolean createSignTextImg(
            String userName, //
            String companyName, //
            String date,
            String jpgname) {
        int width = 255;
        int height = 100;
        FileOutputStream out = null;
        //背景色
        Color bgcolor = Color.WHITE;
        //字色
        Color fontcolor = Color.RED;
        Font doctorNameFont = new Font(null, Font.BOLD, 20);
        Font othorTextFont = new Font(null, Font.BOLD, 18);
        try { // 宽度 高度
            BufferedImage bimage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.setColor(bgcolor); // 背景色
            // 画一个矩形
            g.fillRect(0, 0, width, height);
            // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.RED);
            g.fillRect(0, 0, 8, height);
            g.fillRect(0, 0, width, 8);
            g.fillRect(0, height - 8, width, height);
            g.fillRect(width - 8, 0, width, height);
            g.setColor(fontcolor); // 字的颜色
            g.setFont(doctorNameFont); // 字体字形字号
            FontMetrics fm = FontDesignMetrics.getMetrics(doctorNameFont);
            int font1_Hight = fm.getHeight();
            int strWidth = fm.stringWidth(userName);
            int y = 35;
            int x = (width - strWidth) / 2;
            g.drawString(userName, x, y); // 在指定坐标除添加文字
            g.setFont(othorTextFont); // 字体字形字号
            fm = FontDesignMetrics.getMetrics(othorTextFont);
            int font2_Hight = fm.getHeight();
            strWidth = fm.stringWidth(companyName);
            x = (width - strWidth) / 2;
            g.drawString(companyName, x, y + font1_Hight); // 在指定坐标除添加文字
            strWidth = fm.stringWidth(date);
            x = (width - strWidth) / 2;
            g.drawString(date, x, y + font1_Hight + font2_Hight); // 在指定坐标除添加文字
            g.dispose();
            out = new FileOutputStream(jpgname); // 指定输出文件
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(50f, true);
            encoder.encode(bimage, param); // 存盘
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        createSignTextImg("周大帅", "上海数慧", "2021-06-28", "sign.jpg");
    }
}
