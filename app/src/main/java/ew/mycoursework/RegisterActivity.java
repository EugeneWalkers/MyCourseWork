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

public class RegisterActivity extends AppCompatActivity {

    private final String LOGIN_TAG = "login";
    private final String PASSWORD_TAG = "password";
    private EditText name, login, password;

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
                if (isLoginValid(sLogin) && !isUserExists(sLogin)){
                    if (login.getText().toString().equals("")
                            || password.getText().toString().equals("")
                            || name.getText().toString().equals("")){
                        Toast.makeText(
                                RegisterActivity.this,
                                "Some field is empty!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    else{
                        addUser(
                                new User(name.getText().toString(),
                                        sLogin, password.getText().toString(),
                                        "testeduser")
                        );
                        Toast.makeText(
                                RegisterActivity.this,
                                "Succesfully registered!",
                                Toast.LENGTH_SHORT
                        ).show();
                        finish();
                    }
                }
                else if (!isLoginValid(sLogin)){
                    Toast.makeText(
                            RegisterActivity.this,
                            "Error! Incorrect email!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else{
                    Toast.makeText(
                            RegisterActivity.this,
                            "Error! The user is already exists.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void addUser(User newUser){

    }

    private boolean isLoginValid(@NonNull String login){
        return login.contains("@");
    }

    private boolean isUserExists(@NonNull String login){
        String[] logins = getLoginsDatabase();
        for (String login1 : logins) {
            if (login1.equals(login)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    private String[] getLoginsDatabase(){

        return null;
    }
}
