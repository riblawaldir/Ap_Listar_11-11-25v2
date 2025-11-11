package com.example.ap_listar_11_11_25;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "HabitusPlusDB.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys = ON;");

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Usuarios (" +
                        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL," +
                        "email TEXT UNIQUE NOT NULL," +
                        "fecha_registro TEXT NOT NULL" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Habitos (" +
                        "id_habito INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_usuario INTEGER NOT NULL," +
                        "titulo TEXT NOT NULL," +
                        "descripcion TEXT," +
                        "tipo TEXT," +
                        "objetivo INTEGER," +
                        "color_hex TEXT," +
                        "activo INTEGER DEFAULT 1," +
                        "FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS RegistrosDiarios (" +
                        "id_registro INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_habito INTEGER NOT NULL," +
                        "fecha TEXT NOT NULL," +
                        "cumplido INTEGER NOT NULL," +
                        "hora TEXT," +
                        "nota TEXT," +
                        "FOREIGN KEY(id_habito) REFERENCES Habitos(id_habito)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS SensoresUso (" +
                        "id_sensor INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_habito INTEGER NOT NULL," +
                        "tipo_sensor TEXT NOT NULL," +
                        "valor REAL," +
                        "fecha TEXT NOT NULL," +
                        "hora TEXT NOT NULL," +
                        "FOREIGN KEY(id_habito) REFERENCES Habitos(id_habito)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Ubicaciones (" +
                        "id_ubicacion INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_habito INTEGER NOT NULL," +
                        "lat REAL NOT NULL," +
                        "lon REAL NOT NULL," +
                        "precision REAL," +
                        "fecha TEXT NOT NULL," +
                        "hora TEXT NOT NULL," +
                        "FOREIGN KEY(id_habito) REFERENCES Habitos(id_habito)" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Rol(" +
                        "id_rol INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT NOT NULL," +
                        "descripcion TEXT NOT NULL" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Ubicaciones");
        db.execSQL("DROP TABLE IF EXISTS SensoresUso");
        db.execSQL("DROP TABLE IF EXISTS RegistrosDiarios");
        db.execSQL("DROP TABLE IF EXISTS Habitos");
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL("DROP TABLE IF EXISTS Rol");

        onCreate(db);
    }


    public boolean insertarRol(String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);

        long resultado = db.insert("Rol", null, values);
        return resultado != -1;
    }
    public boolean insertarRol(Rol rol){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", rol.getNombre());
        values.put("descripcion", rol.getDescripcion());
        long resultado = db.insert("Rol", null, values);
        return resultado != -1;

    }

    public ArrayList<Rol> obtenerRoles() {
        ArrayList<Rol> roles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Rol";
        Cursor cursor = db.rawQuery(query, null);


        return roles;
    }
}
