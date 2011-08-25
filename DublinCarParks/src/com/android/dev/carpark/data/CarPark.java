package com.android.dev.carpark.data;

import java.io.Serializable;

import com.google.android.maps.GeoPoint;

public class CarPark implements Serializable {

  private static final long serialVersionUID = 1L;
  
	private int id;
	private String name;
	private String address;
	private GeoPoint location;
	private String url;
	private String freeSpaces;
	
	public int getId() {
  	return id;
  }
	public void setId(int id) {
  	this.id = id;
  }
	public String getName() {
  	return name;
  }
	public void setName(String name) {
  	this.name = name;
  }
	public String getAddress() {
  	return address;
  }
	public void setAddress(String address) {
  	this.address = address;
  }
	public GeoPoint getLocation() {
  	return location;
  }
	public void setLocation(GeoPoint location) {
  	this.location = location;
  }
	public String getUrl() {
  	return url;
  }
	public void setUrl(String url) {
  	this.url = url;
  }
	public void setFreeSpaces(String freeSpaces) {
	  this.freeSpaces = freeSpaces;
  }
	public String getFreeSpaces() {
	  return freeSpaces;
  }
		
}
