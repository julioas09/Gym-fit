package com.example.gordaco;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Ejercicio extends Activity {
	protected String message1;
	protected String message2;
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ejercicio);
		Intent intent = getIntent(); 
		message1 = intent.getStringExtra(Ejercicios.EXTRA_MESSAGE);
		message2 = intent.getStringExtra(Ejercicios.EXTRA_MESSAGE2);
		
		ImageView im = (ImageView) findViewById(R.id.imageView1);
		String ej="images";
		ej= message2.toLowerCase();					
		int id =getResources().getIdentifier(ej, "drawable", getPackageName());
		im.setImageResource(id);
		
		MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
		
		TextView t = (TextView) findViewById(R.id.textView1);
		TextView t2 = (TextView) findViewById(R.id.textView2);
        
        t.setText(message2);
        
        String yeah=db.getEj(message2);
        t2.setText(yeah);
		// Show the Up button in the action bar.
		//setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public void _salir(View view) {
	    Intent intent = new Intent(this, MainActivity.class);

	    startActivity(intent);
	    finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ejercicio, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
