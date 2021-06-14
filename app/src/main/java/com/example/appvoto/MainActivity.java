package com.example.appvoto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView user, pass,name;
    private Button btnIniciarSesion, btnRegis;
    private String User, Pass, Name;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        name = (TextView) findViewById(R.id.name);
        user = (TextView) findViewById(R.id.txtUser);
        pass = (TextView) findViewById(R.id.txtPassword);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciar);
        btnRegis = (Button) findViewById(R.id.btnRegistrar);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MainActivity.this,RegistrarUserActivity.class);
                startActivity(next);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                User = user.getText().toString();
                Pass = pass.getText().toString();

                if (User.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Escribe un correo", Toast.LENGTH_SHORT).show();
                }else if(Pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Escribe tu contraseña", Toast.LENGTH_SHORT).show();
                }else if(name.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Escribe tu nombre", Toast.LENGTH_SHORT).show();
                } else {
                    loginInic();
                }
            }
        });
    }

    //Validacion de Login
    private void loginInic(){
        firebaseAuth.signInWithEmailAndPassword(User, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Name = name.getText().toString();
                if (task.isSuccessful()){
                    Bundle datos = new Bundle();
                    datos.putString("nombre", Name);
                    Intent next = new Intent( MainActivity.this, PrincipalActivity.class);
                    next.putExtras(datos);
                    startActivity(next);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Datos incorrectos o  no existentes", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, PrincipalActivity.class));
            finish();
        }
    }
}