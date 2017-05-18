package cc.wulian.smarthomev5.eyecat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.utils.L;
import com.squareup.picasso.Picasso;
import com.wulian.icam.utils.ImageLoader;

import org.json.JSONArray;

import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.EyecatManager;
import cc.wulian.smarthomev5.eyecat.bean.RingRecondinfo;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;
import cc.wulian.smarthomev5.utils.DateUtil;

/**
 * Created by Administrator on 2017/5/9.
 */

public class WarnAdapter extends BaseAdapter{
    private Context context;
    private List<Warninfo> data;

    public WarnAdapter(Context context, List<Warninfo> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Warninfo warninfo = data.get(position);
        View view;
        if (convertView == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_warnning, null);
            holder.iv_warnning = (ImageView) view.findViewById(R.id.iv_warnning);
            holder.iv_play = (ImageView) view.findViewById(R.id.iv_play);
            holder.tv_warning = (TextView) view.findViewById(R.id.tv_warning);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        JSONArray pvids = warninfo.getPvid();

        Log.d("zcz",pvids.optString(0));
        String pvid = pvids.optString(0);
        URL url= EyecatManager.getInstance().getICVSSUserInstance().equesGetThumbUrl(pvid,warninfo.getBid());
        String picpath = url.toString();
        Log.d("zcz",picpath);
        ImageLoader imageLoader = new ImageLoader(context);
        Bitmap bitmap= imageLoader.loadImage(picpath, new ImageLoader.OnImageLoaderListener() {
            @Override
            public void onImageLoader(Bitmap bitmap, String url) {

            }
        },150,75);
        holder.iv_warnning.setImageBitmap(bitmap);
        holder.tv_time.setText(DateUtil.getFormatIMGTime(warninfo.getTime()));
        int type = warninfo.getType();
        if(type == 5){
            holder.iv_play.setVisibility(View.VISIBLE);
        }else{
            holder.iv_play.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private static class ViewHolder {
        private ImageView iv_warnning;
        private ImageView iv_play;
        private TextView tv_warning;
        private TextView tv_time;
    }

}
