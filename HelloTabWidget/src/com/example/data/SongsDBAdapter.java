package com.example.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SongsDBAdapter {
	
	private Context context;
	private SQLiteDatabase database;
	private SongsDatabaseHelper dbHelper;

	private static final String KEY_ROWID = "_id";
	private static final String KEY_TITLE = "title";	
	private static final String KEY_ARTIST = "artist";
	private static final String KEY_THUMBNAIL = "thumbnail";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_ISBN = "isbn";
	
	private static final String DATABASE_TABLE = "song";
	
	public SongsDBAdapter(Context context) {
		this.context = context;
	}

	public SongsDBAdapter open() throws SQLException {
		dbHelper = new SongsDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Add a new song to the database 
	 * @param songInformation
	 * @return
	 */
	public long createSong(SongInformation songInformation) {
		ContentValues initialValues = createContentValues(songInformation);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/** Update and existing song based on rowid 
	 * 
	 * @param rowId
	 * @param songInfo
	 * @return
	 */
	public boolean updateSong(long rowId, SongInformation songInfo) {
		ContentValues updateValues = createContentValues(songInfo);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * Deletes song
	 */
	public boolean deleteSong(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */
	public SongsList fetchAllSongs() {
		
		SongsList songList = new SongsList();
		
		Cursor results = database.query(DATABASE_TABLE, null, null, 
					null, null, null, null);	
		
		results.moveToFirst();		
		while (!results.isAfterLast()) {
			SongInformation thisSong = new SongInformation();
			thisSong.setId(results.getInt(0));
			thisSong.setTitle(results.getString(1));
			thisSong.setArtist(results.getString(2));
			thisSong.setThumbnail(results.getString(3));
			thisSong.setDescription(results.getString(4));
			thisSong.setIsbn(results.getString(5));			
			
			songList.addSong(thisSong);
		}		
		return songList;
	}

	/**
	 * Return a Cursor positioned at the defined todo
	 */
	public SongInformation fetchSong(long rowId) throws SQLException {
		
		SongInformation thisSong = null;
		
		Cursor result = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_THUMBNAIL, KEY_DESCRIPTION, KEY_ISBN },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		
		if (result != null) {
			result.moveToFirst();
			
			thisSong = new SongInformation();
			thisSong.setId(result.getInt(0));
			thisSong.setTitle(result.getString(1));
			thisSong.setArtist(result.getString(2));
			thisSong.setThumbnail(result.getString(3));
			thisSong.setDescription(result.getString(4));
			thisSong.setIsbn(result.getString(5));	
		}		
		return thisSong;
	}

	private ContentValues createContentValues(SongInformation songInfo) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, songInfo.getTitle());
		values.put(KEY_ARTIST, songInfo.getArtist());
		values.put(KEY_THUMBNAIL, songInfo.getThumbnail());
		values.put(KEY_DESCRIPTION, songInfo.getDescription());
		values.put(KEY_ISBN, songInfo.getIsbn());
		return values;
	}	

}
