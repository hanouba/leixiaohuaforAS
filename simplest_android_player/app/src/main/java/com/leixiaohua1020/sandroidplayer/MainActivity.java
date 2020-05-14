/**
 * 
 * 最简单的安卓视频播放器
 * Simplest Android Player
 * 
 * 雷霄骅 Lei Xiaohua
 * leixiaohua1020@126.com
 * 中国传媒大学/数字电视技术
 * Communication University of China / Digital TV Technology
 * http://blog.csdn.net/leixiaohua1020
 * 
 * 本程序是基于安卓接口的视频播放器。
 * 
 * Video player based on Android API.
 * 
 */
package com.leixiaohua1020.sandroidplayer;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
		rxPermissions
				.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.subscribe(permission -> { // will emit 2 Permission objects
					if (permission.granted) {
						// `permission.name`

					} else if (permission.shouldShowRequestPermissionRationale) {
						// Denied permission without ask never again
					} else {
						// Denied permission with ask never again
						// Need to go to the settings
					}
				});
		final VideoView videoView = (VideoView) findViewById(R.id.videoView1);
		
		Button setButton = (Button) this.findViewById(R.id.button_set);
		Button startButton = (Button) this.findViewById(R.id.button_start);
		Button pauseButton = (Button) this.findViewById(R.id.button_pause);
		Button stopButton = (Button) this.findViewById(R.id.button_stop);
		final EditText urlEdittext= (EditText) this.findViewById(R.id.input_url);
		
		
        setButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				String folderurl=Environment.getExternalStorageDirectory().getPath();
				String urltext=urlEdittext.getText().toString();
				//Small FIX, Avoid '/'
				if(urltext.charAt(0)=='/'){
					urltext=urltext.substring(1);
				}
		        String inputurl=folderurl+"/"+urltext;
		        Log.e("url",inputurl);
		       
				videoView.setVideoPath(inputurl);
				//videoView.setMediaController(new MediaController(MainActivity.this));
			}
		});
        
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				videoView.start();
			}
		});
		
		pauseButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
				videoView.pause();
			}
		});
		
		stopButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){
			    //停止后 ,点击start 无法播放 ,调用 set后可以直接播放
                //因为调用停止时 mCurrentState == STATE_IDLE  而调用播放时 mCurrentState != STATE_IDLE
				videoView.stopPlayback();
			}
		});
        
    }


	@Override
	protected void onStart() {
		super.onStart();


	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
