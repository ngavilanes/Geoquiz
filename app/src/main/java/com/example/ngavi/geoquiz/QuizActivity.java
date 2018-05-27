package com.example.ngavi.geoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    //array of questions :
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_oceans, true),

    };
    private int mCurrentIndex = 0;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";


    private void UpdateQuestion(){ //method to update questions
       int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void CheckAnswer(boolean UserPressedTrue){ //private method to check the truth value of each question
        boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId;

        if(UserPressedTrue==AnswerIsTrue){ //question is true and user pressed it correctly
            messageResId = R.string.correct_toast;
        }
        else{
            messageResId = R.string.incorrect_toast;
        }


        //on click a message should appear --> a toast
        //use toast class method to create toast- makeText
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();


    }


    //in order to wire up button widgets - get references to inflated view objects
        //set listeners on those objects to respond to user actions

    @Override
    protected void onCreate(final Bundle savedInstanceState) { //called when activity subclass is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "OnCreate(Bundle) called");
        //getting the activity its UI BY passing in layout resource ID
        //resources: things that are not code : image files, audio files, XML files
        //setContentView method inflates a layout and puts it on screen
        //R.layout.activity quiz comes from layout inner class of R.java
            //not every widget in the layout has an ID- it must be generated in XML file
            //android:id="blah"
        //strings would be referred to as setTitle(R.string.(name))

        //now to references the buttons and set them to the private variables above
            //findViewById is a method that retrieves a view ID from R.java



        mTrueButton =  findViewById(R.id.true_button);
        //these buttons are waiting for an event (user pressing a button)
        //we need listeners to obtain that information
        mTrueButton.setOnClickListener(new View.OnClickListener(){ //takes listener as argument
            @Override
            public void onClick(View v){
                CheckAnswer(true);
                //ctrl +shift +space to finish autocomplete
            }
                                       });

        mFalseButton =  findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CheckAnswer(false);
            }
        });

        //getting the TextView of the current question
        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                UpdateQuestion();
            }


        });
        mPrevButton= findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex -=1;
                if(mCurrentIndex==-1){
                    mCurrentIndex= mQuestionBank.length-1;
                }

                if(savedInstanceState!=null){
                    mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
                }
                UpdateQuestion();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex =(mCurrentIndex+1) % mQuestionBank.length;
                UpdateQuestion();
            }
        });



        UpdateQuestion();



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }




}
