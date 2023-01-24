package com.example.demo.dtos;

import java.util.Objects;

public class AppUserListEntry {
	private String username;
	private String profilePreviewImage;
	
	public AppUserListEntry(String username, String profilePreviewImage) {
		super();
		this.username = username;
		this.profilePreviewImage = profilePreviewImage;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfilePreviewImage() {
		return profilePreviewImage;
	}
	public void setProfilePreviewImage(String profilePreviewImage) {
		this.profilePreviewImage = profilePreviewImage;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(profilePreviewImage, username);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUserListEntry other = (AppUserListEntry) obj;
		return Objects.equals(profilePreviewImage, other.profilePreviewImage)
				&& Objects.equals(username, other.username);
	}
	
	@Override
	public String toString() {
		return "AppUserListEntry [username=" + username + ", profilePreviewImage=" + profilePreviewImage + "]";
	}
}
