package com.example.mathgamedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public static int currentLevel = 1;

    public static boolean minPlus = false;
    public static boolean plusPlus = false;
    public static boolean plusMin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<buttons.length; i++)        //initializing textViews
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
        sumType();
        textViews[5].setText("Current Level: " + currentLevel);
    }

    public static void sumType(){
        valueNum();

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
        //1st
        Collections.shuffle(fourToTen);
        firstNum = fourToTen.get(0);
        textViews[0].setText(String.valueOf(firstNum));
        //2nd
        Collections.shuffle(oneToFour);
        secondNum = oneToFour.get(0);
        textViews[1].setText(String.valueOf(secondNum));
        //3rd
        thirdNum = oneToFour.get(1);
        textViews[2].setText(String.valueOf(thirdNum));
    }


    @Override
    public void onClick(View view) {
        EditText editTextNumber = findViewById(R.id.editTextNumber);

        switch (view.getId()){

            case R.id.button1:

                if(editTextNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Please enter your answer first", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Integer userAns =
                            Integer.parseInt(String.valueOf(editTextNumber.getText()));

                    if (userAns != correctAns)
                    {
                        Toast.makeText(this, "Incorrect, Please try again", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this, "Correct, Try this next sum!", Toast.LENGTH_SHORT).show();
                        currentLevel++;
                        textViews[5].setText("Current Level: " + currentLevel);
                        sumType();
                    }
                    editTextNumber.getText().clear();
                }

                break;

            case R.id.button2:
                sumType();
                break;
        }
    }
}