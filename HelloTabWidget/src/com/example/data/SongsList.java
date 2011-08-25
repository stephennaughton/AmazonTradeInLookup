package com.example.data;

import java.util.ArrayList;
import java.util.List;

public class SongsList {

	private List<SongInformation> songsList = new ArrayList<SongInformation>();

	public List<SongInformation> getSongsList() {
  	return songsList;
  }

	public void addSong(SongInformation song) {
  	this.songsList.add(song);
  }	
	
}
