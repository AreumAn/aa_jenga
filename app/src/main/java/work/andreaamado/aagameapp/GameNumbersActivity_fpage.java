package work.andreaamado.aagameapp;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by areum on 2017-11-11.
 */

public class GameNumbersActivity_fpage extends Fragment {

    List<Quiz> questions = new ArrayList<Quiz>();

    TextView lblquestion;           // show quiz
    TextView lblquestionNum;        // enter number
    TextView lblquestionCat;        // category
    TextView lblnumberOfquestion;   // show question number

    int showedQuestion = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_game_numbers_fragment, container, false);

        lblquestion = (TextView) view.findViewById(R.id.question);
        lblquestionNum = (TextView) view.findViewById(R.id.question_number);
        lblquestionCat = (TextView) view.findViewById(R.id.cat_name);
        lblnumberOfquestion = (TextView) view.findViewById(R.id.q_number);
        lblquestionCat.setText(((GameNumbersActivity)getActivity()).cate);


        final Button btn0 = (Button) view.findViewById(R.id.btn_0);
        final Button btn1 = (Button) view.findViewById(R.id.btn_1);
        final Button btn2 = (Button) view.findViewById(R.id.btn_2);
        final Button btn3 = (Button) view.findViewById(R.id.btn_3);
        final Button btn4 = (Button) view.findViewById(R.id.btn_4);
        final Button btn5 = (Button) view.findViewById(R.id.btn_5);
        final Button btn6 = (Button) view.findViewById(R.id.btn_6);
        final Button btn7 = (Button) view.findViewById(R.id.btn_7);
        final Button btn8 = (Button) view.findViewById(R.id.btn_8);
        final Button btn9 = (Button) view.findViewById(R.id.btn_9);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),((Button) v).getText(), Toast.LENGTH_LONG).show();

                String text = (String) lblquestionNum.getText();
                text += ((Button) v).getText();

                if (Integer.parseInt(text) > 0 && Integer.parseInt(text) < 101) {
                    lblquestionNum.setText(Integer.parseInt(text) + "");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //builder.setTitle("Warring");
                    builder.setMessage("Please, put number between 1 and 100.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            lblquestionNum.setText("");
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }

        };
        btn0.setOnClickListener(clickListener);
        btn1.setOnClickListener(clickListener);
        btn2.setOnClickListener(clickListener);
        btn3.setOnClickListener(clickListener);
        btn4.setOnClickListener(clickListener);
        btn5.setOnClickListener(clickListener);
        btn6.setOnClickListener(clickListener);
        btn7.setOnClickListener(clickListener);
        btn8.setOnClickListener(clickListener);
        btn9.setOnClickListener(clickListener);

        // Make Quiz 1
        //makeFirstQuiz();
        lblquestion.setText("Please, Enter number of Jenga block.");

        // Click Delete button
        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblquestionNum.setText("");
            }
        });

        // Click the ENTER button
        view.findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionNumtxt = (String) lblquestionNum.getText();

                if(!lblquestionNum.getText().equals("")){
                    // Check out of questions
                    if(showedQuestion == 0) {makeFirstQuiz(questionNumtxt);}
                    else if(showedQuestion > 0 && showedQuestion < ((GameNumbersActivity)getActivity()).question.length) {
                        makeQuiz(questionNumtxt);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Out of Questions");
                        builder.setMessage("It's going to Category page ");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                goMain();
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Out of Questions");
                    builder.setMessage("Please, Enter the number first.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }


                }
        });

        // Click the END button
        view.findViewById(R.id.btn_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to finish this game?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        goMain();
                        dialog.dismiss();
                    }
                });



                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return view;
    }

    // make quiz1 (with make questions array)
    public void makeFirstQuiz(String questionNumtxt) {
        // make questions array and shuffle
        for (int i = 0; i < ((GameNumbersActivity)getActivity()).question.length; i++) {
            questions.add(new Quiz(((GameNumbersActivity)getActivity()).question[i]));
        }
        Collections.shuffle(questions);

        // show
        makeQuiz(questionNumtxt);

    }

    // make quiz and answers statements
    public void makeQuiz(String questionNumtxt) {

        Quiz quiz = questions.get(0);
        // remove used questions element that was used for preventing duplication
        questions.remove(0);

        // set number of quiz and question.
        lblnumberOfquestion.setText("Q." + questionNumtxt);
        lblquestion.setText(quiz.question);
        //lblquestion.setText("Q." + lblquestionNum + ": " + quiz.question);
        lblquestionNum.setText("");

        // How many question showed
        showedQuestion++;

    }

    public void goMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
