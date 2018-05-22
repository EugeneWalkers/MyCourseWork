package ew.mycoursework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder> {
    private String[] dataSource;

    Bundle userData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        private String testName;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("tests");
        DocumentReference ref;
        String TAG = "TestAccessor";

        public void setUserData(Bundle userData) {
            this.userData = userData;
        }

        Bundle userData;


        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(40);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testName = ((TextView) view).getText().toString();
                    ref = reference.document(testName);
                    final Context context = view.getContext();
                    final Intent intent = new Intent().setClass(context, TestActivity.class);
                    ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Log.i(TAG, "Hello from onComplete!");
                            if (task.isSuccessful()) {
                                Log.i(TAG, "Task is successful!");
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.i(TAG, "Document exists!");
                                    Map<String, Object> notParsedTest = document.getData();
                                    String notParcedQuestions = notParsedTest.get("questions").toString();
                                    String[] questions = notParcedQuestions.substring(1, notParcedQuestions.length() - 1).split(", ");
                                    //String name = notParsedTest.get("name").toString();
                                    intent.putExtra(MainActivity.TEST_NAME, testName);
                                    intent.putExtra(MainActivity.QUESTIONS, questions);
                                    start(context, intent);
                                } else {
                                    Log.i(TAG, "No such document");
                                }
                            } else {
                                Log.i(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
            });

        }

        void start(Context context, Intent intent) {
            intent.putExtra(MainActivity.USER_BUNDLE, userData);
            ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_RESULT);
        }
    }

    TestsAdapter(String[] dataArgs, Bundle userdata) {
        dataSource = dataArgs;
        this.userData = userdata;
    }

    @NonNull
    @Override
    public TestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setUserData(userData);
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