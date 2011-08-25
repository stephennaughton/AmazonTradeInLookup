package com.example.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SongsDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "songsdata";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
				"create table song (_id integer primary key autoincrement, "
														+ "title text not null, " 
														+ "artist text not null, " 
														+ "thumbnail text not null, "
														+ "description text not null, "
														+ "isbn integer not null "
														+");";
	
	public SongsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
	
	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database, e.g. if you increase
	// the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(SongsDatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS todo");
		onCreate(database);
	}

}
