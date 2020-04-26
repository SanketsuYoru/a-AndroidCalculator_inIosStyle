package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private TextView displayTextview = null;
    //private Boolean Calculated=false;
    private Boolean Cached = false;
    private String preOperate = "null";
    private Button acbutton = null;
    private String tvcache = null;
    private Double calculatecache = null;
    private Button selectedOperate_btn = null;
    private Boolean calculateComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acbutton = findViewById(R.id.ac_Button);

        displayTextview = findViewById(R.id.display_tv);
    }


    private Boolean isDecimalPointAlreadyexists(String str) {
        String pattern = ".*\\..*";
        return Pattern.matches(pattern, str);
    }


    //小数点点击
    public void decimalpointButton_onClick(View view) {
        if (calculateComplete) {
            Sirusi.operand1 = null;
            Sirusi.operand2 = null;
            Sirusi.operate = "null";
            tvcache = null;
            preOperate = "null";
            calculateComplete = false;
        }

        try {

//            if(calculatecache!=null)
//            {
//                //将暂存的处理结果给op1
//                Sirusi.operand1=calculatecache;
//                calculatecache=null;
//                //Sirusi.operate="null";
//            }


            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();
            acbutton.setText("C");
            //Calculated=false;
            Button numButton = (Button) view;
            //在设置operand1
            if (Sirusi.operand1 == null || Sirusi.operate.equals("null"))

                if (Sirusi.operand1 == null) {
                    //在设置第1个小数点
                    tvcache = "0.";
                    displayTextview.setText(tvcache);
                    Sirusi.operand1 = Double.valueOf("0.0");

                } else {
                    //在设置第2个以上小数点
                    if (!isDecimalPointAlreadyexists(tvcache)) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand1 = Double.valueOf(tvcache);

                        // displayTextview.setText(new DecimalFormat("#,###").format(tvcache));

                        displayTextview.setText(new DecimalFormat("#,###").format(Double.valueOf(tvcache + "0")) + ".");
                    }


                }
                //在设置第operand2
            else {
                if (Sirusi.operand2 == null) {
                    //在设置第1个小数点
                    tvcache = 0 + numButton.getText().toString();
                    displayTextview.setText(0 + numButton.getText().toString());
                    Sirusi.operand2 = Double.valueOf(tvcache + "0");
                } else {
                    //在设置第2个小数点
                    if (!isDecimalPointAlreadyexists(tvcache)) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand2 = Double.valueOf(tvcache + "0");
                        //displayTextview.setText(tvcache);
                        displayTextview.setText(new DecimalFormat("#,###").format(Double.valueOf(tvcache + "0")) + ".");
                    }
                }

            }
            Log.e("tvcache", tvcache);
            Log.e("operand1", Sirusi.operand1.toString());
            Log.e("operate", Sirusi.operate);
            Log.e("operand2", Sirusi.operand2.toString());
        } catch (Exception e) {
            Log.e("numButton_onClick", e.toString());
        }

    }


    public void numButton_onClick(View view) {
        if (calculateComplete) {
            Sirusi.operand1 = null;
            Sirusi.operand2 = null;
            Sirusi.operate = "null";
            tvcache = "";
            preOperate = "null";
            calculateComplete = false;
        }


        try {


            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();
            acbutton.setText("C");
            //Calculated = false;
            Button numButton = (Button) view;


            //在设置operand1
            if (Sirusi.operand1 == null || Sirusi.operate.equals("null")) {
                if (Sirusi.operand1 == null) {
                    //在设置第1个数字
                    tvcache = numButton.getText().toString();
                    displayTextview.setText(numButton.getText().toString());
                    Sirusi.operand1 = Double.valueOf(tvcache);

                } else {
                    //在设置第2个以上数字

                    if (!(Sirusi.operand1 == 0.0 && numButton.getText().toString().equals("0"))) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand1 = Double.valueOf(tvcache);
                    } else {
                        if (Sirusi.operand1 == 0.0 && !isDecimalPointAlreadyexists(tvcache)) {
                            tvcache = numButton.getText().toString();
                            Sirusi.operand1 = Double.valueOf(tvcache);
                        } else {
                            tvcache += numButton.getText().toString();
                            Sirusi.operand1 = Double.valueOf(tvcache);
                        }
                    }


                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));

                    //displayTextview.setText(new DecimalFormat("#,###").format(Sirusi.operand1));
                    //displayTextview.setText(new DecimalFormat("#,###").format(Double.valueOf(tvcache)));
                    //displayTextview.setText(tvcache);
                }

            }


            //在设置第operand2
            else {
                if (Sirusi.operand2 == null) {
                    //在设置第1个数字
                    tvcache = numButton.getText().toString();
                    displayTextview.setText(numButton.getText().toString());
                    Sirusi.operand2 = Double.valueOf(tvcache);
                } else {
                    if (!(Sirusi.operand2 == 0.0 && numButton.getText().toString().equals("0"))) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand2 = Double.valueOf(tvcache);
                    } else {
                        if (Sirusi.operand2 == 0.0 && !isDecimalPointAlreadyexists(tvcache)) {
                            tvcache = numButton.getText().toString();
                            Sirusi.operand2 = Double.valueOf(tvcache);
                        } else {
                            tvcache += numButton.getText().toString();
                            Sirusi.operand2 = Double.valueOf(tvcache);
                        }
                    }
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));
                    //displayTextview.setText(tvcache);
                    //displayTextview.setText(new DecimalFormat("#,###").format(Sirusi.operand2));
                    //displayTextview.setText(new DecimalFormat("#,###").format(Double.valueOf(tvcache)));
                }
            }
            Log.e("tvcache", tvcache);
            Log.e("operand1", Sirusi.operand1.toString());
            Log.e("operate", Sirusi.operate);
            Log.e("operand2", Sirusi.operand2.toString());


        } catch (Exception e) {
            Log.e("numButton_onClick", e.toString());
        }

    }

    public void ACButton_onClick(View view) {


        try {
            if (acbutton.getText().toString().equals("C")) {
                //Clean
                if (Sirusi.operand1 != null && Sirusi.operate.equals("null")) {
                    acbutton.setText("AC");
                    Sirusi.operand1 = null;
                    tvcache = null;
                    displayTextview.setText("0");
                } else {
                    acbutton.setText("AC");
                    Sirusi.operand2 = null;
                    tvcache = null;
                    displayTextview.setText("0");
                }

            } else {
                //All Clean
                if (selectedOperate_btn != null)
                    init_opButtonnotSelectedState();
                displayTextview.setText("0");
                //acbutton.setText("AC");
                tvcache = null;
                Sirusi.operand1 = null;
                Sirusi.operand2 = null;
                Sirusi.operate = "null";
                //calculatecache=null;
            }

            Log.e("operand1", tvcache);
            Log.e("operate", tvcache);
            Log.e("operand2", tvcache);
        } catch (Exception e) {
            Log.e("ACButton_onClick", e.toString());
        }

    }


    private void init_opButtonnotSelectedState() {
        selectedOperate_btn.setBackgroundResource(R.drawable.btn_selector_yellow);
        selectedOperate_btn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.buttonUnLocked_Textcolor));
        selectedOperate_btn = null;
    }

    private void opButton_notSelectedState(Button view) {
        selectedOperate_btn.setBackgroundResource(R.drawable.btn_selector_yellow);
        selectedOperate_btn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.buttonUnLocked_Textcolor));
        selectedOperate_btn = view;
        selectedOperate_btn.setBackgroundResource(R.drawable.lockedbtn_selector);
        selectedOperate_btn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.buttonLocked_Textcolor));
        //selectedOperate_btn.setTextColor(ContextCompat.getColor(this.getBaseContext(),R.color.buttonLocked_Textcolor));
    }

    private void opButton_SelectedState(Button view) {

        selectedOperate_btn = view;
        selectedOperate_btn.setBackgroundResource(R.drawable.lockedbtn_selector);
        selectedOperate_btn.setTextColor(ContextCompat.getColor(this.getBaseContext(), R.color.buttonLocked_Textcolor));
    }

    //如果是加减
    public void addORsub_onClick(Button view) {
        //如果用户没输入op1直接按
        if (Sirusi.operate.equals("null") && Sirusi.operand1 == null) {
            Sirusi.operand1 = 0.0;
            Sirusi.operate = view.getText().toString();
        }
        //用户没输入op2
        else if (Sirusi.operand2 == null) {
            Sirusi.operate = view.getText().toString();
        }
        //用户都输入了
        else {
            if (Cached) {
                //计算Cached并Cached=false;
                Sirusi.operand1 = calculoneOperation(calculatecache, preOperate, calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2));
                //清空op2，计算缓存
                Sirusi.operand2 = null;
                tvcache = Sirusi.operand1.toString();
                //displayTextview.setText(tvcache);
                Sirusi.operate = view.getText().toString();
                calculatecache = null;
                preOperate = "null";
                Cached = false;
            } else
            //不需要处理Cache
            {
                //处理结果并显示
                Sirusi.operand1 = calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2);
                //清空op2，计算缓存
                Sirusi.operand2 = null;
                tvcache = Sirusi.operand1.toString();
                //displayTextview.setText(tvcache);
                Sirusi.operate = view.getText().toString();
                calculatecache = null;
                preOperate = "null";
            }
        }

    }

    //如果是乘除
    public void mulORdiv_onClick(Button view) {

        if (Sirusi.operate.equals("null") && Sirusi.operand1 == null) {
            Sirusi.operand1 = 0.0;
            Sirusi.operate = view.getText().toString();
        }
        //用户没输入op2
        else if (Sirusi.operand2 == null) {
            Sirusi.operate = view.getText().toString();
        } else {
            if (Cached) {
                //计算Cached并Cached=false;
                Sirusi.operand1 = calculoneOperation(calculatecache, preOperate, calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2));
                //清空op2，计算缓存
                Sirusi.operand2 = null;
                tvcache = Sirusi.operand1.toString();
                //displayTextview.setText(tvcache);
                Sirusi.operate = view.getText().toString();
                calculatecache = null;
                preOperate = "null";
                Cached = false;
            } else {
                /*没有需要计算的cache */

                //前一个运算符是+/-需要Cache
                if (Sirusi.operate.equals("+") || Sirusi.operate.equals("-")) {
                    calculatecache = Sirusi.operand1;
                    preOperate = Sirusi.operate;
                    Sirusi.operate = view.getText().toString();
                    Sirusi.operand1 = Sirusi.operand2;
                    Sirusi.operand2 = null;
                    Cached = true;
                } else {
                    //前一个不是+/-直接计算不Cache
                    //计算Cached并Cached=false;
                    Sirusi.operand1 = calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2);
                    //清空op2，计算缓存
                    Sirusi.operand2 = null;
                    tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    Sirusi.operate = view.getText().toString();
                    calculatecache = null;
                    preOperate = "null";
                    Cached = false;

                }
            }
        }

    }


    public int getLengthofDecimal_int(Double orogin) {
        BigDecimal bd = new BigDecimal(orogin.toString());
        String pattern = ".*\\..*";
        String orogin_str = bd.toPlainString();
        boolean isMatch = Pattern.matches(pattern, orogin_str);
        if (isMatch) {
            String temp = "";
            pattern = "(\\d*\\.)(\\d*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(orogin_str);
            if (m.find()) {
                temp = m.group(2);
                Log.e("orogin.toString()", orogin_str);
                Log.e("Decimal", m.group(2));
            }
            if (m.group(2).equals("0"))
                return 0;
            else
                return temp.toCharArray().length;
        } else
            return 0;
    }

    public String getMaxLengthofDecimal_pattern(Double a, Double b, String oper) {
        int lengthOfa = getLengthofDecimal_int(a);
        int lengthOfb = getLengthofDecimal_int(b);
        int maxLength;
        if (oper.equals("+") || oper.equals("-")) {
            maxLength = Math.max(lengthOfa, lengthOfb);
        } else if (oper.equals("×"))//如果是乘除运算的话
        {
            maxLength = lengthOfa + lengthOfb;
        } else
            maxLength = lengthOfa + lengthOfb;//以后再说


        String temp = "";
        for (int i = 0; i < maxLength; i++)
            temp += "#";

        //Log.e("pattern", "#,###."+temp);
        if (maxLength == 0) {
            return "#,###";
        } else {
            return "#,###." + temp;
        }
    }


//    private void setDispalyTextviewWith(String orogin_str)
//    {
//        Log.e("setDispalyTextviewWith_orogin_str",orogin_str);
//        //String pattern = "(\\d*\\.)(\\d*)";
//        String pattern = "(\\d*)(\\.)(\\d*)";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(orogin_str);
//        String intpart="";
//        String decpart="";
//        if (m.find()) {
//            intpart= m.group(1);
//            decpart=m.group(3);
//        }
//        else if(orogin_str.length()>10)
//        {
//            displayTextview.setText(String.format("%E", orogin_str));
//            return;
//        }
//        else
//        {
//            displayTextview.setText(orogin_str);
//            return;
//        }
//
//        // displayTextview.setText(String.format("%E", orogin_str));
//        //displayTextview.setText(orogin_str);
//        // displayTextview.setText(intpart+"+"+String.format("%E", decpart));
//        Log.e("setDispalyTextviewWith_intpart",intpart);
//        Log.e("setDispalyTextviewWith_decpart",decpart);
//        if (orogin_str.length() >= 11) {
//            if(intpart.length()>=10)
//            {
//                 displayTextview.setText(String.format("%E", orogin_str));
//            }
//            else
//            {
//                displayTextview.setText(orogin_str);
//            }
//        }
//        else
//        {
//            displayTextview.setText(orogin_str);
//        }
//    }


    private Boolean isEexists(String str) {
        String pattern = ".*E.*";
        return Pattern.matches(pattern, str);
    }


    public Double calculoneOperation(Double a, String operate, Double b) {
        String res;
        Double result = 0.0;
        switch (operate) {
            case "+":

                result = a + b;
                if (isEexists(result.toString())) {
                    displayTextview.setText(result.toString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate)).format(result));
                    return result;
                }
            case "-":
                result = a - b;
                if (isEexists(result.toString())) {
                    displayTextview.setText(result.toString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate)).format(result));
                    return result;
                }
            case "×":
                result = a * b;
                if (isEexists(result.toString())) {
                    displayTextview.setText(result.toString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate)).format(result));
                    return result;
                }
            case "÷":
                result = a / b;
                if (isEexists(result.toString())) {
                    displayTextview.setText(result.toString());
                    return result;
                } else {
                    displayTextview.setText(result.toString());
                    //displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a,b,operate)).format(result));
                    return result;
                }
        }
        return null;
    }


    public void inverseButton_onClick(View view) {
        try {
            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();
            calculateComplete = false;
            if ((Double.valueOf(tvcache) + 0.0) == Sirusi.operand1 || Sirusi.operand1 == null) {
                if (Sirusi.operand1 == null) {
                    Sirusi.operand1 = -Double.valueOf(tvcache);
                    tvcache = Sirusi.operand1.toString();
                    displayTextview.setText(tvcache);
                    Log.e("inverseButton", Sirusi.operand1 + "");
                } else {
                    Sirusi.operand1 = -Sirusi.operand1;
                    tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));
                    Log.e("inverseButton", Sirusi.operand1 + "");
                }

            } else {
                Sirusi.operand2 = -Sirusi.operand2;
                tvcache = Sirusi.operand2.toString();
                //displayTextview.setText(tvcache);
                displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));
                Log.e("inverseButton", Sirusi.operand2 + "");
            }
        } catch (Exception e) {

            Log.e("inverseButton", e.toString());

        }
    }


    public void percentButton_onClick(View view) {
        try {
            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();
            calculateComplete = false;
            if ((Double.valueOf(tvcache) + 0.0) == Sirusi.operand1 || Sirusi.operand1 == null) {
                if (Sirusi.operand1 == null) {
                    Sirusi.operand1 = Double.valueOf(tvcache);
                    tvcache = Sirusi.operand1.toString();
                    displayTextview.setText(tvcache);
                    Log.e("inverseButton", Sirusi.operand1 + "");
                } else {

                    int resdec = getLengthofDecimal_int(Sirusi.operand1 / 100);

                    Log.e("dec", resdec + "个");
                    if (resdec >= 10) {
                        int dec = getLengthofDecimal_int(Sirusi.operand1) + 2;
                        Sirusi.operand1 = Double.valueOf(String.format(new String("%." + dec + "f"), Sirusi.operand1 / 100));
                    } else {
                        Sirusi.operand1 = Sirusi.operand1 / 100;
                    }


                    tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));

                    Log.e("inverseButton", Sirusi.operand1 + "");
                }

            } else {
//                Sirusi.operand2=Sirusi.operand2/100;
//                tvcache=Sirusi.operand2.toString();
//                //displayTextview.setText(tvcache);
//                displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache),Double.valueOf("0"),"+")).format(Double.valueOf(tvcache)));
//                Log.e("inverseButton",Sirusi.operand2+"");

                int resdec = getLengthofDecimal_int(Sirusi.operand2 / 100);

                Log.e("dec", resdec + "个");
                if (resdec >= 10) {
                    int dec = getLengthofDecimal_int(Sirusi.operand2) + 2;
                    Sirusi.operand2 = Double.valueOf(String.format(new String("%." + dec + "f"), Sirusi.operand2 / 100));
                } else {
                    Sirusi.operand2 = Sirusi.operand2 / 100;
                }


                tvcache = Sirusi.operand2.toString();
                //displayTextview.setText(tvcache);
                displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));

                Log.e("inverseButton", Sirusi.operand1 + "");


            }
        } catch (Exception e) {

            Log.e("inverseButton", e.toString());

        }
    }


    public void equalsButton_onClick(View view) {
        //calculateComplete=false;
        try {

            //init 运算符按钮颜色
            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();

            //计算结果
            if (Cached) {
                //计算Cached并Cached=false;
                Sirusi.operand1 = calculoneOperation(calculatecache, preOperate, Sirusi.operand1);
                //清空op2，计算缓存
                Sirusi.operand2 = null;
                tvcache = Sirusi.operand1.toString();
                //displayTextview.setText(tvcache);
                Sirusi.operate = "null";
                calculatecache = null;
                preOperate = "null";
                Cached = false;
            } else
            //不需要处理Cache
            {
                try {

                    //处理结果并显示
                    Sirusi.operand1 = calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2);
                    //清空op2，计算缓存
                    Sirusi.operand2 = null;
                    tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    Sirusi.operate = "null";
                    calculatecache = null;
                    preOperate = "null";
                    calculateComplete = true;
                } catch (Exception e) {
                    if (Sirusi.operand1 == null)
                        Sirusi.operand1 = Double.valueOf(tvcache);

                    //处理结果并显示
                    //Sirusi.operand1=calculoneOperation(Sirusi.operand1,Sirusi.operate,Sirusi.operand2);
                    //清空op2，计算缓存
                    Sirusi.operand2 = null;
                    tvcache = Sirusi.operand1.toString();

                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));

                    //displayTextview.setText(tvcache);
                    Sirusi.operate = "null";
                    calculatecache = null;
                    preOperate = "null";
                    Log.e("用户骚操作,被我逮到了", e.toString());
                }

            }


        } catch (Exception e) {
            Log.e("你是来捣乱的吧", e.toString());
        }


    }


    public void opButton_onClick(View view) {
        calculateComplete = false;

        Button orgin = (Button) view;
        //设置颜色
        if (selectedOperate_btn == null || selectedOperate_btn.getText().toString().equals(((Button) view).getText().toString())) {
            opButton_SelectedState(orgin);
        } else {
            opButton_notSelectedState(orgin);
        }


        if (orgin.getText().toString().equals("+") || orgin.getText().toString().equals("-")) {
            //如果按了加减的话
            addORsub_onClick(orgin);

        } else {
            //如果按了乘除的话
            mulORdiv_onClick(orgin);

        }


    }
}
