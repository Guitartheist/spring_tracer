package com.example.demo.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class ImageUtil {
	public String squareAndResizeImageString(String inputImageString, int squareSide) {
		StringBuilder r = new StringBuilder("data:image/png;base64,");
		String outputImageString = "";
		int skipAfter = inputImageString.indexOf("base64,") + 7;
		InputStream inputStream = new ByteArrayInputStream(Base64Utils.decodeFromString(inputImageString.substring(skipAfter)));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			BufferedImage inputImage = ImageIO.read(inputStream);
			BufferedImage outputImage = new BufferedImage(squareSide, squareSide, java.awt.image.BufferedImage.TYPE_INT_ARGB);
			int inputWidth = inputImage.getWidth();
			int inputHeight = inputImage.getHeight();
			int xOffset = 0;
			int yOffset = 0;
			Graphics2D g;
			if (inputHeight==inputWidth) {
				inputHeight = squareSide;
				inputWidth = squareSide;
			}
			else if (inputWidth>inputHeight) {
				inputHeight = (int) (squareSide * ((double) inputHeight/inputWidth));
				inputWidth = squareSide;
				yOffset = (inputWidth - inputHeight) / 2;
			}
			else if (inputHeight>inputWidth) {
				inputWidth = (int) (squareSide  * ((double) inputWidth/inputHeight));
				inputHeight = squareSide;
				xOffset = (inputHeight - inputWidth) / 2;
			}
			g = outputImage.createGraphics();
			g.drawImage(inputImage, xOffset, yOffset, inputWidth, inputHeight, null);
			g.dispose();
			ImageIO.write(outputImage, "png", outputStream);
			outputImageString = Base64Utils.encodeToString(outputStream.toByteArray());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		r.append(outputImageString);
		return r.toString();
	}
}
