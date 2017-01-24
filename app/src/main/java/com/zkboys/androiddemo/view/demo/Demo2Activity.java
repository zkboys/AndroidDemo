package com.zkboys.androiddemo.view.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.utils.Pos;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class Demo2Activity extends AppCompatActivity {

    //订单菜品集合
    private List<FoodsBean> foodsBean;

    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, Demo2Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        ButterKnife.bind(this);


        Button bt_print = (Button) findViewById(R.id.bt_print);


        bt_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 开启一个子线程
                new Thread() {
                    public void run() {
                        try {
                            // 192.168.0.137 9100
                            Pos pos = new Pos("192.168.0.137", 9100, "GBK");    //第一个参数是打印机网口IP

                            //初始化打印机
                            pos.initPos();

                            //初始化订单数据
                            initData();

                            pos.bold(true);
                            pos.printTabSpace(2);
                            pos.printWordSpace(1);
                            pos.printText("**测试店铺");

                            pos.printLocation(0);
                            pos.printTextNewLine("----------------------------------------------");
                            pos.bold(false);
                            pos.printTextNewLine("订 单 号：1005199");
                            pos.printTextNewLine("用 户 名：15712937281");
                            pos.printTextNewLine("桌    号：3号桌");
                            pos.printTextNewLine("订单状态：订单已确认");
                            pos.printTextNewLine("订单日期：2016/2/19 12:34:53");
                            pos.printTextNewLine("付 款 人：线下支付（服务员：宝哥）");
                            pos.printTextNewLine("服 务 员：1001");
                            pos.printTextNewLine("订单备注：不要辣，少盐");
                            pos.printLine(2);

                            pos.printText("品项");
                            pos.printLocation(20, 1);
                            pos.printText("单价");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("数量");
                            pos.printWordSpace(3);
                            pos.printText("小计");
                            pos.printTextNewLine("----------------------------------------------");


                            for (FoodsBean foods : foodsBean) {
                                pos.printTextNewLine(foods.getName());
                                pos.printLocation(20, 1);
                                pos.printText(foods.getPrice());
                                pos.printLocation(99, 1);
                                pos.printWordSpace(1);
                                pos.printText(foods.getNumber());
                                pos.printWordSpace(3);
                                pos.printText(foods.getSum());
                            }

                            pos.printTextNewLine("----------------------------------------------");

                            pos.printLocation(1);
                            pos.printLine(2);
                            //打印二维码
                            pos.qrCode("http://blog.csdn.net/haovip123");

                            //切纸
                            pos.feedAndCut();

                            pos.closeIOAndSocket();
                            pos = null;
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();

            }
        });
    }

    private void initData() {
        foodsBean = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            FoodsBean fb = new FoodsBean();
            fb.setName("测试菜品" + i);
            fb.setPrice("90.00");
            fb.setNumber("1" + i);
            fb.setSum("10" + i + ".00");
            foodsBean.add(fb);
        }
    }
}

class FoodsBean {
    private String name;
    private String price;
    private String number;
    private String sum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}