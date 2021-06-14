package com.example.appvoto;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VotoActivity extends AppCompatActivity {

    String txtCandidato;
    TextView txtDui;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voto);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView cboCantidatos = (AutoCompleteTextView)
                findViewById(R.id.cboCantidatos);
        cboCantidatos.setAdapter(adapter);

        txtDui = findViewById(R.id.txtDUI);

        FirebaseMessaging.getInstance().subscribeToTopic("enviaratodoos").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });

        btn = findViewById(R.id.btnVotar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cboCantidatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        txtCandidato = cboCantidatos.getText().toString();
                    }
                });

                if (cboCantidatos.getText().toString().isEmpty()){
                    try {
                        Toast.makeText(VotoActivity.this, "Seleccione un candidato", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(VotoActivity.this, "Seleccione un candidato", Toast.LENGTH_SHORT).show();
                    }
                }else if(txtDui.getText().toString().isEmpty()) {
                    try {
                        Toast.makeText(VotoActivity.this, "Escribe tu numero de DUI", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(VotoActivity.this, "Escribe tu numero de DUI", Toast.LENGTH_SHORT).show();
                    }
                }else if (txtDui.getText().toString().length() == 9){
                    insertarVentas(Integer.valueOf(txtDui.getText().toString()), cboCantidatos.getText().toString(),"5");
                    mostrarVistaPrincipal();
                    notificar(cboCantidatos.getText().toString());
                }else{
                    mostrarMsgToast("El dui no es valido.");
                }
            }
        });

        FloatingActionButton btnRegresar = findViewById(R.id.btnRegresarPrinc);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(VotoActivity.this, PrincipalActivity.class);
                startActivity(next);
                finish();
            }
        });
    }

    private void notificar(String cand) {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        String noti = "A favor del candidato " + cand;

        try {
            json.put("to", "/topics/"+"enviaratodoos");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", "Nuevo voto");
            notificacion.put("detalle", noti);

            json.put("data", notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String ,String > header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAcR4U3v8:APA91bGYt-oGrwEkrKo95Gia3ZEUxI9zP4LUhMf6Bdc98nVZvUirmmZl0r7TvAC81xC-cnD4GJ-Q7-NCxsrAcqVSAHB_cU12MYVViZfgGSupAfkTPu1x20r6MZNTNwaIhd1HnuN_zEou");
                    return header;
                }
            };

            myrequest.add(request);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void insertarVentas(Integer dui, String candidato, String voto) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        registro.put("idVoto", dui);
        registro.put("candidato", candidato);
        registro.put("voto", voto);

        db.insert("elecciones", null, registro);
        db.close();
    }

    private static final String[] COUNTRIES = new String[] {
            "Biden", "Ceren", "Maximiliano"
    };

    private void mostrarVistaPrincipal(){
        Intent iprincipal = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(iprincipal);
    }

    private void mostrarMsgToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

}