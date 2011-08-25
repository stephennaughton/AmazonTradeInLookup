package com.example.data;

public class SongInformation {
	
	private String title;
	private String artist;
	private String thumbnail;
	private String description;
	private String isbn;

	public SongInformation() {}
	
	public SongInformation(String title, String artist, String thumbnail,
      String description, String isbn) {
	  
	  this.title = title;
	  this.artist = artist;
	  this.thumbnail = thumbnail;
	  this.description = description;
	  this.isbn = isbn;
  }
	
	public String getTitle() {
  	return title;
  }
	public void setTitle(String title) {
  	this.title = title;
  }
	public String getArtist() {
  	return artist;
  }
	public void setArtist(String artist) {
  	this.artist = artist;
  }
	public String getThumbnail() {
  	return thumbnail;
  }
	public void setThumbnail(String thumbnail) {
  	this.thumbnail = thumbnail;
  }
	public String getDescription() {
  	return description;
  }
	public void setDescription(String description) {
  	this.description = description;
  }
	public String getIsbn() {
  	return isbn;
  }
	public void setIsbn(String isbn) {
  	this.isbn = isbn;
  }

	@Override
  public String toString() {
	  return "SongInformation [title=" + title + ", artist=" + artist
	      + ", thumbnail=" + thumbnail + ", description=" + description
	      + ", isbn=" + isbn + "]";
  }
	
}
