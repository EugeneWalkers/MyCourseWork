package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class TestActivity extends AppCompatActivity {

    QuestionFragmentAdapter questionFragmentAdapter;
    ViewPager pager;
    double resultOfTest = 0;
    String[] questions;
    String nameTest;
    User user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar_test);
        setPager();
        setUser();
        toolbar.setTitle(nameTest);
        Button finisher = findViewById(R.id.finisher);
        finisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTest();
            }
        });

    }

    private void setUser() {
        Intent intent = getIntent();
        Bundle userdata = intent.getBundleExtra(MainActivity.USER_BUNDLE);
        String login = userdata.getString(MainActivity.LOGIN);
        String password = userdata.getString(MainActivity.PASSWORD);
        String name = userdata.getString(MainActivity.NAME);
        String type = userdata.getString(MainActivity.TYPE);
        String id = userdata.getString(MainActivity.ID);
        ArrayList<String> results = userdata.getStringArrayList(MainActivity.RESULTS);
        user = new User(login, password, name, type, id, results);
    }

    private void setPager() {
        Intent intent = getIntent();
        questions = intent.getStringArrayExtra(MainActivity.QUESTIONS);
        nameTest = intent.getStringExtra(MainActivity.TEST_NAME);

        pager = findViewById(R.id.pager);
        if (questions.length > 0){
            questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager());
            questionFragmentAdapter.setQuestions(questions);
            questionFragmentAdapter.setCount(questions.length);
            pager.setAdapter(questionFragmentAdapter);
        }
    }

    private void saveResultsOfTheTest() {
        ArrayList<String> results = user.getResults();
        String[] someresult;
        boolean isExist = false;
        for (int i = 0; i < results.size(); i++) {
            someresult = results.get(i).split(":");
            if (someresult[0].equals(nameTest)) {
                results.set(i, someresult[0] + ":" + resultOfTest);
                isExist = true;
                break;
            }
        }
        if(!isExist){
            results.add(nameTest+":"+resultOfTest);
            Collections.sort(results);
        }
        user.setResults(results);
        db.collection("users").document(user.getId()).update("results", results);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishTest();
    }

    private void finishTest() {
        QuestionFragment fragment;
        for (int i = 0; i < questions.length; i++) {
            fragment = (QuestionFragment) questionFragmentAdapter.instantiateItem(pager, i);
            if (fragment.buttons.size() != 0){

                int right = Integer.parseInt(questions[i].split(":")[5]);
                if (fragment.buttons.get(right).isChecked()) {
                    resultOfTest++;
                }
            }

        }

        resultOfTest /= (double) questions.length;
        saveResultsOfTheTest();
        Intent result = new Intent();
        result.putExtra(MainActivity.RESULT, String.valueOf(resultOfTest));
        result.putExtra(MainActivity.TEST_NAME, nameTest);
        result.putExtra(MainActivity.RESULTS, user.getResults());
        setResult(MainActivity.REQUEST_OK, result);
        finish();
    }
}
