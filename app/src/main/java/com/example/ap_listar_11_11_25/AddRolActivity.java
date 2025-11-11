package com.example.ap_listar_11_11_25;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddRolActivity extends AppCompatActivity {
    EditText edtNombreRol, edtDescripcionRol;
    Button btnGuardarRol;

    ListView lvRoles;
    DBHelper db;

    ArrayAdapter<Rol> adapter;
    ArrayList<Rol> listaRoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rol);

        //referencias
        edtNombreRol = findViewById(R.id.edtNombreRol);
        edtDescripcionRol = findViewById(R.id.edtDescripcionRol);
        btnGuardarRol = findViewById(R.id.btnGuardarRol);
        lvRoles = findViewById(R.id.lvRoles);
        db = new DBHelper(this);
        //cargar lista inicial
        cargarRoles();

        btnGuardarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombreRol.getText().toString().trim();
                String descripcion = edtDescripcionRol.getText().toString().trim();
                if (nombre.isEmpty()){
                    Toast.makeText(AddRolActivity.this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                //insertar en BD
                boolean exito = db.insertarRol(nombre, descripcion);
                if (exito){
                    Toast.makeText(AddRolActivity.this, "Rol insertado", Toast.LENGTH_SHORT).show();
                    edtNombreRol.setText("");
                    edtDescripcionRol.setText("");
                    cargarRoles();
                }else {
                    Toast.makeText(AddRolActivity.this, "Error al insertar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarRoles() {
        listaRoles = db.obtenerRoles();
        adapter = new ArrayAdapter<>(this, android.R.layout.
                simple_list_item_1,
                listaRoles);
        lvRoles.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarRoles();
    }
}
