package cc.wulian.smarthomev5.eyecat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.EyecatManager;
import cc.wulian.smarthomev5.eyecat.bean.RingRecondinfo;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RingRecondAdapter extends BaseAdapter{
    private Context context;
    private List<RingRecondinfo> data;

    public RingRecondAdapter(Context context, List<RingRecondinfo> data){
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
        RingRecondinfo ringRecondinfo = data.get(position);
        View view;
        if (convertView == null) {
            holder = new ViewHolder();
            view = View.inflate(context, R.layout.item_ringrecond, null);
            holder.iv_warnning = (ImageView) view.findViewById(R.id.iv_warnning);
            holder.iv_play = (ImageView) view.findViewById(R.id.iv_play);
            holder.ringrecond_status = (TextView) view.findViewById(R.id.ringrcond_status);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        URL url = EyecatManager.getInstance().getICVSSUserInstance().equesGetRingPicture(ringRecondinfo.getFid(),ringRecondinfo.getBid());

        return null;
    }

    private static class ViewHolder {
        private ImageView iv_warnning;
        private ImageView iv_play;
        private TextView ringrecond_status;
        private TextView tv_time;
    }
}
