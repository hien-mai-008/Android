package com.hfad.myicon;
import androidx.annotation.RequiresApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity<i> extends Activity {
    boolean isZero = false;
    double resultNumber;
    HashMap<Integer,String> idButton = new HashMap<Integer,String>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    // Function to Execute Operator
    public double applyOp(char op,double b,double a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return 0;
    }

    // Check if existing operator and the last one has Precedence or not
    public boolean hasPrecedence(char op1,char op2)
    {
        return (op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/');
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    // Function to calculate math from input
    public double evaluate(String input)
    {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        double currentNumber = 0;

        for (int i = 0; i < input.length();i++)
        {
            char currentChar = input.charAt(i);

//             If the input is number put to stack values
            if (currentChar >= '0' && currentChar <= '9')
            {
                currentNumber = currentNumber * 10 + Double.parseDouble(String.valueOf(currentChar));
            }

//             If the input is operator put to stack ops
            else
            {
                values.push(currentNumber);
                currentNumber = 0;
                while (!ops.empty() && hasPrecedence(currentChar,ops.peek()))
                {
                    values.push(applyOp(ops.pop(),values.pop(),values.pop()));
                }
                ops.push(currentChar);
            }
        }
        values.push(currentNumber);

        // Execute remain operator in stack
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // The final number in stack value is result
        return values.pop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caculator);

        idButton.put(1000112,"0");
        idButton.put(1000119,"1");
        idButton.put(1000121,"2");
        idButton.put(1000116,"3");
        idButton.put(1000118,"4");
        idButton.put(1000125,"5");
        idButton.put(1000127,"6");
        idButton.put(1000122,"7");
        idButton.put(1000124,"8");
        idButton.put(1000131,"9");
        idButton.put(1000000,"+");
        idButton.put(1000014,"-");
        idButton.put(1000146,"*");
        idButton.put(1000169,"/");

        // Get Calculate Result
        final TextView result = findViewById(R.id.calculatorScreen);

        // Get Number Button
        Button number0 = findViewById(R.id.number0);
        Button number1 = findViewById(R.id.number1);
        Button number2 = findViewById(R.id.number2);
        Button number3 = findViewById(R.id.number3);
        Button number4 = findViewById(R.id.number4);
        Button number5 = findViewById(R.id.number5);
        Button number6 = findViewById(R.id.number6);
        Button number7 = findViewById(R.id.number7);
        Button number8 = findViewById(R.id.number8);
        Button number9 = findViewById(R.id.number9);

        // Get Function Button
        Button equal = findViewById(R.id.equal);
        Button add = findViewById(R.id.add);
        Button subtract = findViewById(R.id.subtract);
        Button division = findViewById(R.id.division);
        Button multiple = findViewById(R.id.multiple);
        Button AC = findViewById(R.id.AC);



        final View.OnClickListener calculatorListener = new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                int id = v.getId();

                // Check if the number is delete or not
                if (isZero) {
                    result.setText("");
                    isZero = false;
                }
                // Display on TextView when click button
                result.append(idButton.get(id));
//                switch(id) {
//                    case R.id.number0:
//                        result.append("0");
//                        break;
//                    case R.id.number1:
//                        result.append("1");
//                        break;
//                    case R.id.number2:
//                        result.append("2");
//                        break;
//                    case R.id.number3:
//                        result.append("3");
//                        break;
//                    case R.id.number4:
//                        result.append("4");
//                        break;
//                    case R.id.number5:
//                        result.append("5");
//                        break;
//                    case R.id.number6:
//                        result.append("6");
//                        break;
//                    case R.id.number7:
//                        result.append("7");
//                        break;
//                    case R.id.number8:
//                        result.append("8");
//                        break;
//                    case R.id.number9:
//                        result.append("9");
//                        break;
//                    case R.id.add:
//                        result.append("+");
//                        break;
//                    case R.id.subtract:
//                        result.append("-");
//                        break;
//                    case R.id.multiple:
//                        result.append("*");
//                        break;
//                    case R.id.division:
//                        result.append("/");
//                        break;
//                }
            }
        };
        number0.setOnClickListener(calculatorListener);
        number1.setOnClickListener(calculatorListener);
        number2.setOnClickListener(calculatorListener);
        number3.setOnClickListener(calculatorListener);
        number4.setOnClickListener(calculatorListener);
        number5.setOnClickListener(calculatorListener);
        number6.setOnClickListener(calculatorListener);
        number7.setOnClickListener(calculatorListener);
        number8.setOnClickListener(calculatorListener);
        number9.setOnClickListener(calculatorListener);
        add.setOnClickListener(calculatorListener);
        subtract.setOnClickListener(calculatorListener);
        multiple.setOnClickListener(calculatorListener);
        division.setOnClickListener(calculatorListener);

        // AC button function
        AC.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                result.setText("0");
                isZero = true;
            }
        });

        // Equal Button Click
        equal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resultNumber = evaluate(result.getText().toString());
                DecimalFormat df = new DecimalFormat("0.###");
                result.setText(df.format(resultNumber));
            }
        });

    }
}

