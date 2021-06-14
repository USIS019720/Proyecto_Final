package com.example.appvoto;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PrincipalActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    BarChart grafica;

    List<Elecciones> listaVentas = new ArrayList<>();
    List<BarEntry> entradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Grafica
        grafica = findViewById(R.id.graficaBarras);

        obtenerVentas();

        for(int i = 0 ; i < listaVentas.size() ; i++) {
            entradas.add(new BarEntry(i, Float.parseFloat(listaVentas.get(i).getVoto())));
        }

        BarDataSet datos = new BarDataSet(entradas, "GRAFICA DE VENTAS");
        BarData data = new BarData(datos);

        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        data.setBarWidth(0.9f);

        grafica.setData(data);
        grafica.setFitBars(true);
        grafica.invalidate();


        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton btnCerrar = findViewById(R.id.btnCerrarSesion);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(PrincipalActivity.this, MainActivity.class));
                finish();
            }
        });

        FloatingActionButton btnAgregar = findViewById(R.id.btnAgregarVoto);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(PrincipalActivity.this, VotoActivity.class);
                startActivity(next);
            }
        });

        FloatingActionButton btnMensajeria = findViewById(R.id.btnMensajeria);
        btnMensajeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name;
                name = getIntent().getStringExtra("nombre");

                Bundle datos = new Bundle();
                datos.putString("nombre2", name);
                Intent next = new Intent( PrincipalActivity.this, ChatActivity.class);
                next.putExtras(datos);
                startActivity(next);
            }
        });
    }

    public void obtenerVentas() {
        listaVentas.clear();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "dbSistema", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery("select idVoto, voto, COUNT(candidato) from elecciones GROUP BY candidato", null);
        if(fila != null && fila.getCount() != 0) {
            fila.moveToFirst();
            do {
                listaVentas.add(
                        new Elecciones(
                                fila.getString(0),
                                fila.getString(1),
                                fila.getString(2)
                        )
                );
            } while(fila.moveToNext());
        } else {
            Toast.makeText(this, "NO HAY REGISTROS", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}