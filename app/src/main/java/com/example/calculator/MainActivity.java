package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    private TextView displayTextview = null;
    private Boolean Cached = false;
    private String preOperate = "null";
    private Button acbutton = null;
    private String tvcache = null;
    private BigDecimal calculatecache = null;
    private Button selectedOperate_btn = null;
    private Boolean calculateComplete = false;

    //private static final BigDecimal INFINITE_BIG_DECIMAL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acbutton = findViewById(R.id.ac_Button);
        displayTextview = findViewById(R.id.display_tv);

          if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //换System San Francisco Display light.ttf字体
        displayTextview.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/systemsanfranciscodisplaythin.ttf"));

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
                    Sirusi.operand1 = new BigDecimal("0");

                } else {
                    //在设置第2个以上小数点
                    if (!isDecimalPointAlreadyexists(tvcache)) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand1 = new BigDecimal(tvcache);

                        // displayTextview.setText(new DecimalFormat("#,###").format(tvcache));

                        displayTextview.setText(new DecimalFormat("#,###").format(new BigDecimal(tvcache + "0")) + ".");
                    }


                }
                //在设置第operand2
            else {
                if (Sirusi.operand2 == null) {
                    //在设置第1个小数点
                    tvcache = 0 + numButton.getText().toString();
                    displayTextview.setText(0 + numButton.getText().toString());
                    Sirusi.operand2 = new BigDecimal(tvcache + "0");
                } else {
                    //在设置第2个小数点
                    if (!isDecimalPointAlreadyexists(tvcache)) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand2 = new BigDecimal(tvcache + "0");
                        //displayTextview.setText(tvcache);
                        displayTextview.setText(new DecimalFormat("#,###").format(new BigDecimal(tvcache + "0")) + ".");
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

            //init layout
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
                    Sirusi.operand1 = new BigDecimal(tvcache);

                } else {
                    //在设置第2个以上数字

                    if (!(Sirusi.operand1.compareTo(BigDecimal.ZERO) == 0 || numButton.getText().toString().equals("0"))) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand1 = new BigDecimal(tvcache);
                    } else {
                        if (Sirusi.operand1.compareTo(BigDecimal.ZERO) == 0 && !isDecimalPointAlreadyexists(tvcache)) {
                            tvcache = numButton.getText().toString();
                            Sirusi.operand1 = new BigDecimal(tvcache);
                        } else {
                            tvcache += numButton.getText().toString();
                            Sirusi.operand1 = new BigDecimal(tvcache);
                        }
                    }

                    //displayTextview.setText(tvcache);
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(new BigDecimal(tvcache), new BigDecimal("0"), "+", "0")).format(new BigDecimal(tvcache)));
                    //displayTextview.setText(new DecimalFormat(getDecimalLength_int_Double(tvcache)).format(tvcache));

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
                    Sirusi.operand2 = new BigDecimal(tvcache);
                } else {
                    if (!(Sirusi.operand2.compareTo(BigDecimal.ZERO) == 0 || numButton.getText().toString().equals("0"))) {
                        tvcache += numButton.getText().toString();
                        Sirusi.operand2 = new BigDecimal(tvcache);
                    } else {
                        if (Sirusi.operand2.compareTo(BigDecimal.ZERO) == 0 && !isDecimalPointAlreadyexists(tvcache)) {
                            tvcache = numButton.getText().toString();
                            Sirusi.operand2 = new BigDecimal(tvcache);
                        } else {
                            tvcache += numButton.getText().toString();
                            Sirusi.operand2 = new BigDecimal(tvcache);
                        }
                    }
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(new BigDecimal(tvcache), new BigDecimal("0"), "+", "0")).format(new BigDecimal(tvcache)));
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
            Sirusi.operand1 = new BigDecimal("0");
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
            Sirusi.operand1 = new BigDecimal("0");
            Sirusi.operate = view.getText().toString();
        }
        //用户没输入op2
        else if (Sirusi.operand2 == null) {
            Sirusi.operate = view.getText().toString();
        } else {
            if (Cached) {

                if (!(Sirusi.operate.equals("+") || Sirusi.operate.equals("-"))) {
                    //前一个不是+/-直接计算保留Cache
                    //计算Cached并Cached=false;
                    Sirusi.operand1 = calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2);
                    //清空op2，计算缓存
                    Sirusi.operand2 = null;
                    tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    Sirusi.operate = view.getText().toString();
                } else {
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
                }
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


    public int getLengthofDecimal_int(BigDecimal orogin) {
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
            return temp.toCharArray().length;
        } else
            return 0;
    }

    public String getMaxLengthofDecimal_pattern(BigDecimal a, BigDecimal b, String oper, String parameter) {
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

        Log.e("maxLength", maxLength + "");
        String temp = "";
        for (int i = 0; i < maxLength; i++)
            temp += parameter;

        Log.e("pattern", "#,##0." + temp);
        if (maxLength == 0) {
            return "#,##0";
        } else {
            return "#,##0." + temp;
        }
    }


    private Boolean isEexists(String str) {
        String pattern = ".*E.*";
        return Pattern.matches(pattern, str);
    }


    public BigDecimal calculoneOperation(BigDecimal a, String operate, BigDecimal b) {
        String res;
        BigDecimal result;
        switch (operate) {
            case "+":

                result = a.add(b);
                Log.e("calculoneOperationresult", result.toString());
                if (result.toPlainString().length() >= 10) {
                    result = new BigDecimal(result.toString(), new MathContext(3, RoundingMode.HALF_UP));
                    displayTextview.setText(result.toEngineeringString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate, "#")).format(result));
                    return result;
                }
            case "-":
                result = a.subtract(b);
                if (result.toPlainString().length() >= 10) {
                    result = new BigDecimal(result.toString(), new MathContext(3, RoundingMode.HALF_UP));
                    displayTextview.setText(result.toEngineeringString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate, "#")).format(result));
                    return result;
                }
            case "×":
                result = a.multiply(b);
                if (result.toPlainString().length() >= 10) {
                    result = new BigDecimal(result.toString(), new MathContext(3, RoundingMode.HALF_UP));
                    displayTextview.setText(result.toEngineeringString());
                    return result;
                } else {
                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate, "#")).format(result));
                    return result;
                }
            case "÷":


                if (Sirusi.operand2.compareTo(BigDecimal.ZERO) == 0)//如果计算结果为无限的话
                {
                    Sirusi.operand1 = null;
                    Sirusi.operand2 = null;
                    Sirusi.operate = "null";
                    tvcache = null;
                    preOperate = "null";
                    calculatecache = null;
                    calculateComplete = true;
                    displayTextview.setText("infinity");

                } else {
                    try {
                        result = a.divide(b);
                    } catch (Exception e) {
                        result = a.divide(b, 10, BigDecimal.ROUND_HALF_UP);
                        Log.e("BigDecimaldivide", e.toString());

                    }

                    if (result.toPlainString().length() >= 10) {
                        result = new BigDecimal(result.toString(), new MathContext(3, RoundingMode.HALF_UP));
                        displayTextview.setText(result.toEngineeringString());
                        return result;
                    } else {
                        displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(a, b, operate, "#")).format(result));
                        return result;
                    }
                }
        }
        return null;
    }


    public void inverseButton_onClick(View view) {
        try {
            if (selectedOperate_btn != null)
                init_opButtonnotSelectedState();
            calculateComplete = false;
            if (Sirusi.operate.equals("null") || Sirusi.operand1 == null) {
                if (Sirusi.operand1 == null) {
                    //Sirusi.operand1 = 0.0;
                    //tvcache = Sirusi.operand1.toString();
                    displayTextview.setText("0");
                    Log.e("inverseButton", Sirusi.operand1 + "");
                } else {
                    Sirusi.operand1 = Sirusi.operand1.multiply(new BigDecimal(-1));
                    //tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    tvcache = new DecimalFormat(getMaxLengthofDecimal_pattern(Sirusi.operand1, new BigDecimal("0"), "+", "#")).format(Sirusi.operand1);
                    displayTextview.setText(tvcache);
                    Log.e("inverseButton", Sirusi.operand1 + "");
                }

            } else {
                Sirusi.operand2 = Sirusi.operand2.multiply(new BigDecimal(-1));
                //tvcache = Sirusi.operand2.toString();

                tvcache = new DecimalFormat(getMaxLengthofDecimal_pattern(Sirusi.operand2, new BigDecimal("0"), "+", "#")).format(Sirusi.operand2);
                displayTextview.setText(tvcache);
                //displayTextview.setText(tvcache);
                //displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));
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
            if (Sirusi.operate.equals("null") || Sirusi.operand1 == null) {
                if (Sirusi.operand1 == null) {
                    //Sirusi.operand1 = 0.0;
                    //tvcache = Sirusi.operand1.toString();
                    displayTextview.setText("0");
                    Log.e("percentButton", Sirusi.operand1 + "");
                } else {
                    Sirusi.operand1 = Sirusi.operand1.divide(new BigDecimal("100"));
                    //tvcache = Sirusi.operand1.toString();
                    //displayTextview.setText(tvcache);
                    tvcache = new DecimalFormat(getMaxLengthofDecimal_pattern(Sirusi.operand1, new BigDecimal("0"), "+", "#")).format(Sirusi.operand1);
                    displayTextview.setText(tvcache);
                    Log.e("percentButton", Sirusi.operand1 + "");
                }

            } else {
                Sirusi.operand2 = Sirusi.operand2.divide(new BigDecimal("100"));
                //tvcache = Sirusi.operand2.toString();

                tvcache = new DecimalFormat(getMaxLengthofDecimal_pattern(Sirusi.operand2, new BigDecimal("0"), "+", "#")).format(Sirusi.operand2);
                displayTextview.setText(tvcache);
                //displayTextview.setText(tvcache);
                //displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(Double.valueOf(tvcache), Double.valueOf("0"), "+")).format(Double.valueOf(tvcache)));
                Log.e("percentButton", Sirusi.operand2 + "");
            }
        } catch (Exception e) {

            Log.e("percentButton", e.toString());

        }


//
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
                //Sirusi.operand1 = calculoneOperation(calculatecache, preOperate, Sirusi.operand1);
                Sirusi.operand1 = calculoneOperation(calculatecache, preOperate, calculoneOperation(Sirusi.operand1, Sirusi.operate, Sirusi.operand2));
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
                        Sirusi.operand1 = new BigDecimal(tvcache);

                    //处理结果并显示
                    //Sirusi.operand1=calculoneOperation(Sirusi.operand1,Sirusi.operate,Sirusi.operand2);
                    //清空op2，计算缓存
                    Sirusi.operand2 = null;
                    tvcache = Sirusi.operand1.toString();

                    displayTextview.setText(new DecimalFormat(getMaxLengthofDecimal_pattern(new BigDecimal(tvcache), new BigDecimal("0"), "+", "#")).format(new BigDecimal(tvcache)));

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
