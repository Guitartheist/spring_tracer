package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.EmailAlreadyRegisteredException;
import com.example.demo.exceptions.UsernameAlreadyRegisteredException;
import com.example.demo.models.AppUser;
import com.example.demo.repos.UserRepo;
import com.example.demo.util.ImageUtil;

@Service
public class UserService {
	private UserRepo userRepo;
	private int imageLength;
	private int imagePreviewLength;
	private ImageUtil imageUtil;
	
	@Autowired
	public UserService(UserRepo userRepo, Environment environment, ImageUtil imageUtil) {
		this.userRepo = userRepo;
		imageLength = Integer.parseInt( environment.getProperty("appUserProfileImageSideLength") );
		imagePreviewLength = Integer.parseInt( environment.getProperty("appUserProfileImagePreviewSideLength") );
		this.imageUtil = imageUtil;
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
	
	public AppUser registerUser(AppUser u) throws UsernameAlreadyRegisteredException, EmailAlreadyRegisteredException {
		if (findUserByUsername(u.getUsername())!=null) {
			throw new UsernameAlreadyRegisteredException(u.getUsername());
		}
		if (findUserByEmail(u.getEmail())!=null) {
			throw new EmailAlreadyRegisteredException(u.getEmail());
		}
		if (u.getProfileImage()!=null && u.getProfileImage().length()>0) {
			u.setProfileImage(imageUtil.squareAndResizeImageString(u.getProfileImage(), imageLength));
			u.setProfilePreviewImage(imageUtil.squareAndResizeImageString(u.getProfileImage(), imagePreviewLength));
		}
		return userRepo.save(u);
	}
	
	public AppUser updateUser(AppUser u) {
		u.setProfileImage(imageUtil.squareAndResizeImageString(u.getProfileImage(), imageLength));
		u.setProfilePreviewImage(imageUtil.squareAndResizeImageString(u.getProfileImage(), imagePreviewLength));
		return userRepo.save(u);
	}
}
