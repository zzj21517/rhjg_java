package com.lxh.rhjg.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

public class CreateMatrixToImage {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     *生成二维码图片，返回Image对象
     *徐李
     *2017年5月2日
     */
    public static Image writeQrCodeContent(String text, int width, int height) throws Exception {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        bitMatrix = deleteWhite(bitMatrix);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        writeToStream(bitMatrix, "JPG", imageStream);
        byte[] tagInfo = imageStream.toByteArray();
        InputStream buffin = new ByteArrayInputStream(tagInfo);
        BufferedImage bi;
        bi = ImageIO.read(buffin);
        java.awt.Image im = (java.awt.Image) bi;
        return im;
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 删除二维码白边
     * 徐李
     * 2017年5月2日
     * */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

    /**
     *生成条形码图片Code128码，返回Image对象
     *2017年5月2日
     */
    public static Image writeBarCodeContent(String text, int width, int height) throws Exception {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height, hints);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        writeToStream(bitMatrix, "JPG", imageStream);
        byte[] tagInfo = imageStream.toByteArray();
        InputStream buffin = new ByteArrayInputStream(tagInfo);
        BufferedImage bi;
        bi = ImageIO.read(buffin);
        java.awt.Image im = (java.awt.Image) bi;
        return im;
    }
}
