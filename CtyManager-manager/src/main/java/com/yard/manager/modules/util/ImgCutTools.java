package com.yard.manager.modules.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

/**
 * 图片裁剪
 * 
 * @author sdywcd
 * 
 */
public class ImgCutTools {

	public static void main(String[] args) {
		String needCutImgPath = "D:/test/normal.png";
		String savePath = "D:/test/";

        int[] ws;
        int[] hs;
        ws = new int[]{750,718,100,750};
        hs = new int[]{500,450,100,450};
        
		try {
			ImgCutTools.compressImages(needCutImgPath, savePath, ws, hs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void compressImages(String needCutImgPath,
			String targetSavePath, int[] ws, int[] hs) throws IOException {
		if (needCutImgPath == null) {
			throw new IOException("图片不存在1:" + needCutImgPath);
		}
		try {
			
		
		File file = new File(needCutImgPath);
		if (!file.exists()) {
			throw new IOException("图片不存在:" + needCutImgPath);
		}
		if (ws.length>0  && hs.length > 0) {
			// 截取文件名称
			System.out.println("开始裁剪");
			String fileName = file.getName();
			// 文件后缀
			String extName = null;
			// 缩略图文件后缀
			String extName2 = null;
			// 新文件名
			String newFileName = null;
			// 图片类型
			String formatName = null;
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf("."));
				extName2 = extName.toLowerCase();
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				formatName = extName.substring(1, extName.length());
			}
            BufferedImage bufferedImage = ImageIO.read(file);
			//缩略图名称
            for ( int i = 0 ; i < ws.length ; i++ ) {
                newFileName = fileName + extName +"_"+ String.valueOf(ws[i]) + "X"
                        + String.valueOf(hs[i]) ;
                copyImageFile(file, targetSavePath+newFileName);
                BufferedImage zoomImage = compressWithCut(bufferedImage, ws[i], hs[i]);
                if ( zoomImage != null) {
                    ImageIO.write(zoomImage, formatName, new File(targetSavePath
                                + newFileName));
                }
            }
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void compressImages(String needCutImgPath,
			String targetSavePath, int width, int height) throws IOException {
		File file = new File(needCutImgPath);
		if (!file.exists()) {
			throw new IOException("图片不存在:" + needCutImgPath);
		}
		if (width != 0 && height != 0) {
			// 截取文件名称
			String fileName = file.getName();
			// 文件后缀
			String extName = null;
			// 新文件名
			String newFileName = null;
			// 图片类型
			String formatName = null;
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf("."));
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
				formatName = extName.substring(1, extName.length());
			}
			//缩略图名称
			newFileName = fileName + "_" + String.valueOf(width) + "x"
					+ String.valueOf(height) + extName;
			copyImageFile(file, targetSavePath+newFileName);
			BufferedImage bufferedImage = ImageIO.read(file);
			BufferedImage zoomImage = compress(bufferedImage, width, height);
            if ( zoomImage != null) {
                ImageIO.write(zoomImage, formatName, new File(targetSavePath
                            + newFileName));
            }

		}
	}
	/**
	 * 压缩图片
	 * 
	 * @param bufferedImage
	 * @param width
	 * @param height
	 * @return
	 */
	private static BufferedImage compressWithCut(BufferedImage bufferedImage,
			int width, int height) {

        int ow = bufferedImage.getWidth();
        int oh = bufferedImage.getHeight();
        if ( ow < width || oh < height) {
            return null;
        }

        int sx1 = 0 ,sx2=0,sy1=0,sy2=0;
        double scalew = ((double)ow)/((double)width);
        double scaleh = ((double)oh)/((double)height);

        scalew  = Math.min(scalew,scaleh);
        int nw = (int)(scalew * width);
        int nh = (int)(scalew * height);

        sx1 = (ow-nw)/2;
        sy1 = (oh-nh)/2;
        sx2 = sx1 +nw;
        sy2= sy1 + nh;

        String info = "w="+width+",h="+height+ ",sx1="+sx1 +"sx2="+sx2+"sy1="+sy1+"sy2="+sy2;
        System.out.println(info);


		BufferedImage compressImage = new BufferedImage(width, height,
				bufferedImage.getType());
		//Image image = bufferedImage.getScaledInstance(ow, oh,
	//			Image.SCALE_SMOOTH);
		Graphics graphics = compressImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.drawImage(bufferedImage, 0, 0,width,height,sx1,sy1,sx2,sy2,Color.WHITE, null);
		//graphics.drawImage(image, 0, 0, null);
        //graphics.drawString(info,50,0);
		return compressImage;
	}

	/**
	 * 压缩图片
	 * 
	 * @param bufferedImage
	 * @param width
	 * @param height
	 * @return
	 */
	private static BufferedImage compress(BufferedImage bufferedImage,
			int width, int height) {
		BufferedImage compressImage = new BufferedImage(width, height,
				bufferedImage.getType());
		Image image = bufferedImage.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		Graphics graphics = compressImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.drawImage(image, 0, 0, null);
		return compressImage;
	}

	/**
	 * 复制图片文件进行缩略图处理
	 * @param oldFile
	 * @param newFilePath
	 * @throws IOException
	 */
	private static void copyImageFile(File oldFile,String newFilePath)
			throws IOException {
		File newImageFile=new File(newFilePath);
		if(!newImageFile.exists()){
			newImageFile.createNewFile();
		}
		FileInputStream in=new FileInputStream(
				oldFile);
		FileOutputStream out = new FileOutputStream(newFilePath);
		IOUtils.copy(in, out);
		in.close();
		out.flush();
		out.close();

	}

}
