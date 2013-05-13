package com.example.gordaco;



import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 15;
 
    // Nombre de la BD
    private static final String DATABASE_NAME = "gordacoDB2";
 
    // Nombre de las tablas
    private static final String TABLE_NAME = "ents";
    private static final String TABLE_NAME2 = "hist";
    private static final String TABLE_NAME3 = "ejs";
 
    // Nombres de las columnas de la tabla 1
    private static final String KEY_ID = "id";
    private static final String KEY_ENTRENAMIENTO = "entrenamiento";
    private static final String KEY_EJERCICIO = "ejercicio";
    private static final String KEY_SERIES = "series";
    private static final String KEY_REPETICIONES = "repeticiones";
 // Nombres de las columnas de la tabla 2
    private static final String KEY_ID2 = "id2";
    private static final String KEY_FECHA = "fecha";
    private static final String KEY_PESO = "peso";
 // Nombres de las columnas de la tabla 3
    private static final String KEY_ID3 = "id3";
    private static final String KEY_ZONA = "zona";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_DESCRIPCION = "descripcion";
    //private static final String KEY_RUTAFOTO = "rutafoto";
 
    String Insert_Data;
    ContentValues nuevoRegistro;
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Sentencia SQL para crear tabla
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ENTRENAMIENTO + " TEXT,"+ KEY_EJERCICIO + " TEXT," + KEY_SERIES + " INTEGER," + KEY_REPETICIONES + " INTEGER)";
        db.execSQL(CREATE_CATEGORIES_TABLE);//metodo que ejecuta sentencias SQL
        String CREATE_CATEGORIES_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "("
                + KEY_ID2 + " INTEGER PRIMARY KEY," + KEY_FECHA + " TEXT,"+ KEY_PESO + " INTEGER)";
        db.execSQL(CREATE_CATEGORIES_TABLE2);//metodo que ejecuta sentencias SQL
        String CREATE_CATEGORIES_TABLE3 = "CREATE TABLE " + TABLE_NAME3 + "("
                + KEY_ID3 + " INTEGER PRIMARY KEY," + KEY_ZONA + " TEXT,"+ KEY_NOMBRE + " TEXT,"+ KEY_DESCRIPCION + " TEXT)";//+ KEY_RUTAFOTO + " TEXT)";
        db.execSQL(CREATE_CATEGORIES_TABLE3);//metodo que ejecuta sentencias SQL
        
      //Para tener llena la tabla de ejercicios
        String[] a= {"Brazo","Brazo","Pecho","Pecho","Espalda","Espalda","Pierna","Pierna","Abdominales","Abdominales"};
        String[] b= {"Biceps","Triceps","PechoSuperior","PechoInferior","Dorsales","Lumbares","Cuadriceps","Gemelo","AbdominalesCentrales","AbdominalesOblicuos"};
        String[] c= {"Apoyar el codo en la rodilla subir la mancuerna de forma vertical. No extender del todo el brazo en ningun momento.",
        		"Apoyar las manos en las asas y descender con los codos pegados. Que los brazos no pasen de un ángulo de 90 grados.",
        		"Separar las manos de forma simétrica y levantar las pesas. La barra no debe tocar el pecho.",
        		"Apoyar las manos en las asas y descender con los codos pegados. Que los brazos no pasen de un ángulo de 90 grados.",
        		"Agarrar la barra con las manos colocadas de forma simétrica y subir hasta dejar la barra a la altura de la nariz.",
        		"Colocarse sobre la pelota con el abdomen y levantar la parte superior del cuerpo todo lo posible. Manos en la nuca.",
        		"Opcional apoyarse en pelota. Piernas separadas ancho de los hombros y bajar hasta los 90-100 grados en la rodilla.",
        		"Apoyar la punta de los pies y levantar el resto de la pierna con ellas. Opcional aumentar el peso.",
        		"Tumbarse boca arriba con las piernas flexionadas y subir la parte superior del cuerpo hasta separar la espalda del suelo.",
        		"Tumbarse boca arriba con las piernas flexionadas y las manos en la nuca. Tocar con los codos las rodillas."};
         
    //Creamos el registro a insertar como objeto ContentValues
        for(int i=0; i<a.length; i++){
          nuevoRegistro = new ContentValues();
          nuevoRegistro.put("zona", a[i]);
          nuevoRegistro.put("nombre",b[i]);
          nuevoRegistro.put("descripcion",c[i]);

          db.insert(TABLE_NAME3, null, nuevoRegistro);
        }   

    }
 
    // Cuando se actualiza la version de la DB
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
 
        // Create tables again
        onCreate(db);
    }
    //Para modificar un entrenamiento existente
    public void editEntrenamiento(String entren, String ej, int ser, int rep){
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ENTRENAMIENTO, KEY_EJERCICIO}, 
                   "entrenamiento = '"+entren+"' AND ejercicio='"+ej+"'", null, null, null, null);
    	//para comprobar si hay que crear el registro o editarlo
    	boolean exists = (cursor.getCount() > 0);
    	
    	ContentValues editRegistro = new ContentValues();
        editRegistro.put("entrenamiento", entren);
        editRegistro.put("ejercicio",ej);
        editRegistro.put("series",ser);
        editRegistro.put("repeticiones",rep);
        
        if(exists){
        	//edita registro
        	db.update(TABLE_NAME, editRegistro, "entrenamiento='"+entren+"' AND ejercicio='"+ej+"'", null);
        }else{
        	//crea nuevo registro
        	db.insert(TABLE_NAME, null, editRegistro);
            
        	
        }
        db.close();
    }
    //Para borrar un registro de la tabla 1
    public void borraEntrada(String entren, String ej){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "entrenamiento='"+entren+"' AND ejercicio='"+ej+"'",null);
    }
  //Para borrar un registro de la tabla 2
    public void borraEntrada2(int id){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, "id2='"+id+"'",null);
    }
    public void borraTabla2(){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME2, null,null);
    }public void borraEntrenamiento(String ent){
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "entrenamiento='"+ent+"'",null);
    }
    
 
    /**
     * Crea un nuevo registro
     * */
    public void insertEntrenamiento(String entren, String ej, int ser, int rep){
    	
        SQLiteDatabase db = this.getWritableDatabase();
        
      //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("entrenamiento", entren);
        nuevoRegistro.put("ejercicio",ej);
        nuevoRegistro.put("series",ser);
        nuevoRegistro.put("repeticiones",rep);

        //Insertamos el registro en la base de datos

 
        // Inserting Row
        db.insert(TABLE_NAME, null, nuevoRegistro);
        db.close(); // Closing database connection
    }
public void insertPeso(String fech, int pes){
    	
        SQLiteDatabase db = this.getWritableDatabase();
        
      //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("fecha", fech);
        nuevoRegistro.put("peso",pes);


        //Insertamos el registro en la base de datos

 
        // Inserting Row
        db.insert(TABLE_NAME2, null, nuevoRegistro);
        db.close(); // Closing database connection
    }
 
    /**
     * 
     * Obtiene entrenamientos para el spinner
     * */
    public List<String> getEntren(){
        List<String> labels = new ArrayList<String>();
 


        SQLiteDatabase db = this.getReadableDatabase();
        //Que solo coja entrenamiento
        //El primer "true" es para el DISTINCT (no coger elementos repetidos)
        Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ENTRENAMIENTO}, 
                null, null, null, null, null,null);
        Log.e("cursor: "+cursor.getCount(),"cursor: "+cursor.getCount());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return labels;
    }
    //Para obtener als entradas de la tabla 2, para el spinner
    public List<String> getEntradas(){
        List<String> labels = new ArrayList<String>();
 


        SQLiteDatabase db = this.getReadableDatabase();
        //Que solo coja entrenamiento
        //El primer "true" es para el DISTINCT (no coger elementos repetidos)aqui no haria falta
        Cursor cursor = db.query(true, TABLE_NAME2, new String[] { KEY_ID2}, 
                null, null, null, null, null,null);
        
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return labels;
    }
    //Para objener los ejs de la tabla 3
    public List<String> getEjs(String selec){
        List<String> labels = new ArrayList<String>();
 


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        if (selec!=" "){
        cursor = db.query(TABLE_NAME3, new String[] { KEY_NOMBRE}, 
        		"zona = '"+selec+"'", null, null, null,null);
        }else{
        cursor = db.query(TABLE_NAME3, new String[] { KEY_NOMBRE}, 
            		null, null, null, null,null);
        }

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return labels;
    }
    //para obteren los datos de un entrenamiento y meterlos en un textView
    public String getCosas(String entren){

        String todo="";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EJERCICIO, KEY_SERIES, KEY_REPETICIONES}, 
                "entrenamiento = '"+entren+"'", null, null, null, null);
        Log.e("cursor: "+cursor.getCount(),"cursor: "+cursor.getCount());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	todo+="++Ejercicio: "+cursor.getString(0)+",Series: "+cursor.getInt(1)+",Repes: "+cursor.getInt(2)+"\n";
                
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return todo;
    }
    //para obtener la descripcion de un ejercicio
    public String getEj(String nombre){

        String todo="";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME3, new String[] { KEY_DESCRIPCION}, 
                "nombre = '"+nombre+"'", null, null, null, null);
        
        // looping through all rows and adding to list
        		cursor.moveToFirst();
            	todo+=cursor.getString(0);
                

 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return todo;
    }
    
    
    public String getCosas2(){

        String todo="";
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, new String[] { KEY_ID2, KEY_FECHA, KEY_PESO}, 
                null, null, null, null, null);
        Log.e("cursor: "+cursor.getCount(),"cursor: "+cursor.getCount());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	todo+="++ID: "+cursor.getInt(0)+", Fecha: "+cursor.getString(1)+", Peso: "+cursor.getInt(2)+"\n";
                
            } while (cursor.moveToNext());
        }
 
        // closing connection
        cursor.close();
        db.close();
 
        // returning lables
        return todo;
    }
}