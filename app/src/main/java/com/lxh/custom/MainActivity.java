package com.lxh.custom;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lxh.custom.activity.AbilityMapActivity;
import com.lxh.custom.activity.CircularStatisticsActivity;
import com.lxh.custom.activity.GoogleIconActivity;
import com.lxh.custom.activity.LuckRollerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.click1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.click2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.click3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.click4);
        button4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click1:
                startActivity(new Intent(this, CircularStatisticsActivity.class));
                break;
            case R.id.click2:
                startActivity(new Intent(this, LuckRollerActivity.class));
                break;
            case R.id.click3:
                startActivity(new Intent(this, GoogleIconActivity.class));
                break;
            case R.id.click4:
                startActivity(new Intent(this, AbilityMapActivity.class));
                break;
        }
    }
}














