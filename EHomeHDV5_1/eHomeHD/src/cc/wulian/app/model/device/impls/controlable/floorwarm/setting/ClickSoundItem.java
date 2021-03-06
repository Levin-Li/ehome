package cc.wulian.app.model.device.impls.controlable.floorwarm.setting;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import cc.wulian.app.model.device.impls.controlable.floorwarm.FloorWarmUtil;
import cc.wulian.ihome.wan.util.StringUtil;
import cc.wulian.smarthomev5.R;
import cc.wulian.smarthomev5.fragment.setting.AbstractSettingItem;
import cc.wulian.smarthomev5.tools.SendMessage;

public class ClickSoundItem extends AbstractSettingItem {
	
	private String mGwId;
	private String mDevId;
	private String mEp;
	private String mEpType;
	private boolean isSoundOpen = true;
	
	private static final int DRAWABLE_SOUND_ON = R.drawable.thermost_setting_icon_on;
	private static final int DRAWABLE_SOUND_OFF = R.drawable.thermost_setting_icon_off;

	public ClickSoundItem(Context context) {
		super(context, R.drawable.icon_gateway_id, context.getResources().getString(R.string.AP_key_sound));
	}

	@Override
	public void initSystemState() {
		super.initSystemState();
		setCilckSound();
	}
	
	public void setSoundData(String gwId,String devId,String ep,String epType){
		mGwId = gwId;
		mDevId = devId;
		mEp = ep;
		mEpType = epType;
	}
	
	//设置 开关状态
	public void setSoundType(String clickSoundData){
		if(StringUtil.equals(clickSoundData, FloorWarmUtil.STATE_ON)){
			isSoundOpen = true;
		}else if(StringUtil.equals(clickSoundData, FloorWarmUtil.STATE_OFF)){
			isSoundOpen = false;
		}
		setClickSoundImage(isSoundOpen);
	}

	private void setClickSoundImage(boolean isOpen){
		if(isOpen){
			infoImageView.setBackgroundResource(DRAWABLE_SOUND_ON);
		}else{
			infoImageView.setBackgroundResource(DRAWABLE_SOUND_OFF);
		}
	}
	
	public void setCilckSound() {
		nameTextView.setTextColor(Color.parseColor("#3e3e3e"));
		iconImageView.setVisibility(View.GONE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, 0);
		nameTextView.setLayoutParams(params);
		infoImageView.setVisibility(View.VISIBLE);
		infoImageView.setLayoutParams(params);
		infoImageView.setImageDrawable(null);
		setClickSoundImage(isSoundOpen);
		
		infoImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isSoundOpen){
					SendMessage.sendControlDevMsg(mGwId, mDevId, mEp, mEpType, FloorWarmUtil.SOUND_CMD_OFF);
				}else{
					SendMessage.sendControlDevMsg(mGwId, mDevId, mEp, mEpType, FloorWarmUtil.SOUND_CMD_ON);
				}
				
			}
		});
	}

	@Override
	public void doSomethingAboutSystem() {
		
	}
}
