package com.yuyu.soft.base.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.jsonplugin.annotations.JSON;
import com.yuyu.soft.util.Constants;
import com.yuyu.soft.util.ImageUtil;

/**生成验证码
 *                       
 * @Filename: ImageController.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
@Controller
@RequestMapping("/image")
public class ImageController {
	private boolean ok=false;
	private String number;
	
	private InputStream imageStream;
	@JSON(serialize = false)
	public InputStream getImageStream(){
		return imageStream;
	}
	
	public void setImageStream(InputStream imageStream){
		this.imageStream=imageStream;
	}
	
	public String validNumber(HttpServletRequest request) throws Exception{
		String scode = (String)request.getSession().getAttribute(Constants.IMAGE_CODE);
		String numfin=number.toUpperCase();
		if(numfin.equals(scode)){
			ok=true;
		}else{
			ok=false;
		}
		return "validnumber";
	}
	
	/**生成验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image.htm" , method = { RequestMethod.GET })
	public void creatImage(HttpServletRequest request, 
			HttpServletResponse response){
		Map<String,BufferedImage> map = ImageUtil.getImage();
		String imageCode = map.keySet().iterator().next();
		request.getSession(false).setAttribute(Constants.IMAGE_CODE, imageCode);
		BufferedImage image = map.get(imageCode);
		response.setHeader("Pragma", "No-cache");  
		response.setHeader("Cache-Control", "No-cache");  
		response.setDateHeader("Expires", -1);  
		//指定生成的响应图片  
		response.setContentType("image/jpeg");  
		try {
			imageStream = ImageUtil.getImageStream(image);
			ServletOutputStream responseOutputStream = response.getOutputStream();
		    
		    ImageIO.write(image, "JPEG", responseOutputStream);
		    

		    responseOutputStream.flush();
		    responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return imageStream;
	}
	
	/**校验验证码是否正确
	 * @param request
	 * @param response
	 * @param code
	 */
	@RequestMapping(value = "/validateCode.htm", method = { RequestMethod.GET })
	public void verify_code(HttpServletRequest request,
			HttpServletResponse response, String code) {
		boolean ret = true;
		String scode = (String)request.getSession().getAttribute(Constants.IMAGE_CODE);
		String numfin = code.toUpperCase();
		if(!numfin.equals(scode)){
			ret = false;
		}
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
	@JSON(serialize=false)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
