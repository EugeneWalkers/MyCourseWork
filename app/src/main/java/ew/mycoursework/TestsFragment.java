package ew.mycoursework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TestsFragment extends Fragment {

    String[] myDataset = {""};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("tests");
    DocumentReference ref;
    RecyclerView.Adapter mAdapter;
    String TAG = "TestAccessor";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tests, container, false);
        final RecyclerView mRecyclerView = v.findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ref = reference.document("metadata");
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
                        String arrayOfTests = notParsedTest.get("tests-array").toString();
                        myDataset  = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
                        mAdapter = new TestsAdapter(myDataset);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.i(TAG, "No such document");
                    }
                } else {
                    Log.i(TAG, "get failed with ", task.getException());
                }
            }
        });
        return v;
    }
}
