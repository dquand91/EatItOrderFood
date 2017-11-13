package com.baitap.quan.eatitorderfood.Model;

/**
 * Created by luong.duong.quan on 11/13/2017.
 */

public class Category {

	private String Name;
	private String Image;

	public Category(){

	}

	public Category(String name, String image) {
		Name = name;
		Image = image;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}
}
