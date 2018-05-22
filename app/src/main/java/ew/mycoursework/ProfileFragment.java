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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ProfileFragment extends Fragment {

    String name, type, id;
    RecyclerView mRecyclerView;
    FirebaseFirestore db;
    DocumentReference ref1;
    RecyclerView.Adapter mAdapter;
    String[] tests, results;
    CollectionReference referenceUsers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            name = b.getString(MainActivity.NAME);
            type = b.getString(MainActivity.TYPE);
            id = b.getString(MainActivity.ID);
        }
        db = FirebaseFirestore.getInstance();
        referenceUsers = db.collection("users");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ((TextView) v.findViewById(R.id.name)).setText(name);
        ((TextView) v.findViewById(R.id.type)).setText(type);
        mRecyclerView = v.findViewById(R.id.userTests);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
         //Получаем количество тестов
        ref1 = referenceUsers.document(id);
        ref1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> notParsedTest = document.getData();
                        if (notParsedTest.containsKey("results")){

                            String arrayOfTestsInUser = notParsedTest.get("results").toString();
                            results = arrayOfTestsInUser.substring(1, arrayOfTestsInUser.length()-1).split(", ");
                            mAdapter = new ResultsAdapter(results);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                }
            }
        });
        return v;
    }

    void setUser(String[] newMass){
        //TODO: write this func
    }


}
// user: test1:0.5, test3:1