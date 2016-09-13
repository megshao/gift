package com.make.shao.gift;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.os.Handler;
import android.widget.LinearLayout;

/**
 * Created by ChengYuShao on 15/2/14.
 */
public class gift_open extends ActionBarActivity {
    View LL;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    count++;
                        if(count == 2){
                            LL.setBackgroundResource(R.drawable.cake2);
                        }
                        else if(count == 3){
                            LL.setBackgroundResource(R.drawable.cake3);
                        }
                        else if(count == 4){
                            LL.setBackgroundResource(R.drawable.cake4);
                        }
                        else if(count == 5){
                            LL.setBackgroundResource(R.drawable.cake5);
                        }
                        else if(count == 6){
                            LL.setBackgroundResource(R.drawable.cake6);
                        }
                        else if(count == 7){
                            LL.setBackgroundResource(R.drawable.cake7);
                        }
                        else if(count == 8){
                            mThread.interrupt();
                            mThread = null;
                            Intent i = new Intent();
                            i.setClass(gift_open.this,gift_wish.class);
                            startActivity(i);
                            finish();
                        }
                    break;
            }
            //super.handleMessage(msg);
        }
    };
    Thread mThread;
    int count;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_gift_open);
        res = this.getResources();
        LL = (LinearLayout) this.findViewById(R.id.open_LL);
        LL.setBackgroundResource(R.drawable.cake1);
        count = 1;

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        if(count > 8)
                            break;
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }
}
