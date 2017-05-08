package com.lxh.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lxh.custom.view.CircularStatisticsView;
import com.lxh.custom.view.HorizontalProgressView;
import com.lxh.custom.view.luckRoller.LuckRollerView;
import com.lxh.custom.view.luckRoller.LuckyBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    CircularStatisticsView circularStatisticsView;
    LuckRollerView luckRollerView;
    Context mContext;
    List<LuckyBean> luckyBeanList = new ArrayList<>();
    int i = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        initView();
//        double a =  test();
//        Log.e("TAG","====一个月积分======"+a);
//        HorizontalProgressView horizontalProgressView = (HorizontalProgressView) findViewById(R.id.progressView);
//        horizontalProgressView.setMaxProgress(1000);
//        horizontalProgressView.setmProgress(500);
    }

    /**
     * 与文字对应的图片
     */
    private int[] mImgs = new int[]{R.mipmap.fanbingbing, R.mipmap.libb,
            R.mipmap.liuss, R.mipmap.lyf, R.mipmap.dlrb,R.mipmap.linzl};
    /**
     * 抽奖的文字
     */
    private String[] mStrs = new String[]{"范冰冰", "李冰冰", "刘诗诗", "刘亦菲",
            "迪丽热巴", "林志玲"};
    /**
     * 每个盘块的颜色
     */
    private int[] mColors = new int[]{0xFFFFC300, 0xFFF17E01, 0xFFFFC300,
            0xFFF17E01, 0xFFFFC300, 0xFFF17E01};

    private void initData(){
        for (int i = 0;i<mStrs.length;i++){
            LuckyBean luckyBean = new LuckyBean();
            luckyBean.setName(mStrs[i]);
            luckyBean.setColor(mColors[i]);
            luckyBean.setImg(mImgs[i]);
            luckyBeanList.add(luckyBean);
        }
    }


//    private void initView() {
//        initData();
//        luckRollerView = (LuckRollerView) findViewById(R.id.luckRollerView);
//        luckRollerView.setData(mContext, luckyBeanList);
//        luckRollerView.setOnLuckyRollerListenner(new LuckRollerView.OnLuckyRollerListenner() {
//            @Override
//            public void onFinish(String data) {
//                Toast.makeText(mContext, "恭喜你抽中了-" + data + "-", Toast.LENGTH_LONG).show();
//            }
//        });







//        circularStatisticsView = (CircularStatisticsView) findViewById(R.id.circularStatisticsView);
//        circularStatisticsView.setOnClickListener(this);
//        circularStatisticsView.setReminderText("剩余");
//        circularStatisticsView.setProgressText("已使用");
//        circularStatisticsView.setProgress(15638,5493);
//        circularStatisticsView.setReminderColor(Color.parseColor("#FF0000FF"));
//        circularStatisticsView.setProgressColor(Color.parseColor("#FFFF0000"));
//        circularStatisticsView.setCircleWidth(200);
//}

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.circularStatisticsView:
//                i+=100;
//                circularStatisticsView.setProgress(15638,i);
//                break;
//
//        }
    }
}














