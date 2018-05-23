package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeQuestion extends AppCompatActivity {
    String question = null;
    String[] allQuestions;
    String testname;
    int right = 0;
    ArrayList<String> questionsToSend;
    String[] rights = {"1", "2", "3", "4"};
    Spinner spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_question);
        Button write = findViewById(R.id.writeQuestion);
        spinner = findViewById(R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rights);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.QUESTION)) {
            question = intent.getStringExtra(MainActivity.QUESTION);
        }
        if (intent.hasExtra(MainActivity.QUESTIONS)){
            allQuestions = intent.getStringArrayExtra(MainActivity.QUESTIONS);
            questionsToSend = new ArrayList<>(Arrays.asList(allQuestions));
        }
        else{
            questionsToSend = new ArrayList<>();
        }
        testname = intent.getStringExtra(MainActivity.TEST_NAME);
        if (question != null) {
            String[] myQuestion = question.split(":");
            ((EditText) findViewById(R.id.questionText)).setText(myQuestion[0]);
            ((EditText) findViewById(R.id.answer0Text)).setText(myQuestion[1]);
            ((EditText) findViewById(R.id.answer1Text)).setText(myQuestion[2]);
            ((EditText) findViewById(R.id.answer2Text)).setText(myQuestion[3]);
            ((EditText) findViewById(R.id.answer3Text)).setText(myQuestion[4]);
            spinner.setSelection(Integer.parseInt(myQuestion[5]));
        }
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultQuestion = ((EditText) findViewById(R.id.questionText)).getText().toString() + ":" +
                        ((EditText) findViewById(R.id.answer0Text)).getText().toString() + ":" +
                        ((EditText) findViewById(R.id.answer1Text)).getText().toString() + ":" +
                        ((EditText) findViewById(R.id.answer2Text)).getText().toString() + ":" +
                        ((EditText) findViewById(R.id.answer3Text)).getText().toString() + ":" +
                        spinner.getSelectedItemPosition();
                if (question != null){
                    for (int i=0; i<questionsToSend.size(); i++){
                        if (questionsToSend.get(i).equals(question)){
                            questionsToSend.set(i, resultQuestion);
                            break;
                        }
                    }
                }
                else{
                    questionsToSend.add(resultQuestion);
                }
                FirebaseFirestore.getInstance().collection("tests").document(testname).update("questions", questionsToSend);
                String[] result = new String[questionsToSend.size()];
                for (int i=0; i<result.length; i++){
                    result[i] = questionsToSend.get(i);
                }
                Intent intent1 = new Intent();
                intent1.putExtra(MainActivity.QUESTIONS, result);
                setResult(MainActivity.REQUEST_OK, intent1);
                finish();
            }
        });

    }
}
