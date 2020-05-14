/**
 * ��򵥵Ļ���FFmpeg����Ƶ������-��׿
 * Simplest FFmpeg Android Decoder
 * 
 * ������ Lei Xiaohua
 * leixiaohua1020@126.com
 * �й���ý��ѧ/���ֵ��Ӽ���
 * Communication University of China / Digital TV Technology
 * http://blog.csdn.net/leixiaohua1020
 * 
 * �������ǰ�׿ƽ̨����򵥵Ļ���FFmpeg����Ƶ�������������Խ��������Ƶ���ݽ����YUV�������ݡ�
 * 
 * This software is the simplest decoder based on FFmpeg in Android. It can decode video stream
 * to raw YUV data.
 * 
 */
package com.leixiaohua1020.sffmpegandroiddecoder;


import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();


		Button startButton = (Button) this.findViewById(R.id.button_start);
		final EditText urlEdittext_input= (EditText) this.findViewById(R.id.input_url);
		final EditText urlEdittext_output= (EditText) this.findViewById(R.id.output_url);
		
		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0){

				String folderurl=Environment.getExternalStorageDirectory().getPath();
				
				String urltext_input=urlEdittext_input.getText().toString();
		        String inputurl=folderurl+"/"+urltext_input;
		        
		        String urltext_output=urlEdittext_output.getText().toString();
		        String outputurl=folderurl+"/"+urltext_output;
		        
		        Log.i("inputurl",inputurl);
		        Log.i("outputurl",outputurl);
		    
		        decode(inputurl,outputurl);
		        
			}
		});
    }

	private void getPermission() {
		RxPermissions rxPermissions = new RxPermissions(this);

		rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO)
				.subscribe(permission ->  {
					if (permission.granted) {

					} else if (permission.shouldShowRequestPermissionRationale) {
						// Denied permission without ask never again
					} else {
						// Desettingsnied permission with ask never again
						// Need to go to the
					}
				});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //JNI
    public native int decode(String inputurl, String outputurl);
    
    static{
    	System.loadLibrary("avutil-54");
    	System.loadLibrary("swresample-1");
    	System.loadLibrary("avcodec-56");
    	System.loadLibrary("avformat-56");
    	System.loadLibrary("swscale-3");
    	System.loadLibrary("postproc-53");
    	System.loadLibrary("avfilter-5");
    	System.loadLibrary("avdevice-56");
    	System.loadLibrary("sffdecoder");
    }
}
