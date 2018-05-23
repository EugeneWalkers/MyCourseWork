package ew.mycoursework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    String[] myDataset;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("tests");
    DocumentReference ref;
    RecyclerView.Adapter mAdapter;
    String testName;
    Button addQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        final RecyclerView mRecyclerView = findViewById(R.id.recyclerQuestions);
        Intent intent = getIntent();
        testName = intent.getStringExtra(MainActivity.TEST_NAME);
        addQuestion = findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = reference.document(testName);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> notParsedTest = document.getData();
                                Intent goToChangeQuestionActivity = new Intent(AdminActivity.this, ChangeQuestion.class);
                                if (notParsedTest.containsKey("questions")){
                                    String arrayOfTests = notParsedTest.get("questions").toString();
                                    myDataset = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
                                    goToChangeQuestionActivity.putExtra(MainActivity.QUESTIONS, myDataset);
                                }
                                goToChangeQuestionActivity.putExtra(MainActivity.TEST_NAME, testName);
                                startActivity(goToChangeQuestionActivity);
                                finish();
                            }
                        }
                    }
                });


            }
        });
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ref = reference.document(testName);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> notParsedTest = document.getData();
                        if (document.contains("questions")){
                            String arrayOfTests = notParsedTest.get("questions").toString();
                            String[] notParcedQuestions = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
                            myDataset = new String[notParcedQuestions.length];
                            for (int i = 0; i < myDataset.length; i++) {
                                String[] someQ = notParcedQuestions[i].split(":");
                                myDataset[i] = someQ[0];
                            }
                        }
                        else{
                            myDataset = new String[0];
                        }
                        mAdapter = new QuestionsToChangeAdapter(myDataset, testName);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }
            }
        });
    }
}
