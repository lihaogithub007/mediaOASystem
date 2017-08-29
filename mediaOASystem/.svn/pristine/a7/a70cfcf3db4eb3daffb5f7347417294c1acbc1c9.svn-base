package com.yuyu.soft.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public final class ImageUtil {
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I" ,"J" , "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W" ,"X" , "Y",
			"Z"};
	private static final int SIZE = 4;
	private static final int LINES = 20;
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private static final int FONT_SIZE = 60;
	
	public static Map<String,BufferedImage> getImage() {
		StringBuffer sb = new StringBuffer();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		//画随机字符
		for(int i=1;i<=SIZE;i++){
			
			int r = ran.nextInt(chars.length);
			graphic.setColor(getFontRandomColor());
			graphic.setFont(new Font(null,Font.BOLD+Font.ITALIC,FONT_SIZE));
			graphic.drawString(chars[r],(i-1)*WIDTH/SIZE , ran.nextInt(HEIGHT-FONT_SIZE)+FONT_SIZE);
			sb.append(chars[r]);//将字符保存，存入Session
		}
		//画干扰线
		for(int i=1;i<=LINES;i++){
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH),ran.nextInt(HEIGHT));
		}
		Map<String,BufferedImage> map = new HashMap<String,BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}
	
	public static Color getFontRandomColor(){
		Random ran = new Random();
		Color color = new Color(ran.nextInt(128),ran.nextInt(128),ran.nextInt(128));
		return color;
	}
	
	public static Color getRandomColor(){
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
		return color;
	}
	
	public static InputStream getImageStream(BufferedImage image) throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		encoder.encode(image);
		ByteArrayInputStream imageStream = new ByteArrayInputStream(bos.toByteArray());
		return imageStream;
	}
	
}
