package ew.mycoursework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    String[] myDataset;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("tests");
    DocumentReference ref;
    RecyclerView.Adapter mAdapter;
    Bundle userdata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userdata = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tests, container, false);
        final RecyclerView mRecyclerView = v.findViewById(R.id.recycler);
//        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ref = reference.document("metadata");
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> notParsedTest = document.getData();
                        String arrayOfTests = notParsedTest.get("tests-array").toString();
                        myDataset  = arrayOfTests.substring(1, arrayOfTests.length() - 1).split(", ");
                        mAdapter = new TestsAdapter(myDataset, userdata);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
        });
        return v;
    }
}
