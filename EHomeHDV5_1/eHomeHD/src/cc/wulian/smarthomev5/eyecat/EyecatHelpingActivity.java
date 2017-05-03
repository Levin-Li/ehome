package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cc.wulian.smarthomev5.R;

/**
 * Created by Administrator on 2017/5/3.
 */

public class EyecatHelpingActivity extends Activity{
    private TextView eyecat_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_helping);
        eyecat_return = (TextView) findViewById(R.id.eyecat_return);
        eyecat_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
