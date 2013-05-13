package com.example.gordaco;

import java.util.Collection;
import java.util.List;

import com.example.gordaco.Entrenamientos.myOnItemSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Ejercicios extends Activity {
	 /**
     * ArrayAdapter connects the spinner widget to array-based data.
     */
	protected int mPos;
    protected String mSelection;
    protected int mPos2;
    protected String mSelection2;
    protected ArrayAdapter<CharSequence> m2Adapter;
    protected ArrayAdapter<String> dataAdapter;

    /**
     *  The initial position of the spinner when it is first installed.
     */
     public static final int DEFAULT_POSITION = 2;

    /**
     * The name of a properties file that stores the position and
     * selection when the activity is not loaded.
     */
    public static final String PREFERENCES_FILE = "SpinnerPrefs";

    /**
     * These values are used to read and write the properties file.
     * PROPERTY_DELIMITER delimits the key and value in a Java properties file.
     * The "marker" strings are used to write the properties into the file
     */
    public static final String PROPERTY_DELIMITER = "=";

    /**
     * The key or label for "position" in the preferences file
     */
    public static final String POSITION_KEY = "Position";

    /**
     * The key or label for "selection" in the preferences file
     */
    public static final String SELECTION_KEY = "Selection";

    public static final String POSITION_MARKER =
            POSITION_KEY + PROPERTY_DELIMITER;

    public static final String SELECTION_MARKER =
            SELECTION_KEY + PROPERTY_DELIMITER;
    
    public final static String EXTRA_MESSAGE = "com.example.gordaco.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.example.gordaco.MESSAGE2";
    Spinner spinner;
    int i=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ejercicios);
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner = (Spinner) findViewById(R.id.spinner1);

        /*
         * Create a backing mLocalAdapter for the Spinner from a list of the
         * planets. The list is defined by XML in the strings.xml file.
         */
		//spinner.setVisibility(View.INVISIBLE);
        this.m2Adapter = ArrayAdapter.createFromResource(this, R.array.Zonas,
                android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(this.m2Adapter);
        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.m2Adapter,null);
        spinner2.setOnItemSelectedListener(spinnerListener);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ejercicios, menu);
		return true;
	}
	public void _atras(View view) {
	    Intent intent = new Intent(this, MainActivity.class);

	    startActivity(intent);
	    finish();
	}
	public void _ejercicio(View v) {
	    Intent intent = new Intent(this, Ejercicio.class);
	    intent.putExtra(EXTRA_MESSAGE, mSelection);
	    intent.putExtra(EXTRA_MESSAGE2, mSelection2);
	    startActivity(intent);
	    finish();
	}
	private void loadSpinnerData(String selec) {
        // database handler
        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
 
        // Spinner Drop down elements
        
        List<String> entradas = db.getEjs(selec);

 
        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, entradas);


        
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,null,this.dataAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);
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
	public class myOnItemSelectedListener implements OnItemSelectedListener {

        /*
         * provide local instances of the mLocalAdapter and the mLocalContext
         */

        ArrayAdapter<CharSequence> mLocalAdapter;
        ArrayAdapter<String> mLocalAdapter2;
        Activity mLocalContext;

        /**
         *  Constructor
         *  @param c - The activity that displays the Spinner.
         *  @param ad - The Adapter view that
         *    controls the Spinner.
         *  Instantiate a new listener object.
         */
    public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad, ArrayAdapter<String> as) {

        this.mLocalContext = c;
        this.mLocalAdapter = ad;
        this.mLocalAdapter2 = as;

      }

      /**
       * When the user selects an item in the spinner, this method is invoked by the callback
       * chain. Android calls the item selected listener for the spinner, which invokes the
       * onItemSelected method.
       *
       * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
       *  android.widget.AdapterView, android.view.View, int, long)
       * @param parent - the AdapterView for this listener
       * @param v - the View for this listener
       * @param pos - the 0-based position of the selection in the mLocalAdapter
       * @param row - the 0-based row number of the selection in the View
       */
      public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {

    	  
    	  int id = parent.getId();
          switch (id) 
          {
                  case R.id.spinner2:
                      Ejercicios.this.mPos = pos;
                      Ejercicios.this.mSelection = parent.getItemAtPosition(pos).toString();
                      //loadSpinnerData(mSelection);
                      //Pa que no aparezca sin seleccionar nada el segundo spinner
                     
                      //if (i>0){
                    	  
                      //spinner.setVisibility(View.VISIBLE);
                      loadSpinnerData(mSelection);
                      
                      
                      //}
                      //i++;
                  break;
              case R.id.spinner1:
            	  
            	  Ejercicios.this.mPos2 = pos;
                  Ejercicios.this.mSelection2 = parent.getItemAtPosition(pos).toString();
                                 	  
                
                      
              break;
          }

  

      }
      

      /**
       * The definition of OnItemSelectedListener requires an override
       * of onNothingSelected(), even though this implementation does not use it.
       * @param parent - The View for this Listener
       */
      public void onNothingSelected(AdapterView<?> parent) {

          // do nothing

      }
  }
	


}
