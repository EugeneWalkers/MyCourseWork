package ew.mycoursework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class QuestionsToChangeAdapter extends RecyclerView.Adapter<QuestionsToChangeAdapter.ViewHolder> {
    private String[] dataSource;
    private String testName;

    public String[] getDataSource() {
        return dataSource;
    }

    public void setDataSource(String[] dataSource) {
        this.dataSource = dataSource;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        private String questionName;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("tests");
        DocumentReference ref;

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        String testName;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(40);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionName = ((TextView) view).getText().toString();
                    ref = reference.document(testName);
                    final Context context = view.getContext();
                    final Intent intent = new Intent().setClass(context, ChangeQuestionActivity.class);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Map<String, Object> notParsedTest = document.getData();
                                    if (notParsedTest.containsKey("questions")){
                                        String notParcedQuestions = notParsedTest.get("questions").toString();
                                        String[] questions = notParcedQuestions.substring(1, notParcedQuestions.length() - 1).split(", ");
                                        intent.putExtra(MainActivity.QUESTIONS, questions);
                                        for (int i=0; i<questions.length; i++){
                                            if (questions[i].split(":")[0].equals(questionName)){
                                                intent.putExtra(MainActivity.QUESTION, questions[i]);
                                                break;
                                            }
                                        }
                                    }
                                    intent.putExtra(MainActivity.TEST_NAME, testName);
                                    start(context, intent);
                                }
                            }
                        }
                    });

                }
            });

        }

        void start(Context context, Intent intent) {
            ((Activity)context).startActivity(intent);
            ((Activity)context).finish();
        }
    }

    QuestionsToChangeAdapter(String[] dataArgs, String testName) {
        dataSource = dataArgs;
        this.testName = testName;
    }

    @NonNull
    @Override
    public QuestionsToChangeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setTestName(testName);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }


}