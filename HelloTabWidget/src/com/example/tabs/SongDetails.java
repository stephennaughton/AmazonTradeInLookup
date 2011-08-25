package com.example.tabs;

import com.example.data.SongInformation;
import com.example.data.SongsDBAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SongDetails extends Activity {
	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private SongsDBAdapter mDbHelper;
	private Spinner mCategory;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mDbHelper = new SongsDBAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.artist_edit);
		mCategory = (Spinner) findViewById(R.id.category);
		mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
		mBodyText = (EditText) findViewById(R.id.todo_edit_description);

		Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
		mRowId = null;
		Bundle extras = getIntent().getExtras();
		mRowId = (bundle == null) ? null : (Long) bundle
				.getSerializable(SongsDBAdapter.KEY_ROWID);
		if (extras != null) {
			mRowId = extras.getLong(SongsDBAdapter.KEY_ROWID);
		}
		populateFields();
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor todo = mDbHelper.fetchTodo(mRowId);
			startManagingCursor(todo);
			String category = todo.getString(todo
					.getColumnIndexOrThrow(SongsDBAdapter.KEY_TITLE));
			
			for (int i=0; i<mCategory.getCount();i++){
				
				String s = (String) mCategory.getItemAtPosition(i); 
				Log.e(null, s +" " + category);
				if (s.equalsIgnoreCase(category)){
					mCategory.setSelection(i);
				}
			}
			
			mTitleText.setText(todo.getString(todo
					.getColumnIndexOrThrow(SongsDBAdapter.KEY_TITLE)));
			mBodyText.setText(todo.getString(todo
					.getColumnIndexOrThrow(SongsDBAdapter.KEY_DESCRIPTION)));
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(SongsDBAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String category = (String) mCategory.getSelectedItem();
		String summary = mTitleText.getText().toString();
		String description = mBodyText.getText().toString();

		SongInformation songInformation = new SongInformation(); 
		
		songInformation.setArtist(category);
		songInformation.setDescription(description);
		songInformation.setTitle(summary);
		songInformation.setThumbnail("test.png");
		songInformation.setIsbn("12345");

		if (mRowId == null) {
			long id = mDbHelper.createSong(songInformation);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateSong(mRowId, songInformation);
		}
	}
}


