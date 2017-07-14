package com.lxh.custom.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lxh.custom.R;
import com.lxh.custom.view.CircularStatisticsView;

public class CircularStatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    CircularStatisticsView circularStatisticsView;
    int i = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_statistics);
        initView();
    }

    private void initView() {
        circularStatisticsView = (CircularStatisticsView) findViewById(R.id.circularStatisticsView);
        circularStatisticsView.setOnClickListener(this);
        circularStatisticsView.setReminderText("剩余");
        circularStatisticsView.setProgressText("已使用");
        circularStatisticsView.setProgress(15638, 5493);
        circularStatisticsView.setReminderColor(Color.parseColor("#FF0000FF"));
        circularStatisticsView.setProgressColor(Color.parseColor("#FFFF0000"));
        circularStatisticsView.setCircleWidth(200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circularStatisticsView:
                i+=100;
                circularStatisticsView.setProgress(15638,i);
                break;

        }
    }
}
