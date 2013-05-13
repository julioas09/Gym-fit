package com.example.gordaco;


import java.util.List;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
public class Entrenamientos extends Activity {
	protected int mPos;
    protected String mSelection;

    /**
     * ArrayAdapter connects the spinner widget to array-based data.
     */
    protected ArrayAdapter<CharSequence> mAdapter;

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
    protected Spinner spinner;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		setContentView(R.layout.entrenamientos);
		spinner = (Spinner) findViewById(R.id.spinner1);
		loadSpinnerData();
		

        /*
         * Create a backing mLocalAdapter for the Spinner from a list of the
         * planets. The list is defined by XML in the strings.xml file.
         */

        //this.mAdapter = ArrayAdapter.createFromResource(this, R.array.Entrenamientos,
         //       android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(this.mAdapter);
		// Show the Up button in the action bar.
		//setupActionBar();
        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.mAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);
	}
	private void loadSpinnerData() {
        // database handler
        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
 
        // Spinner Drop down elements
        List<String> lables = db.getEntren();

 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void notification(){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(android.R.drawable.ic_dialog_alert)
		        .setContentTitle("Entrenamiento Borrado")
		        .setContentText(mSelection).setAutoCancel(true);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}
	public void borraEntrenamiento(View v){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_title).setIcon(android.R.drawable.ic_dialog_alert).setMessage(R.string.dialog_message);
		// Add the buttons
		builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		        	   MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
		        	   db.borraEntrenamiento(mSelection);
		       		db.close();
		       		notification();
		       		Bundle tempBundle = new Bundle();
		       		onCreate(tempBundle);
		           }
		       });
		builder.setNegativeButton(R.string.atras, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });



		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
		
	}
 


	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	public void _nuevo(View view) {
	    Intent intent = new Intent(this, Entrenamiento.class);

	    startActivity(intent);
	    finish();
	}
	public void _atras(View view) {
	    Intent intent = new Intent(this, MainActivity.class);

	    startActivity(intent);
	    finish();
	}
	public void _entren(View view) {
	    Intent intent = new Intent(this, Consulta.class);
	    intent.putExtra(EXTRA_MESSAGE, mSelection);

	    startActivity(intent);
	    finish();
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
        Activity mLocalContext;

        /**
         *  Constructor
         *  @param c - The activity that displays the Spinner.
         *  @param ad - The Adapter view that
         *    controls the Spinner.
         *  Instantiate a new listener object.
         */
        public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {

          this.mLocalContext = c;
          this.mLocalAdapter = ad;

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

            Entrenamientos.this.mPos = pos;
            Entrenamientos.this.mSelection = parent.getItemAtPosition(pos).toString();
            /*
             * Set the value of the text field in the UI
             */

            //String item = parent.getItemAtPosition(pos).toString();
            //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            
    	    //startActivity(intent);
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
