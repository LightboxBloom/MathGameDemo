package com.example.mathgamedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Button[] buttons = new Button[2];

    public static TextView[] textViews = new TextView[6];

    public static List<Integer> oneToThree = new ArrayList<Integer>(Arrays.asList(1,2,3));

    public static List<Integer> oneToFour = new ArrayList<Integer>(Arrays.asList(1,2,3,4));

    public static List<Integer> fourToTen = new ArrayList<Integer>(Arrays.asList(4,5,6,7,8,9,10));

    public static int firstNum;

    public static int secondNum;

    public static int thirdNum;

    public static int correctAns;

    public static int levelNumber = -100;

    public static boolean minPlus = false;
    public static boolean plusPlus = false;
    public static boolean plusMin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //Firebase functionality works when offline, online is required for first launch to retrieve data

        for(int i=0; i<buttons.length; i++)        //initializing Buttons
        {
            String textViewID = "button" + (i+1);

            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(this);
        }

        for(int i=0; i<textViews.length; i++)        //initializing textViews
        {
            String textViewID = "textView" + (i+1);

            int resID = getResources().getIdentifier(textViewID, "id", getPackageName());
            textViews[i] = findViewById(resID);
        }
        FirebaseHandler.getSetUserLevel();
        //sumType();                                                  //sets the plus or minus signs and sets the correctAns value
        //textViews[5].setText("Current Level: " + levelNumber);     //displays current level
    }

    public static void sumType(){
        valueNum();                                                 //sets values of the symbols

        textViews[5].setText("Current Level: " + levelNumber);     //displays current level

        //picks a random order of plus or minus signs
        Collections.shuffle(oneToThree);
        if(oneToThree.get(0) == 1){
            textViews[3].setText("-");
            textViews[4].setText("+");
            correctAns = firstNum - secondNum + thirdNum;
        }
        else if(oneToThree.get(0) == 2){
            textViews[3].setText("+");
            textViews[4].setText("+");
            correctAns = firstNum + secondNum + thirdNum;
        }
        else if(oneToThree.get(0) == 3){
            textViews[3].setText("+");
            textViews[4].setText("-");
            correctAns = firstNum + secondNum - thirdNum;
        }
    }

    public static void valueNum(){
        //1st symbol value
        Collections.shuffle(fourToTen);
        firstNum = fourToTen.get(0);
        textViews[0].setText(String.valueOf(firstNum));
        //2nd symbol value
        Collections.shuffle(oneToFour);
        secondNum = oneToFour.get(0);
        textViews[1].setText(String.valueOf(secondNum));
        //3rd symbol value
        thirdNum = oneToFour.get(1);
        textViews[2].setText(String.valueOf(thirdNum));
    }


    @Override
    public void onClick(View view) {
        EditText editTextNumber = findViewById(R.id.editTextNumber);

        switch (view.getId()){

            case R.id.button1:

                if(editTextNumber.getText().toString().isEmpty())                //checks if user has entered an answer
                {
                    Toast.makeText(this, "Please enter your answer first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //sets user answer to the value they entered in the editTextNumber
                    Integer userAns =
                            Integer.parseInt(String.valueOf(editTextNumber.getText()));

                    //checks if users answer is correct or not
                    if (userAns != correctAns)
                    {
                        Toast.makeText(this, "Incorrect, Please try again", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Correct, Try this next sum!", Toast.LENGTH_SHORT).show();
                        levelNumber++;                                                 //increase user level
                        FirebaseHandler.getSetUserLevel();
                        textViews[5].setText("Current Level: " + levelNumber);         //display new user level
                        sumType();                                                      //generate new sum to be answered
                    }
                    editTextNumber.getText().clear();                                   //clears the users answer for convenience
                }

                break;

            case R.id.button2:
                sumType();          //generates a new sum for the user
                break;
        }
    }
}