package cc.wulian.smarthomev5.eyecat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.net.URI;
import java.net.URL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.adapter.WLBaseAdapter;
import cc.wulian.smarthomev5.eyecat.EyecatManager;
import cc.wulian.smarthomev5.eyecat.bean.RingRecondinfo;
import cc.wulian.smarthomev5.eyecat.bean.Warninfo;
import cc.wulian.smarthomev5.utils.DateUtil;

/**
 * Created by Administrator on 2017/5/9.
 */

public class WarnAdapter extends WLBaseAdapter<Warninfo>{
    private boolean isEdit = false;
    private Map<Integer,Boolean> willDeleteMap =  new HashMap<Integer,Boolean>();
    public WarnAdapter(Context context, List<Warninfo> data) {
        super(context, data);
    }

    @Override
    protected void bindView(Context context, View view, int pos,final Warninfo item) {
        final ImageView imageView = (ImageView)view.findViewById(R.id.iv_warnning);
        ImageView checkImageView = (ImageView)view.findViewById(R.id.iv_check);
        TextView timeTextView = (TextView)view.findViewById(R.id.tv_time);
        String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat fromat = new SimpleDateFormat(yyyyMMddHHmmss);
        String timeText = fromat.format(new Date(item.getTime()));
        timeTextView.setText(timeText);
        if(isEdit){
            checkImageView.setVisibility(View.VISIBLE);
            Boolean isChecked = willDeleteMap.get(pos);
            if(isChecked != null && isChecked == true) {
                checkImageView.setImageResource(R.drawable.device_led_adjust_select);
            }else{
                checkImageView.setImageResource(R.drawable.device_led_adjust_normal);
            }
        }else{
            checkImageView.setVisibility(View.GONE);
        }
        JSONArray pvids = item.getPvid();

        Log.d("zcz",pvids.optString(0));
        String pvid = pvids.optString(0);
        URL url = EyecatManager.getInstance().getICVSSUserInstance().equesGetAlarmfileUrl(pvid,item.getBid());
        ImageLoader.getInstance().displayImage(url.toString(),imageView );
    }

    @Override
    protected View newView(Context context, LayoutInflater inflater, ViewGroup parent, int pos) {
        View view = View.inflate(context, R.layout.item_warnning, null);
        return view;
    }

    public boolean isEdit() {
        return isEdit;
    }
    public void setEdit(boolean edit) {
        isEdit = edit;
    }
    public void toggerEdit(){
        if(isEdit){
            isEdit = false;
        }else{
            isEdit = true;
        }
        notifyDataSetChanged();
    }
    public int checkedSize(){
        return willDeleteMap.size();
    }
    public void toggleCheck(int position){
        Warninfo info = getItem(position);
        Boolean isChecked = willDeleteMap.get(position);
        if(isChecked != null && isChecked == true){
            willDeleteMap.remove(position);
        }else{
            willDeleteMap.put(position,true);
        }
        notifyDataSetChanged();
    }
    public void uncheckAll(){
        willDeleteMap.clear();
        notifyDataSetChanged();
    }
    public void checkAll(){
        for(int i = 0; i< getCount();i++){
            willDeleteMap.put(i,true);
        }
        notifyDataSetChanged();
    }

    public List<Warninfo> getCheckedData(){
        ArrayList list = new ArrayList<RingRecondinfo>();
        for(int position : willDeleteMap.keySet()){
            list.add(getItem(position));
        }
        return list;
    }
    public void removeCheckedData(){
        getData().removeAll(getCheckedData());
        willDeleteMap.clear();
        notifyDataSetChanged();
    }
}
