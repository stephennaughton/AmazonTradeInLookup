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

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";	
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_ISBN = "isbn";
	
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
	
	public long createSong(SongInformation songInformation) {
		ContentValues initialValues = createContentValues(songInformation);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean updateSong(long rowId, SongInformation songInfo) {
		ContentValues updateValues = createContentValues(songInfo);

		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	/**
	 * Deletes todo
	 */
	public boolean deleteTodo(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllTodos() {
		
		SongInformation songInformation = new SongInformation("TestTitle", "TestArtist", "image.png", "Blah Blah", "123415");
		
		createSong(songInformation);
		
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_ARTIST, KEY_THUMBNAIL, KEY_DESCRIPTION, KEY_ISBN }, null, null, null,
				null, null);
	}

	/**
	 * Return a Cursor positioned at the defined todo
	 */
	public Cursor fetchTodo(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_TITLE, KEY_ARTIST, KEY_THUMBNAIL, KEY_DESCRIPTION, KEY_ISBN },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
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
