package cc.wulian.app.model.device.impls.controlable.fancoil.countdown;

import android.os.Bundle;
import cc.wulian.smarthomev5.activity.EventBusActivity;

public class FanCoilCountDownActivity extends EventBusActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		FanCoilCountDownFragment fragment = new FanCoilCountDownFragment();
		fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,fragment).commit();
	}
	@Override
	protected boolean finshSelf() {
		return false;
	}
	
	@Override
	public boolean fingerRightFromLeft() {
		return false;
	}
	
}
