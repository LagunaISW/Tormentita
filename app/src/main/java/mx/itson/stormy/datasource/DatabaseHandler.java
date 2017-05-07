package mx.itson.stormy.datasource;
/**
 * Created by LagunaISW on 7/05/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "forecast.db";
    private static final String TABLE_WEATHER = "weather";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_PRECIPITATION = "precipitation";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_WEATHER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEMPERATURE + " TEXT, " +
                COLUMN_HUMIDITY + " TEXT, " +
                COLUMN_PRECIPITATION + " TEXT, " +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(db);
    }
/*  >>>>>>>>>>>>>>>>>>>>.EJEMPLOS <<<<<<<<<<<<<<<<<<<<<
    //Añade un nuevo Row a la Base de Datos

    public void addPersona(Persona persona) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMPERATURE, persona.get_nombre());
        values.put(COLUMN_HUMIDITY, persona.get_apellido());
        values.put(COLUMN_PRECIPITATION, persona.get_edad());
        values.put(COLUMN_TEL, persona.get_telefono());
        values.put(COLUMN_EMAIL, persona.get_email());
        values.put(COLUMN_DIRECCION, persona.get_direccion());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WEATHER, null, values);
        db.close();

    }

    public void updatepersona(Persona persona){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMPERATURE, persona.get_nombre());
        values.put(COLUMN_HUMIDITY, persona.get_apellido());
        values.put(COLUMN_PRECIPITATION, persona.get_edad());
        values.put(COLUMN_TEL, persona.get_telefono());
        values.put(COLUMN_EMAIL, persona.get_email());
        values.put(COLUMN_DIRECCION, persona.get_direccion());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_WEATHER, values, COLUMN_ID + "= ?", new String[] { String.valueOf(persona.get_id())});
        db.close();

    }

    // Borrar una persona de la Base de Datos

    public void borrarPersona(int persona_id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_WEATHER + " WHERE " + COLUMN_ID + " = " + persona_id + ";");
        db.close();
    }

    //listar por id

    public Cursor personabyid(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WEATHER + " WHERE " + COLUMN_ID + " = " + id + ";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    //listar a todas las personas
    public Cursor listarpersonas(){
        SQLiteDatabase db = getReadableDatabase();
        String query = ("SELECT * FROM " + TABLE_WEATHER + " WHERE 1 ORDER BY " + COLUMN_HUMIDITY + ";");
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }
*/

}
