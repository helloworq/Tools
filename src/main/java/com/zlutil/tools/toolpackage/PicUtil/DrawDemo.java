package com.zlutil.tools.toolpackage.PicUtil;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawDemo {
    final static int width = 120;
    final static int height = 40;
    static String filePath = "C:\\Users\\12733\\Desktop\\Repositroy\\";
    static String fileSuffixes = ".jpg";

    public static void creatPic(String fileName) throws IOException {
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
        g2.drawRect(0, 0, width - 1, height - 1); //画边框
        g2.setFont(new Font("黑体", Font.BOLD, 30)); //设置字体:字体、字号、大小
        g2.setColor(Color.BLACK);//设置背景颜色
        int strWidth = g2.getFontMetrics().stringWidth(fileName);
        //向图片上写字符串
        g2.drawString(fileName, (width - strWidth) / 2, height / 2 + 10);
        //保存图片 JPEG表示保存格式
        ImageIO.write(bi, "JPEG", new FileOutputStream(filePath + fileName + fileSuffixes));
        System.out.println("创建成功" + fileName);
    }

    public static String genrateString() {
        String orignString = "1234567890qwertyuioplkjhgfdsazxcvbnm";
        String resString = "";
        int resStringLength = 4;
        char[] orignChar = orignString.toCharArray();
        char a = 'a';
        for (int i = 0; i < resStringLength; i++) {
            resString += String.valueOf(orignChar[(int) (Math.random() * orignString.length())]);
        }
        return resString;
    }

    public static void main(String[] args) throws IOException {
        //creatPic("test");
        System.out.println(System.getProperty("user.dir"));

    }
}
