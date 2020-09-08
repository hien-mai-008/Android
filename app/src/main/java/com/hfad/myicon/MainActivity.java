package com.hfad.myicon;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Stack;

public class MainActivity<i> extends Activity {
  static final HashMap<Integer, String> idButtonMap = new HashMap<Integer, String>();
  boolean isZero = false;
  double resultNumber;

  // Function to Execute Operator
  public double applyOp(char op, double operand1, double operand2) {
    switch (op) {
      case '+':
        return operand1 + operand2;
      case '-':
        return operand1 - operand2;
      case '*':
        return operand1 * operand2;
      case '/':
        return operand1 / operand2;
    }
    return 0;
  }

  // Check if existing operator and the last one has Precedence or not
  public boolean hasPrecedence(char op1, char op2) {
    return (op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/');
  }

  // Function to calculate math from input
  public double evaluateMath(String input) {
    Stack<Double> valueStack = new Stack<>();
    Stack<Character> operationStack = new Stack<>();
    double currentNumber = 0;
    Character operator;
    Double operand1;
    Double operand2;

    for (int i = 0; i < input.length(); i++) {
      char currentChar = input.charAt(i);
      if (currentChar >= '0' && currentChar <= '9') {
        currentNumber = currentNumber * 10 + (currentChar - '0');
      } else {
        valueStack.push(currentNumber);
        currentNumber = 0;
        while (!operationStack.empty() && hasPrecedence(currentChar, operationStack.peek())) {
          operator = operationStack.pop();
          operand2 = valueStack.pop();
          operand1 = valueStack.pop();
          valueStack.push(applyOp(operator, operand1, operand2));
        }
        operationStack.push(currentChar);
      }
    }
    valueStack.push(currentNumber);

    // Execute remain operator in stack
    while (!operationStack.empty()) {
      operator = operationStack.pop();
      operand2 = valueStack.pop();
      operand1 = valueStack.pop();
      valueStack.push(applyOp(operator, operand1, operand2));
    }

    // The final number in stack value is result
    return valueStack.pop();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.caculator);
    idButtonMap.put(R.id.number0, "0");
    idButtonMap.put(R.id.number1, "1");
    idButtonMap.put(R.id.number2, "2");
    idButtonMap.put(R.id.number3, "3");
    idButtonMap.put(R.id.number4, "4");
    idButtonMap.put(R.id.number5, "5");
    idButtonMap.put(R.id.number6, "6");
    idButtonMap.put(R.id.number7, "7");
    idButtonMap.put(R.id.number8, "8");
    idButtonMap.put(R.id.number9, "9");
    idButtonMap.put(R.id.add, "+");
    idButtonMap.put(R.id.subtract, "-");
    idButtonMap.put(R.id.multiple, "*");
    idButtonMap.put(R.id.division, "/");

    // Get Calculate Result
    final TextView result = findViewById(R.id.calculatorScreen);

    // Get Number Button
    final Button number0 = findViewById(R.id.number0);
    final Button number1 = findViewById(R.id.number1);
    final Button number2 = findViewById(R.id.number2);
    final Button number3 = findViewById(R.id.number3);
    final Button number4 = findViewById(R.id.number4);
    final Button number5 = findViewById(R.id.number5);
    final Button number6 = findViewById(R.id.number6);
    final Button number7 = findViewById(R.id.number7);
    final Button number8 = findViewById(R.id.number8);
    final Button number9 = findViewById(R.id.number9);

    // Get Function Button
    final Button equal = findViewById(R.id.equal);
    final Button add = findViewById(R.id.add);
    final Button subtract = findViewById(R.id.subtract);
    final Button division = findViewById(R.id.division);
    final Button multiple = findViewById(R.id.multiple);
    final Button AC = findViewById(R.id.AC);

    number0.setOnClickListener(this->handlingButtonClickEvent());
    number1.setOnClickListener(this->handlingButtonClickEvent());
    number2.setOnClickListener(this->handlingButtonClickEvent());
    number3.setOnClickListener(this->handlingButtonClickEvent());
    number4.setOnClickListener(this->handlingButtonClickEvent());
    number5.setOnClickListener(this->handlingButtonClickEvent());
    number6.setOnClickListener(this->handlingButtonClickEvent());
    number7.setOnClickListener(this->handlingButtonClickEvent());
    number8.setOnClickListener(this->handlingNumberClickEvent());
    number9.setOnClickListener(this->handlingButtonClickEvent());
    add.setOnClickListener(this->handlingButtonClickEvent());
    subtract.setOnClickListener(this->handlingButtonClickEvent());
    multiple.setOnClickListener(this->handlingButtonClickEvent());
    division.setOnClickListener(this->handlingButtonClickEvent());

    // AC button function
    AC.setOnClickListener(this->handlingACButtonClickEvent());

    // Equal Button Click
    equal.setOnClickListener(this->handlingEqualButtonClickEvent());
  }
  
  // TODO : add type of return value
  final private handlingButtonClickEvent() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int id = v.getId();

        // Check if the number is delete or not
        if (isZero) {
          result.setText("");
          isZero = false;
        }

        // Display on TextView when click button
        result.append(idButtonMap.get(id));
      }
    };
  }
  
  // TODO : add type of return value
  final private handlingACButtonClickEvent() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        result.setText("0");
        isZero = true;
      }
    };
  }
  
  // TODO : add type of return value
  final private handlingEqualButtonClickEvent() {
    return new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onClick(View v) {
        resultNumber = evaluateMath(result.getText().toString());
        DecimalFormat df = new DecimalFormat("0.###");
        result.setText(df.format(resultNumber));
      }
    };
  }
}
