package com.example.ngavi.geoquiz;

public class TrueFalse extends Object {
    private int mQuestion;
    private boolean mTrueQuestion;
    private boolean mcheated;
    private int mnumcheats = 3;
    public TrueFalse(int question, boolean trueQuestion, boolean cheated){
        mQuestion = question; //holds resource ID (always an int)
        mTrueQuestion  = trueQuestion; //indicates whether question is true or false
        mcheated = cheated;



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

    public boolean getCheated(){
        return mcheated;
    }

    public void setCheated(boolean cheated){
        mcheated = cheated;

    }
    public int getnumcheats(){
        return mnumcheats;
    }

    public void setnumcheats(int numcheats){
        mnumcheats = numcheats;

    }




}
