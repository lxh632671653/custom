package com.lxh.custom.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lxh.custom.R;
import com.lxh.custom.view.luckRoller.LuckRollerView;
import com.lxh.custom.view.luckRoller.LuckyBean;

import java.util.ArrayList;
import java.util.List;

import static com.lxh.custom.R.id.luckRollerView;

public class LuckRollerActivity extends AppCompatActivity {

    LuckRollerView luckRollerView;
    Context mContext;
    List<LuckyBean> luckyBeanList = new ArrayList<>();
    /**
     * 与文字对应的图片
     */
    private int[] mImgs = new int[]{R.mipmap.fanbingbing, R.mipmap.libb,
            R.mipmap.liuss, R.mipmap.lyf, R.mipmap.dlrb, R.mipmap.linzl};
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_roller);
        mContext = this;
        initView();
    }

    private void initData() {
        for (int i = 0; i < mStrs.length; i++) {
            LuckyBean luckyBean = new LuckyBean();
            luckyBean.setName(mStrs[i]);
            luckyBean.setColor(mColors[i]);
            luckyBean.setImg(mImgs[i]);
            luckyBeanList.add(luckyBean);
        }
    }

    private void initView() {
        initData();
        luckRollerView = (LuckRollerView) findViewById(R.id.luckRollerView);
        luckRollerView.setData(mContext, luckyBeanList);
        luckRollerView.setOnLuckyRollerListenner(new LuckRollerView.OnLuckyRollerListenner() {
            @Override
            public void onFinish(String data) {
                Toast.makeText(mContext, "恭喜你抽中了-" + data + "-", Toast.LENGTH_LONG).show();
            }
        });
    }
}
