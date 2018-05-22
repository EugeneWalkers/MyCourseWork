package ew.mycoursework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class QuestionFragmentAdapter extends FragmentPagerAdapter {

    public static final String NAME = "questionName";
    public static final String QUESTIONS = "questions";
    public static final String RIGHT = "right";
    public static final String NUMBER = "number";
    public static final String COUNT = "count";

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    String[] questions;

    public void setCount(int count) {
        this.count = count;
    }

    private int count = 0;

    QuestionFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment qf = new QuestionFragment();
        Bundle bundle = new Bundle();
        String notParcedQuestion = questions[position];
        String[] myQuestion = notParcedQuestion.split(":");
        bundle.putStringArray(QUESTIONS, myQuestion);
        bundle.putInt(NUMBER, position);
        qf.setArguments(bundle);
        return qf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Question " + position;
    }

    @Override
    public int getCount() {
        return count;
    }
}

