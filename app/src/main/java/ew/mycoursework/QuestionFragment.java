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

    int correct;
    int numberOfQuestion;
    ArrayList<RadioButton> buttons = new ArrayList<>();
    TextView question;
    String[] myQuestion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            numberOfQuestion = bundle.getInt(QuestionFragmentAdapter.NUMBER);
            myQuestion = bundle.getStringArray(QuestionFragmentAdapter.QUESTIONS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        question = v.findViewById(R.id.question);
        if (buttons.size() == 0){
            buttons.add((RadioButton) v.findViewById(R.id.answer0));
            buttons.add((RadioButton) v.findViewById(R.id.answer1));
            buttons.add((RadioButton) v.findViewById(R.id.answer2));
            buttons.add((RadioButton) v.findViewById(R.id.answer3));
        }
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(myQuestion[i + 1]);
        }
        question.setText(myQuestion[0]);
        correct = Integer.parseInt(myQuestion[5]);
        return v;
    }
}
