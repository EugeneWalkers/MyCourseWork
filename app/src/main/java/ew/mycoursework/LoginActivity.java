package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_TAG = "login";
    private final String PASSWORD_TAG = "password";

    String login;
    String password;
    private FirebaseFirestore db;
    private int number;
    StringBuilder loginB = new StringBuilder("");
    StringBuilder passwordB = new StringBuilder("");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button loginButton = findViewById(R.id.login_button);
        TextView registerButton = findViewById(R.id.register_button_login);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                login = ((EditText) findViewById(R.id.login_input)).getText().toString();
                password = ((EditText) findViewById(R.id.password_input)).getText().toString();
                goToRegister.putExtra(LOGIN_TAG, login);
                goToRegister.putExtra(PASSWORD_TAG, password);
                startActivity(goToRegister);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login = ((EditText) findViewById(R.id.login_input)).getText().toString();
                password = ((EditText) findViewById(R.id.password_input)).getText().toString();
                if (isLoginValid(login)) {
                    db = FirebaseFirestore.getInstance();
                    db.collection("users").document("metadata").get();
                    DocumentReference ref1 = db.collection("users").document("metadata");
                    ref1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    number = Integer.parseInt(document.get("number").toString());
                                    DocumentReference ref2;

                                    for (int i = 0; i < number; i++) {
                                        ref2 = db.collection("users").document("user" + i);
                                        final int finalI = i;
                                        ref2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                boolean isExist = false;
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        loginB.append(document.get("login").toString());
                                                        passwordB.append(document.get("password").toString());

                                                        if (login.equals(loginB.toString()) && password.equals(passwordB.toString())) {
                                                            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                                                            goToMain.putExtra(MainActivity.LOGIN, login);
                                                            goToMain.putExtra(MainActivity.PASSWORD, password);
                                                            goToMain.putExtra(MainActivity.NAME, document.get("name").toString());
                                                            goToMain.putExtra(MainActivity.TYPE, document.get("type").toString());
                                                            goToMain.putExtra(MainActivity.ID, "user" + finalI);
                                                            if (document.contains("results")){

                                                                ArrayList<String> results = (ArrayList<String>)document.get("results");
                                                                goToMain.putExtra(MainActivity.RESULTS, results);
                                                            }
                                                            startActivity(goToMain);
                                                            finish();
                                                        }
                                                        loginB.delete(0, loginB.length());
                                                        passwordB.delete(0, passwordB.length());
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }

                        }
                    });


                } else {
                    Toast.makeText(LoginActivity.this, "Not valid login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isLoginValid(@NonNull String login) {
        return login.contains("@");
    }
}