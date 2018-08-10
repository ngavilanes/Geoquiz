package com.example.ngavi.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_ANSWER_IS_TRUE= "com.example.ngavi.geoquiz.answer_is_true";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    public static final String EXTRA_ANSWER_SHOWN = "com.example.ngavi.geoquiz.answer_shown";
    private static final String KEY_INDEX ="index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mShowAnswerButton = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);

                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }

                SetAnswerShownResult(true); //determines if a user has cheated to send back to QuizActivity
                //code for a later API version
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {


                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }
                else{
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    private void SetAnswerShownResult(boolean Answer_Shown_Param){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, Answer_Shown_Param);
        setResult(RESULT_OK, intent); //not required if you do not need to distinguish between results but required if StartActivityForResult() is called from parent

    }

    public static boolean WasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }


}
