package cc.wulian.smarthomev5.eyecat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;

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

        return view;
    }

    private static class ViewHolder {
        private ImageView iv_warnning;
        private ImageView iv_play;
        private TextView tv_warning;
        private TextView tv_time;
    }
    private String getTime(Long time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String d = format.format(time);
        Date date = null;
        try {
            date=format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }
}
