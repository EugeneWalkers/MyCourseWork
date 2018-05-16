package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    QuestionFragment questionFragment;
    QuestionFragmentAdapter questionFragmentAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.TEST);
        Test test = getTest(name);
        Toolbar toolbar = findViewById(R.id.toolbar_test);
        TextView nameView = toolbar.findViewById(R.id.name_test);
        nameView.setText(name);
        pager = findViewById(R.id.pager);
        questionFragmentAdapter = new QuestionFragmentAdapter(getSupportFragmentManager());
        questionFragmentAdapter.setTest(test);
        pager.setAdapter(questionFragmentAdapter);
//        Button finisher = pager.findViewById(R.id.finisher);
//        finisher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }

    private Test getTest(String name) {
        ArrayList<Question> questions = new ArrayList<>();
        String[] answers0 = {"wq1", "rq", "wq2", "wq3"};
        questions.add(new Question("Q0", answers0, 2));
        String[] answers1 = {"rq", "wq1", "wq2", "wq3"};
        questions.add(new Question("Q1", answers1, 1));
        Test test = new Test(name, questions);
        return test;
    }
}
