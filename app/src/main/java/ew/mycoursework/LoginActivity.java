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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private final String LOGIN_TAG = "login";
    private final String PASSWORD_TAG = "password";
    String login;
    String password;
    File listOfUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: delete this block on release
        ((EditText) findViewById(R.id.login_input)).setText("lol@");
        ((EditText) findViewById(R.id.password_input)).setText("pass");

        try {
            listOfUsers = File.createTempFile("users", "txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button loginButton = findViewById(R.id.login_button);
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
                    boolean isExist = false;
                    ArrayList<User> users = getListOfUsers();
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getLogin().equals(login)
                                && users.get(i).getPassword().equals(password)) {
                            Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                            goToMain.putExtra(MainActivity.LOGIN, login);
                            goToMain.putExtra(MainActivity.PASSWORD, password);
                            goToMain.putExtra(MainActivity.NAME, users.get(i).getName());
                            goToMain.putExtra(MainActivity.TYPE, users.get(i).getType());
                            isExist = true;
                            startActivity(goToMain);
                            finish();
                            break;
                        }
                    }
                    if (!isExist){
                        Toast.makeText(LoginActivity.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Not valid login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isLoginValid(@NonNull String login) {
        return login.contains("@");
    }

    @NonNull
    private ArrayList<User> getListOfUsers() {
//        final StorageReference mStrorageRef = FirebaseStorage.getInstance().getReference();
//        StorageReference existedUsers = mStrorageRef.child("/users/existedusers");
//        existedUsers.getFile(listOfUsers).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                try {
//                    BufferedReader readerListOfUsers = new BufferedReader(new FileReader(listOfUsers));
//                    StringBuilder readingLine = new StringBuilder("");
//                    while(!readingLine.append(readerListOfUsers.readLine()).toString().equals("null")){
//                        if (readingLine.toString().equals(login)){
//                            readingLine.delete(0, readingLine.length());
//                            StorageReference mapOfUsers = mStrorageRef.child("/users/existedusers/Map/users");
//                            File map = File.createTempFile("map", "txt");
//                            BufferedReader mapReader = new BufferedReader(new FileReader(map));
//                            while(!readingLine.append(mapReader.readLine()).toString().equals("null")){
//
//                            }
//                        }
//                        readingLine.delete(0, readingLine.length());
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoginActivity.this, "Unknown Error", Toast.LENGTH_SHORT).show();
//            }
//        });

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("lol@", "pass", "trololo", "testeduser"));
        return users;
    }
}