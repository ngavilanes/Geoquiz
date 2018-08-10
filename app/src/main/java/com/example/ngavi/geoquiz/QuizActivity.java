package com.example.ngavi.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private boolean mIsCheater;
    private TextView mQuestionTextView;
    private TextView mNumberOfCheatsTextView;
    private TextView mAPITextView;
    private int mCurrentIndex = 0;
    public static final int REQUEST_CODE_CHEAT = 0;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private int mnumcheats = 3;

    //array of questions :
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_africa, false,false),
            new TrueFalse(R.string.question_americas, true,false),
            new TrueFalse(R.string.question_asia, true,false),
            new TrueFalse(R.string.question_mideast, false,false),
            new TrueFalse(R.string.question_oceans, true,false),

    };


    @Override
    protected void onCreate( Bundle savedInstanceState) { //called when activity subclass is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.d(TAG, "OnCreate(Bundle) called");

        if(savedInstanceState!=null){ //saving information every time you change orientation
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean("index2", false);
            mnumcheats = savedInstanceState.getInt("index3", 3);
        }
        //getting the activity its UI BY passing in layout resource ID
        //resources: things that are not code : image files, audio files, XML files
        //setContentView method inflates a layout and puts it on screen
        //R.layout.activity quiz comes from layout inner class of R.java
            //not every widget in the layout has an ID- it must be generated in XML file
            //android:id="blah"
        //strings would be referred to as setTitle(R.string.(name))

        //now to references the buttons and set them to the private variables above
            //findViewById is a method that retrieves a view ID from R.java


        mAPITextView= findViewById(R.id.VersionTextView);
        mAPITextView.setText("API Version: " + Build.VERSION.SDK_INT);


        mNumberOfCheatsTextView = findViewById(R.id.NumCheatsTextView);
        mNumberOfCheatsTextView.setText("Number of cheats left: " + mnumcheats);



      //  mTrueButton =  findViewById(R.id.true_button);
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


                UpdateQuestion();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater= false;
                mCurrentIndex =(mCurrentIndex+1) % mQuestionBank.length;
                UpdateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               //setting up new activity - requires an intent which communicates with the OS to start the cheat activity
               //set up intent using constructor: Intent(context,class);

               Intent intent = new Intent(QuizActivity.this, CheatActivity.class); //intent class parameters : 1->where you're starting from  2-> where you're going
               boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
               intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,AnswerIsTrue);
               startActivityForResult(intent,REQUEST_CODE_CHEAT);

            }


                                        });





        UpdateQuestion();



    }

    private void UpdateQuestion(){ //method to update questions
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
        mNumberOfCheatsTextView.setText("Number of cheats left: " + mnumcheats);
        //Log.d(TAG,"Updating question text for question #: " + mCurrentIndex, new Exception());

    }

    private void CheckAnswer(boolean UserPressedTrue){ //private method to check the truth value of each question
        boolean AnswerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId;
        if(mIsCheater==true){
            if(mnumcheats>0) {
                mnumcheats--;
                mNumberOfCheatsTextView.setText("Number of cheats left: " + mnumcheats);
                mIsCheater=false;
            }

        }

        if(mnumcheats>0 || !mIsCheater) {
            if (UserPressedTrue == AnswerIsTrue) { //question is true and user pressed it correctly
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        else{
            messageResId = R.string.judegement_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();



        //on click a message should appear --> a toast
        //use toast class method to create toast- makeText




    }

    @Override
    protected void onActivityResult(int RequestCode, int ResultCode, Intent Data){
        if(ResultCode != Activity.RESULT_OK){
            return;
        }
        if(RequestCode==REQUEST_CODE_CHEAT){
            if(Data ==null){
                return;
            }
            mIsCheater= CheatActivity.WasAnswerShown(Data);
        }
    }


    //in order to wire up button widgets - get references to inflated view objects
    //set listeners on those objects to respond to user actions

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean("index2", mIsCheater);
        savedInstanceState.putInt("index3",mnumcheats);
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
