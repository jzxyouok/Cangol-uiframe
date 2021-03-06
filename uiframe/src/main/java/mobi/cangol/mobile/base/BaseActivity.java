/** 
 * Copyright (c) 2013 Cangol
 * 
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mobi.cangol.mobile.base;

import mobi.cangol.mobile.CoreApplication;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.AppService;
import mobi.cangol.mobile.service.session.SessionService;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Cangol
 */
public  abstract class BaseActivity extends Activity implements BaseActivityDelegate{
	protected String TAG = Utils.makeLogTag(BaseActivity.class);
	private static final boolean LIFECYCLE=Utils.LIFECYCLE;
	public CoreApplication app;
	private long startTime;

	public float getIdletime(){
		 return (System.currentTimeMillis()-startTime)/1000.0f;
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		TAG = Utils.makeLogTag(this.getClass());
		app = (CoreApplication) this.getApplication();
		app.addActivityToManager(this);
	}

	public  void showToast(int resId){
		Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
	}
	public  void showToast(String str){
		Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
	}
	public  void showToast(int resId,int duration){
		Toast.makeText(this,resId,duration).show();
	}
	public  void showToast(String str,int duration){
		Toast.makeText(this,str,duration).show();
	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if(LIFECYCLE)Log.v(TAG, "onNewIntent");
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(LIFECYCLE)Log.v(TAG, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(LIFECYCLE)Log.v(TAG, "onResume "+getIdletime()+"s");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(LIFECYCLE)Log.v(TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(LIFECYCLE)Log.v(TAG, "onRestart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(LIFECYCLE)Log.v(TAG, "onStop");
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(LIFECYCLE)Log.v(TAG, "onRestoreInstanceState");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(LIFECYCLE)Log.v(TAG, "onSaveInstanceState");
	}

	public SessionService getSession() {
		return app.getSession();
	}

	public AppService getAppService(String name) {
		return app.getAppService(name);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(LIFECYCLE)Log.v(TAG, "onConfigurationChanged");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(LIFECYCLE)Log.v(TAG, "onDestroy");
		app.delActivityFromManager(this);
	}

	@Override
	public void onBackPressed() {
		if(LIFECYCLE)Log.v(TAG, "onBackPressed");
		super.onBackPressed();
	}

	@Override
	public void setFullScreen(boolean fullscreen) {
		if(fullscreen){
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}else{
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

    /**
     * 处理back事件
     */
	@Override
	public void onBack() {
		super.onBackPressed();
	}
}
