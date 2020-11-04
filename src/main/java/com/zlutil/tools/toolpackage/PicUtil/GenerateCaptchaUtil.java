package com.zlutil.tools.toolpackage.PicUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class GenerateCaptchaUtil {
    final static int width = 120;
    final static int height = 40;
    final static String filePath = "C:\\Users\\12733\\Desktop\\Repositroy\\";
    static String generatedString = "";
    public static String getGeneratedString() {
        return generatedString;
    }

    public void setGeneratedString(String generatedString) {
        this.generatedString = generatedString;
    }
    //@ResponseBody
    public byte[] generateCaptcha() throws IOException {
        String fileName = generateCaptchaText();
        //将获取到的验证码字符赋值给generatedString供之后的验证操作
        setGeneratedString(fileName);
        //得到图片缓冲区
        BufferedImage bi = new BufferedImage
                (width, height, BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150
        //得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.fillRect(0, 0, width, height);//填充一个矩形 左上角坐标(0,0),宽70,高150;填充整张图片
        //设置颜色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);//填充整张图片(其实就是设置背景颜色)
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("黑体", Font.BOLD, 30)); //设置字体:字体、字号、大小
        g2.setColor(Color.BLACK);//设置背景颜色
        int strWidth = g2.getFontMetrics().stringWidth(fileName);
        //向图片上写字符串
        g2.drawString(fileName, (width - strWidth) / 2, height / 2 + 10);
        //保存图片 JPEG表示保存格式

        ImageIO.write(bi, "JPEG", new FileOutputStream(filePath + fileName + ".jpg"));

        FileInputStream fileInputStream = new FileInputStream(
                new File(filePath + fileName + ".jpg"));
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes, 0, fileInputStream.available());
        System.out.println("生成的验证码为：" + getGeneratedString());
        System.out.println(generatedString);
        return bytes;
    }


    public String generateCaptchaText() {
        String orignString = "1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
        String resString = "";
        int resStringLength = 4;//设置验证码字符串长度
        char[] orignChar = orignString.toCharArray();//将给定的字符串转化为数组
        for (int i = 0; i < resStringLength; i++) {
            //根据给定的resStringLength的长度生成指定长度的随机字符串
            resString += String.valueOf(orignChar[(int) (Math.random() * orignString.length())]);
        }
        return resString;
    }

}