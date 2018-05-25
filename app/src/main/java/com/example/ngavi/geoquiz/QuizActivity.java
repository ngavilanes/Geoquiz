package com.example.ngavi.geoquiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
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






    //in order to wire up button widgets - get references to inflated view objects
        //set listeners on those objects to respond to user actions

    @Override
    protected void onCreate(Bundle savedInstanceState) { //called when activity subclass is created
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //getting the activity its UI BY passing in layout resource ID
        //resources: things that are not code : image files, audio files, XML files
        //setContentView method inflates a layout and puts it on screen
        //R.layout.activity quiz comes from layout inner class of R.java
            //not every widget in the layout has an ID- must be generated in XML file
            //android:id="blah"
        //strings would be referred to as setTitle(R.string.(name))

        //now to references the buttons and set them to the private variables above
            //findViewById is a method that retrieves a view ID from R.java

        //getting the TextView of the current question
        mQuestionTextView = findViewById(R.id.question_text_view);
         int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);

        mTrueButton =  findViewById(R.id.true_button); //must cast to Button object
        //these buttons are waiting for an event (user pressing a button)
        //we need listeners to obtain that information
        mTrueButton.setOnClickListener(new View.OnClickListener(){ //takes listener as argument
            @Override
            public void onClick(View v){
                //anonymous inner class- puts entire implementation of listener methods in one place
                //must implement sole method OnClick(View v)
                //on click a message should appear --> a toast
                //use toast class method to create toast- makeText

                Toast.makeText(QuizActivity.this, R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                //ctrl +shift +space to finish autocomplete
            }
                                       });

        mFalseButton =  findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex =(mCurrentIndex+1) % mQuestionBank.length;
               int question = mQuestionBank[mCurrentIndex].getQuestion();
                mQuestionTextView.setText(question);
            }
        });


    }
}
