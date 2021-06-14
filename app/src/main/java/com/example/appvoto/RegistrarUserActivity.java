package com.example.appvoto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUserActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnRegistrar;

    private String Email = "", Password = "";

    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        getSupportActionBar().hide();

        //Instancia de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        //Volver a login
        Button btn = (Button) findViewById(R.id.btnRegresar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Declaracion de variable
        email = (EditText) findViewById(R.id.txtUsuario);
        password = (EditText) findViewById(R.id.txtPassword2);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar2);

        //Validacion de datos y registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString();
                Password = password.getText().toString();

                if(!Email.isEmpty() && !Password.isEmpty()){
                    if (Password.length() >= 6) {
                        registrarUser();
                    }else {
                        Toast.makeText(RegistrarUserActivity.this,"Debes elegir una contraseña de al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegistrarUserActivity.this,"Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void  registrarUser(){
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> datos = new HashMap<>();
                    datos.put("Email", Email);
                    datos.put("Password", Password);

                    String id = firebaseAuth.getCurrentUser().getUid();

                    Toast.makeText(RegistrarUserActivity.this,"Te has registrado  exitosamente", Toast.LENGTH_SHORT).show();

                    database.child("Users").child(id).push().setValue(datos).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                Toast.makeText(RegistrarUserActivity.this,"Tus datos se guardaron exitosamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrarUserActivity.this, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(RegistrarUserActivity.this,"Tus datos no se enviaron correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrarUserActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });

                }else {
                    Toast.makeText(RegistrarUserActivity.this,"Tu correo ya esta registrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}