package com.application.android.amazon;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AmazonProductDetailLookup extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init() {
		Button button = (Button)findViewById(R.id.scanId);
		button.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		IntentIntegrator.initiateScan(AmazonProductDetailLookup.this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case IntentIntegrator.REQUEST_CODE: {
			if (resultCode != RESULT_CANCELED) {
				IntentResult scanResult =
						IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
				if (scanResult != null) {
					String upc = scanResult.getContents();
										
					Log.d("DEBUG", "Done with scan. ISBN is: " + upc);
					
					Log.d("DEBUG", "Kicking off Lookup Activity.");
					
					// Got barcode... So will kick off the Intent to lookup Amazon for Book ISBN
					Intent amazonLookupIntent = new Intent(this, AmazonRetrieveDetail.class);					
					amazonLookupIntent.putExtra("BOOK_ISBN", upc);					
					startActivity(amazonLookupIntent);
				}
			}
			break;
		}
		}

	}

}