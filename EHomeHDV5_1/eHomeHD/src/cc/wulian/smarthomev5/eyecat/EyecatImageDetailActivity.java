package cc.wulian.smarthomev5.eyecat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.eques.icvss.utils.ELog;
import com.nostra13.universalimageloader.core.ImageLoader;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.utils.FileUtil;


public class EyecatImageDetailActivity extends Activity
{
    private String url;
    private ImageView detailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eyecat_activity_image_detail);
        url = getIntent().getStringExtra("url");
        ImageView saveImageView = (ImageView) findViewById(R.id.save_imageview);
        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailImageView.buildDrawingCache();
                Bitmap bitmap=detailImageView.getDrawingCache();
                String path =  Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/DingDong/";
                FileUtil.saveBitmapToJpeg(bitmap, path);
                ELog.showToastShort(EyecatImageDetailActivity.this,"保存成功");
            }
        });
        detailImageView = (ImageView)findViewById(R.id.image_detail);
        ImageLoader.getInstance().displayImage(url,detailImageView );
    }
}
