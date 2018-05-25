package com.example.ngavi.geoquiz;

public class TrueFalse extends Object {
    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse(int question, boolean trueQuestion){
        mQuestion = question; //holds resource ID (always an int)
        mTrueQuestion  = trueQuestion; //indicates whether question is true or false

    }
    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }


}
