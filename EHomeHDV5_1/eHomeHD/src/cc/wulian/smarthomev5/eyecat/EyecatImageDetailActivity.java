package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;

import cc.wulian.smarthomev5.R;



public class EyecatImageDetailActivity extends Activity
{
    private String bid;
    private String fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_image_detail);
        bid = getIntent().getStringExtra("bid");
        fid = getIntent().getStringExtra("fid");
        initView();
    }
    private void initView(){
    }
}
