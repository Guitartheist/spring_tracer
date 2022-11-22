package com.example.demo.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.example.demo.models.AppUser;
import com.example.demo.repos.UserRepo;

@Service
public class UserService {
	private UserRepo userRepo;
	private int imageLength;
	private int imagePreviewLength;
	
	@Autowired
	public UserService(UserRepo userRepo, Environment environment) {
		this.userRepo = userRepo;
		imageLength = Integer.parseInt( environment.getProperty("appUserProfileImageSideLength") );
		imagePreviewLength = Integer.parseInt( environment.getProperty("appUserProfileImagePreviewSideLength") );
	}
	
	public ArrayList<AppUser> findAllUsers() {
		return (ArrayList<AppUser>) userRepo.findAll();
	}
	
	public AppUser findUserById(int id) {
		Optional<AppUser> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u.get();
		}
		return null;
	}
	
    public List<AppUser> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<AppUser> pagedResult = userRepo.findAll(paging);

        return pagedResult.toList();
    }
	
	public AppUser findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}
	
	public AppUser findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public AppUser registerUser(AppUser u) {
		if (findUserByUsername(u.getUsername())!=null) {
			return null;
		}
		if (findUserByEmail(u.getEmail())!=null) {
			return null;
		}
		if (u.getProfileImage()!=null && u.getProfileImage().length()>0)
			u.setProfileImage(squareAndResizeImageString(u.getProfileImage()));
		return userRepo.save(u);
	}
	
	public AppUser updateUser(AppUser u) {
		u.setProfileImage(squareAndResizeImageString(u.getProfileImage()));
		return userRepo.save(u);
	}
	
	public String squareAndResizeImageString(String inputImageString) {
		StringBuilder r = new StringBuilder("data:image/png;base64,");
		String outputImageString = "";
		int skipAfter = inputImageString.indexOf("base64,") + 7;
		InputStream inputStream = new ByteArrayInputStream(Base64Utils.decodeFromString(inputImageString.substring(skipAfter)));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			BufferedImage inputImage = ImageIO.read(inputStream);
			BufferedImage outputImage = new BufferedImage(imageLength, imageLength, java.awt.image.BufferedImage.TYPE_INT_ARGB);
			int inputWidth = inputImage.getWidth();
			int inputHeight = inputImage.getHeight();
			int xOffset = 0;
			int yOffset = 0;
			Graphics2D g;
			if (inputHeight==inputWidth) {
				inputHeight = imageLength;
				inputWidth = imageLength;
			}
			else if (inputWidth>inputHeight) {
				inputHeight = (int) (imageLength * ((double) inputHeight/inputWidth));
				inputWidth = imageLength;
				yOffset = (inputWidth - inputHeight) / 2;
			}
			else if (inputHeight>inputWidth) {
				inputWidth = (int) (imageLength  * ((double) inputWidth/inputHeight));
				inputHeight = imageLength;
				xOffset = (inputHeight - inputWidth) / 2;
			}
			g = outputImage.createGraphics();
			System.out.println(xOffset + " " + yOffset + " " + inputWidth + " " + inputHeight);
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
