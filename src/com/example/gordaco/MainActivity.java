package com.example.gordaco;
import android.view.View;
import android.util.Log;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent; 
//and android.widget.EditText. 
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	/** Called when the user clicks the Entrenamiento button */
	public void _entrenamiento(View view) {
	    Intent intent = new Intent(this, Entrenamientos.class);

	    startActivity(intent);
	    finish();
	}
	public void _ejercicios(View view) {
	    Intent intent = new Intent(this, Ejercicios.class);

	    startActivity(intent);
	    finish();
	}
	public void _peso(View view) {
	    Intent intent = new Intent(this, Peso.class);

	    startActivity(intent);
	    finish();
	}
	public void _acercade(View view) {
	    Intent intent = new Intent(this, Acercade.class);

	    startActivity(intent);
	    finish();
	}
	public void _ayuda(View view) {
	    Intent intent = new Intent(this, Ayuda.class);

	    startActivity(intent);
	    finish();
	}

}
