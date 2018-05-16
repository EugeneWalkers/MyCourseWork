package ew.mycoursework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class QuestionFragmentAdapter extends FragmentStatePagerAdapter{
    private Test test;

    public static final String NAME = "QuestionName";
    public static final String QUESTIONS = "q";
    public static final String RIGHT = "right";

    QuestionFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTest(Test test){
        this.test = test;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment qf = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NAME, test.getQuestions().get(position).getText());
        bundle.putStringArray(QUESTIONS, test.getQuestions().get(position).getAnswers());
        bundle.putInt(RIGHT, test.getQuestions().get(position).getRightAnswer());
        qf.setArguments(bundle);
        return qf;
    }

    @Override
    public int getCount() {
        return test.getQuestions().size();
    }
}

