package com.zlutil.tools.toolpackage.JavaBasic.MyIO;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Thumb {
    public static void main(String[] args) throws IOException {
        //transformByThumb("C:\\Users\\12733\\Desktop\\Windows聚焦图片\\11.jpg","C:\\Users\\12733\\Desktop\\Windows聚焦图片\\22.jpg");

        transform("C:\\Users\\12733\\Desktop\\Windows聚焦图片\\11.jpg", "C:\\Users\\12733\\Desktop\\Windows聚焦图片\\33.jpg");
    }

    public static void transform(String localFilePath, String tblocalFilePath) throws IOException {
        File fi = new File(localFilePath); //大图文件
        File fo = new File(tblocalFilePath); //将要转换出的小图文件
        int nw = 100;

        AffineTransform transform = new AffineTransform();
        BufferedImage bis = ImageIO.read(fi); //读取图片
        int w = bis.getWidth();
        int h = bis.getHeight();
        //double scale = (double)w/h;
        int nh = (nw * h) / w;
        double sx = (double) nw / w;
        double sy = (double) nh / h;
        transform.setToScale(sx, sy); //setToScale(double sx, double sy) 将此变换设置为缩放变换。
        AffineTransformOp ato = new AffineTransformOp(transform, null);
        //此处修改为与原图片一样的格式
        BufferedImage bid = new BufferedImage(nw, nh, bis.getType());
        ato.filter(bis, bid);
        ImageIO.write(bid, "bmp", fo);
    }

    public static void transformByThumb(String localFilePath, String tblocalFilePath) throws IOException {
        Thumbnails.of(localFilePath).scale(0.25f).toFile(tblocalFilePath);
    }
}
