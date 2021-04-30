package com.example.hw3;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Calculator extends MainActivity implements OnClickListener
{
    /** Called when the activity is first created. */

    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button btnAdd,btnSub,btnMul,btnDiv,btnPow,btnEqu,btnCan,btnClr;
    EditText editText;
    Double oldValue;
    char chOp=' ';

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_layout);

        editText=(EditText) findViewById(R.id.editText);

        btn0 = (Button)findViewById(R.id.button0);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        btn5 = (Button)findViewById(R.id.button5);
        btn6 = (Button)findViewById(R.id.button6);
        btn7 = (Button)findViewById(R.id.button7);
        btn8 = (Button)findViewById(R.id.button8);
        btn9 = (Button)findViewById(R.id.button9);

        btnAdd = (Button)findViewById(R.id.buttonAdd);
        btnSub = (Button)findViewById(R.id.buttonSub);
        btnMul = (Button)findViewById(R.id.buttonMul);
        btnDiv = (Button)findViewById(R.id.buttonDiv);
        btnPow = (Button)findViewById(R.id.buttonPow);

        btnEqu = (Button)findViewById(R.id.buttonEqu);
        btnCan = (Button)findViewById(R.id.buttonCan);
        btnClr = (Button)findViewById(R.id.buttonClear);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnPow.setOnClickListener(this);
        btnEqu.setOnClickListener(this);
        btnCan.setOnClickListener(this);
        btnClr.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        try
        {
            Button btn = (Button)v;
            Double answer, curValue;

            switch(v.getId())
            {
                case R.id.button0:
                case R.id.button1:
                case R.id.button2:
                case R.id.button3:
                case R.id.button4:
                case R.id.button5:
                case R.id.button6:
                case R.id.button7:
                case R.id.button8:
                case R.id.button9:

                    editText.setText(editText.getText()+""+btn.getText()+"");
                    break;

                case R.id.buttonAdd:
                case R.id.buttonSub:
                case R.id.buttonMul:
                case R.id.buttonDiv:
                case R.id.buttonPow:

                    if(editText.getText()+"" == "")
                    {
                        return;
                    }
                    chOp=(btn.getText()+"").charAt(0);
                    oldValue=Double.parseDouble(editText.getText()+"");
                    editText.setText("");
                    break;

                case R.id.buttonCan:

                    oldValue=0.0;
                    editText.setText("");
                    break;

                case R.id.buttonClear:

                    int len = (editText.getText()+"").length();
                    if(len==0)
                    {
                        Toast.makeText(getApplicationContext(), "Nothing to erase...", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        editText.setText((editText.getText()+"").substring(0, len-1));
                        break;
                    }



                case R.id.buttonEqu:

                    if(editText.getText()+"" == "")
                    {
                        return;
                    }
                    curValue = Double.parseDouble(editText.getText()+"");
                    switch (chOp)
                    {
                        case '+':

                            answer = oldValue+curValue;
                            editText.setText(answer+"");
                            break;

                        case '-':

                            answer=oldValue-curValue;
                            editText.setText(answer+"");
                            break;

                        case '*':

                            answer=oldValue*curValue;
                            editText.setText(answer+"");
                            break;

                        case '/':

                            answer=oldValue/curValue;
                            editText.setText(answer+"");
                            break;

                        case '^':

                            answer=Math.pow(oldValue, curValue);
                            editText.setText(answer+"");
                            break;
                    }
            }
        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}