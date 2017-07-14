package com.lxh.custom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.lxh.custom.R;
import com.lxh.custom.view.AbilityMapView;

public class AbilityMapActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    AbilityMapView abilityMapView;
    SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5;
    private int[] s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_map);
        s = new int[]{90, 50, 60, 40, 35};
        abilityMapView = (AbilityMapView) findViewById(R.id.abilityView);
        String [] t = {"攻击","防御","生存","控制","辅助"};
        abilityMapView.setAbility(t);
        abilityMapView.setAbilityScore(s);
        abilityMapView.setAbilityFullMark(100);
        abilityMapView.setLayer(3);



        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setOnSeekBarChangeListener(this);
        seekBar1.setMax(100);
        seekBar1.setProgress(s[0]);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar2.setMax(100);
        seekBar2.setProgress(s[1]);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar3.setMax(100);
        seekBar3.setProgress(s[2]);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBar4.setOnSeekBarChangeListener(this);
        seekBar4.setMax(100);
        seekBar4.setProgress(s[3]);
        seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
        seekBar5.setOnSeekBarChangeListener(this);
        seekBar5.setMax(100);
        seekBar5.setProgress(s[4]);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.seekBar1:
                s[0] = progress;
                abilityMapView.setAbilityScore(s);
                break;
            case R.id.seekBar2:
                s[1] = progress;
                abilityMapView.setAbilityScore(s);
                break;
            case R.id.seekBar3:
                s[2] = progress;
                abilityMapView.setAbilityScore(s);
                break;
            case R.id.seekBar4:
                s[3] = progress;
                abilityMapView.setAbilityScore(s);
                break;
            case R.id.seekBar5:
                s[4] = progress;
                abilityMapView.setAbilityScore(s);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
