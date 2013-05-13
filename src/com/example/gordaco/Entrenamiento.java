package com.example.gordaco;

import java.util.List;

import com.example.gordaco.Ejercicios.myOnItemSelectedListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

public class Entrenamiento extends Activity {
	private SQLiteDatabase database;
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
    protected Spinner spinner;
    protected String mail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entrenamiento);

		spinner = (Spinner) findViewById(R.id.spinner4);

        /*
         * Create a backing mLocalAdapter for the Spinner from a list of the
         * planets. The list is defined by XML in the strings.xml file.
         */
		loadSpinnerData();
        
        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.mAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);
		// Show the Up button in the action bar.
		//setupActionBar();
	}
	//Para rellenar el spinne
	private void loadSpinnerData() {
        // database handler
        MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
 
        // Spinner Drop down elements
        List<String> lables = db.getEjs(" ");

 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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
		getMenuInflater().inflate(R.menu.entrenamiento, menu);
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

	public void _atras(View view) {
	    Intent intent = new Intent(this, MainActivity.class);

	    startActivity(intent);
	    finish();
	}
	public void nuevoEntrenam(View view) throws NumberFormatException{
		MySQLiteHelper db = new MySQLiteHelper(getApplicationContext());
		int rep;
		int ser;
		String nom;
		
		try{
			//obtengo nombre
			EditText editText = (EditText) findViewById(R.id.editText3);
			nom = editText.getText().toString();
			//obtengo series
			EditText editText2 = (EditText) findViewById(R.id.editText2);
			String s = editText2.getText().toString();
			ser = Integer.parseInt(s);
			//obtengo repeticiones
			EditText editText3 = (EditText) findViewById(R.id.editText1); 
			String s2 = editText3.getText().toString();
			 rep =  Integer.parseInt(s2);
		}catch(NumberFormatException e){
			Toast.makeText(getApplicationContext(), "Intorduce valores v‡lidos", Toast.LENGTH_LONG).show();
			return;
		}
		
		//if (nom=="" || ser==0 || rep==0 ){
		//	Toast.makeText(getApplicationContext(), "Intorduce valores v‡lidos", Toast.LENGTH_LONG).show();
		//}else{
		 
        db.insertEntrenamiento(nom ,mSelection, ser, rep );
        TextView t = (TextView) findViewById(R.id.textView2);
        String antes= t.getText().toString();
        
        t.setText(antes+"++Ejercicio: "+mSelection+", Series: "+ser+", Reps: "+rep+"\n");
		//}
        return;
	
	}
	public void email(String mail){
		TextView t = (TextView) findViewById(R.id.textView2);
        String pant= t.getText().toString();
        
        
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("plain/text");//message/rfc822
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
		i.putExtra(Intent.EXTRA_SUBJECT, "Entrenamiento");
		i.putExtra(Intent.EXTRA_TEXT   , pant);
		try {
		  //startActivity(i);
		    startActivity(Intent.createChooser(i, "Mandar email..."));
		    Toast.makeText(this, "e-mail enviado", Toast.LENGTH_SHORT).show();
		    
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(this, "No hay clientes de email instalados", Toast.LENGTH_SHORT).show();
		}
	}
	public void mandarEmail(View v){
		
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final EditText input = new EditText(this); 
        builder.setTitle("Email");  
        //builder.setMessage("Enter Pin :");  
       builder.setView(input)
        // Add action buttons
               .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
     
                       mail= input.getText().toString();
                       email(mail);
                   }
               })
               .setNegativeButton(R.string.atras, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       
                   }
               });      
        builder.create().show();
		
		
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

          Entrenamiento.this.mPos = pos;
          Entrenamiento.this.mSelection = parent.getItemAtPosition(pos).toString();



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
