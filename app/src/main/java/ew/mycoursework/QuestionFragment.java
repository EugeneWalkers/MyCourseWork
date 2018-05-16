package ew.mycoursework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {

    String[] answers;
    String name;
    int correct;
    ArrayList<RadioButton> buttons = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            answers = bundle.getStringArray(QuestionFragmentAdapter.QUESTIONS);
            name = bundle.getString(QuestionFragmentAdapter.NAME);
            correct = bundle.getInt(QuestionFragmentAdapter.RIGHT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        TextView question = v.findViewById(R.id.question);
        RadioButton a0 = v.findViewById(R.id.answer0);
        RadioButton a1 = v.findViewById(R.id.answer1);
        RadioButton a2 = v.findViewById(R.id.answer2);
        RadioButton a3 = v.findViewById(R.id.answer3);
        question.setText(name);
        a0.setText(answers[0]);
        a1.setText(answers[1]);
        a2.setText(answers[2]);
        a3.setText(answers[3]);
        buttons.add(a0);
        buttons.add(a1);
        buttons.add(a2);
        buttons.add(a3);
//        Button finish = v.findViewById(R.id.finisher);
//        finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (buttons.get(correct - 1).isChecked()){
//                    Toast.makeText(getActivity(), "Correct", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), "Uncorrect", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return v;
    }
}
