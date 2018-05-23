package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private final String LOGIN_TAG = "login";
    private final String PASSWORD_TAG = "password";
    private EditText name, login, password;
    private FirebaseFirestore db;
    private DocumentReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name_input_register);
        login = findViewById(R.id.login_input_register);
        password = findViewById(R.id.password_input_register);
        Intent ourIntent = getIntent();
        login.setText(ourIntent.getStringExtra(LOGIN_TAG));
        password.setText(ourIntent.getStringExtra(PASSWORD_TAG));
        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sLogin = login.getText().toString();
                if (isLoginValid(sLogin)){
                    if (
                            login.getText().toString().equals("")
                            || password.getText().toString().equals("")
                            || name.getText().toString().equals("")){
                        Toast.makeText(
                                RegisterActivity.this,
                                "Some field is empty!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    else{

                        db = FirebaseFirestore.getInstance();
                        ref = db.collection("users").document("metadata");
                        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()){
                                        ArrayList<String> users = (ArrayList<String>)document.get("users");
                                        boolean isExist = false;
                                        for (int i=0; i<users.size(); i++){
                                            if (users.get(i).equals(login)){
                                                isExist = true;
                                                break;
                                            }
                                        }
                                        if (!isExist){
                                            String id = "user" + users.size();
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("login", login.getText().toString());
                                            map.put("password", password.getText().toString());
                                            map.put("name", name.getText().toString());
                                            map.put("type", "testeduser");
                                            db.collection("users").document(id).set(map);
                                            users.add(login.getText().toString());
                                            db.collection("users").document("metadata").update("users", users);
                                            db.collection("users").document("metadata").update("number", users.size());
                                            finish();
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
                else if (!isLoginValid(sLogin)){
                    Toast.makeText(
                            RegisterActivity.this,
                            "Error! Incorrect email!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private boolean isLoginValid(@NonNull String login){
        return login.contains("@");
    }
}
