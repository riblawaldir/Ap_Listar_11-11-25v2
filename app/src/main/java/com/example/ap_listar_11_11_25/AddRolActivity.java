package com.example.ap_listar_11_11_25;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class AddRolActivity extends AppCompatActivity {

    EditText edtNombre, edtDescripcion;
    Button btnGuardar;
    ListView lvRoles;

    DBHelper db;
    ArrayList<String> listaRoles;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rol);

        edtNombre = findViewById(R.id.edtNombreRol);
        edtDescripcion = findViewById(R.id.edtDescripcionRol);
        btnGuardar = findViewById(R.id.btnGuardarRol);
        lvRoles = findViewById(R.id.lvRoles);

        db = new DBHelper(this);

        cargarRoles();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = edtNombre.getText().toString();
                String descripcion = edtDescripcion.getText().toString();

                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    Toast.makeText(AddRolActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean insertado = db.insertarRol(nombre, descripcion);

                if (insertado) {
                    Toast.makeText(AddRolActivity.this, "Rol guardado", Toast.LENGTH_SHORT).show();
                    edtNombre.setText("");
                    edtDescripcion.setText("");
                    cargarRoles();
                } else {
                    Toast.makeText(AddRolActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarRoles() {
        listaRoles = new ArrayList<>();
        var dbRead = db.getReadableDatabase();
        var cursor = dbRead.rawQuery("SELECT nombre, descripcion FROM Rol", null);

        if (cursor.moveToFirst()) {
            do {
                String rol = cursor.getString(0) + " - " + cursor.getString(1);
                listaRoles.add(rol);
            } while (cursor.moveToNext());
        }

        cursor.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRoles);
        lvRoles.setAdapter(adapter);
    }
}
