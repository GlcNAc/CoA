package prj.coa.tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class ZImgResizer{
	
	private ZImgResizer(){
		
	}
	
	private void imageOp(InputStream inFile, String outFilePath, int width, int height){
		Image image = null;
		try {
			image = ImageIO.read(inFile);
		} catch (IOException e) {
			System.out.println("file path error...");
		}
		
		int originalImageWidth = image.getWidth(null);
		int originalImageHeight = image.getHeight(null);
		
		BufferedImage originalImage = new BufferedImage(
				originalImageWidth,
				originalImageHeight,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		
		BufferedImage changedImage =
			new BufferedImage(
					width,
					height,
					BufferedImage.TYPE_3BYTE_BGR);
		
		double widthBo = (double)width/originalImageWidth;
		double heightBo = (double)width/originalImageHeight;
		
		AffineTransform transform = new AffineTransform();
		transform.setToScale(widthBo, heightBo);
		
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		ato.filter(originalImage, changedImage);
		
		File fo = new File(outFilePath); //将要转换出的小图文件 

		try {
			ImageIO.write(changedImage, "jpeg", fo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void imageOp(String inFilePath, String outFilePath, int width, int height){
		File tempFile = new File(inFilePath);
		Image image = null;
		try {
			image = ImageIO.read(tempFile);
		} catch (IOException e) {
			System.out.println("file path error...");
		}
		
		int originalImageWidth = image.getWidth(null);
		int originalImageHeight = image.getHeight(null);
		
		BufferedImage originalImage = new BufferedImage(
				originalImageWidth,
				originalImageHeight,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = originalImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		
		BufferedImage changedImage =
			new BufferedImage(
					width,
					height,
					BufferedImage.TYPE_3BYTE_BGR);
		
		double widthBo = (double)width/originalImageWidth;
		double heightBo = (double)width/originalImageHeight;
		
		AffineTransform transform = new AffineTransform();
		transform.setToScale(widthBo, heightBo);
		
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		ato.filter(originalImage, changedImage);
		
		File fo = new File(outFilePath); //将要转换出的小图文件 

		try {
			ImageIO.write(changedImage, "jpeg", fo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ZImgResizer t1 = new ZImgResizer();
		t1.imageOp("C:/p02.jpg", "C:/p03.jpg", 400, 300);

		InputStream in = new FileInputStream(new File("C:/p02.jpg"));
		t1.imageOp(in, "C:/p04.jpg", 400, 300);

	}
	
}
