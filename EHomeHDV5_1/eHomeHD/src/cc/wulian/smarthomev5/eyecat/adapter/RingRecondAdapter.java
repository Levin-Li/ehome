package cc.wulian.smarthomev5.eyecat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URL;
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

/**
 * Created by Administrator on 2017/5/9.
 */

public class RingRecondAdapter extends WLBaseAdapter<RingRecondinfo>{
    private boolean isEdit = false;
    private Map<Integer,Boolean> willDeleteMap =  new HashMap<Integer,Boolean>();
    public RingRecondAdapter(Context context, List<RingRecondinfo> data) {
        super(context, data);
    }

    @Override
    protected void bindView(final Context context, View view, int pos, final RingRecondinfo item) {
        final ImageView imageView = (ImageView)view.findViewById(R.id.iv_warnning);
        ImageView checkImageView = (ImageView)view.findViewById(R.id.iv_check);
        TextView timeTextView = (TextView)view.findViewById(R.id.tv_time);
        String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat fromat = new SimpleDateFormat(yyyyMMddHHmmss);
        String timeText = fromat.format(new Date(item.getRingtime()));
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
        URL url = EyecatManager.getInstance().getICVSSUserInstance().equesGetRingPicture(item.getFid(),item.getBid());
        ImageLoader.getInstance().displayImage(url.toString(),imageView );

    }

    @Override
    protected View newView(Context context, LayoutInflater inflater, ViewGroup parent, int pos) {
        View view = View.inflate(context, R.layout.item_ringrecond, null);
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
        RingRecondinfo info = getItem(position);
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

    public List<RingRecondinfo> getCheckedData(){
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
