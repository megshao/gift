package com.make.shao.gift;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * Created by ChengYuShao on 15/2/16.
 */
public class gift_candle extends Activity{
    int count;
    boolean flag_candle;
    TextView tv;
    Button btn1,btn2;
    private MediaRecorder mRecorder = null;
    View LL_candle;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            double ringerVol = mRecorder.getMaxAmplitude();
            //TextView tv = (TextView) findViewById(R.id.textView4);
            //tv.setText(Double.toString(ringerVol));
            //mHandler.sendMessageDelayed(Message.obtain(),300);
            if (ringerVol > 30000){
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                //gift_candle.this.finish();
                Message msgt = new Message();
                msgt.what = 2;
                mHandler.sendMessage(msgt);
            }
            else
                handler.sendMessageDelayed(Message.obtain(),300);
        }
    };

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    count++;
                    if(count % 2 == 0){
                        LL_candle.setBackgroundResource(R.drawable.candle);
                    }
                    else if(count % 2 == 1){
                        LL_candle.setBackgroundResource(R.drawable.candle4);
                    }
                    break;
                case 2:
                    flag_candle = false;
                    LL_candle.setBackgroundResource(R.drawable.candle3);
                    tv.setText("祝妳心想事成:P");
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    break;
            }
            //super.handleMessage(msg);
        }
    };
    Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_gift_candle);
        count = 0;
        flag_candle = true;
        LL_candle = (RelativeLayout) this.findViewById(R.id.LL_candle);
        LL_candle.setBackgroundResource(R.drawable.candle);
        tv = (TextView) this.findViewById(R.id.textView4);
        btn1 = (Button) this.findViewById(R.id.button2);
        btn2 = (Button) this.findViewById(R.id.button3);
        if(mRecorder == null){
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
            }catch (IllegalStateException Ie){
                Ie.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            mRecorder.start();
        }
        handler.sendEmptyMessageDelayed(0,1000);

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag_candle){
                    try {
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("QAQ哪泥！！！！");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gift_candle.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if(mRecorder != null){
            //mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }*/
    }
}
