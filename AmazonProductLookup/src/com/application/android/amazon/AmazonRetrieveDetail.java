package com.application.android.amazon;

import java.util.Map;

import com.amazon.prodadvertising.api.ProductLookup;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class AmazonRetrieveDetail extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proddetail);
		doLookup();
	}

	private void doLookup() {
		ProductLookup detailLookup = new ProductLookup();
		String isbn = (String) getIntent().getExtras().get("BOOK_ISBN");
		Map<String, String> detail = detailLookup.lookupAmazonProductInfo(isbn);
		
		Log.d("DEBUG", detail.toString());
		
		EditText textField = (EditText) findViewById(R.id.productDetail);
		
		for (String s:detail.keySet()) {
			textField.append(s + ": " + detail.get(s) + "\n");
		}
		
	}
	
}
