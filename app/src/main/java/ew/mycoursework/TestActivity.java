package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    QuestionFragmentAdapter questionFragmentAdapter;
    ViewPager pager;

    private Test test = null;
    double resultOfTest = 0;
    String[] questions;
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar_test);
        TextView nameView = toolbar.findViewById(R.id.name_test);
        setPager();
        nameView.setText(name);
        Button finisher = findViewById(R.id.finisher);
        finisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                QuestionFragment fragment;
                for (int i=0; i<questions.length; i++){
                    fragment = (QuestionFragment) questionFragmentAdapter.instantiateItem(pager, i);
                    int right = Integer.parseInt(questions[i].split(":")[5]);
                    if (fragment.buttons.get(right).isChecked()){
                        resultOfTest++;
                    }
                }

                resultOfTest /= (double)questions.length;
                saveResultsOfTheTest();
                Intent result = new Intent();
                result.putExtra(MainActivity.RESULT, String.valueOf(resultOfTest));
                result.putExtra(MainActivity.TEST_NAME, name);
                setResult(MainActivity.REQUEST_OK, result);
                finish();
            }
        });

    }

    private void setPager(){
        Intent intent = getIntent();
        questions = intent.getStringArrayExtra(MainActivity.QUESTIONS);
        name = intent.getStringExtra(MainActivity.NAME);
        pager = findViewById(R.id.pager);
        questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager());
        questionFragmentAdapter.setQuestions(questions);
        questionFragmentAdapter.setCount(questions.length);
        pager.setAdapter(questionFragmentAdapter);
    }

    private void saveResultsOfTheTest() {

    }
}
