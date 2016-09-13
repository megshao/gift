package com.make.shao.gift;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ChengYuShao on 15/2/16.
 */
public class gift_wish extends ActionBarActivity {
    Button btn_wish;
    EditText editText1,editText2;
    Thread mThread;
    HttpClient client;
    JSONObject jObj;
    SharedPreferences pref;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Intent i = new Intent();
                    i.setClass(gift_wish.this, gift_candle.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_gift_wish);

        btn_wish = (Button) this.findViewById(R.id.button);
        editText1 = (EditText) this.findViewById(R.id.editText);
        editText2 = (EditText) this.findViewById(R.id.editText2);


        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String wish1 = editText1.getText().toString();
                    String wish2 = editText2.getText().toString();
                    client = new DefaultHttpClient();
                    String url = "http://192.241.211.157:5000/wish";
                    HttpPost post = new HttpPost(url);
                    JSONObject postData = new JSONObject();
                    postData.put("1", wish1);
                    postData.put("2", wish2);
                    post.setEntity(new StringEntity(postData.toString()));
                    post.setHeader("Content-type", "application/json");
                    HttpResponse rp = client.execute(post);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.handleMessage(msg);
                    /*if(rp.getStatusLine().getStatusCode() == 200){
                        String result = EntityUtils.toString(rp.getEntity());
                        jObj = new JSONObject(result);
                        Log.d("loginresult", "stat:"+jObj.getString("stat"));
                        gv.setUserID(jObj.getInt("userID"));
                        Log.d("loginresult", "userID:"+jObj.getInt("userID"));
                        pref.edit().putBoolean(IS_LOGIN, true).commit();
                        pref.edit().putString(KEY_ACCOUNT, str_user).commit();
                        pref.edit().putString(KEY_PASSWD, str_passwd).commit();
                        pref.edit().putString(KEY_LOGINTYPE, KEY_ACCOUNT);
                        Log.d("session", pref.getString(KEY_ACCOUNT, null));
                        Message msg_t = new Message();
                        msg_t.what = 1;
                        mHandler.handleMessage(msg_t);
                    }
                    else{
                        Log.d("result", "Not 200");
                    }*/
                } catch (ClientProtocolException e) {
                    Log.d("cPE", e.getMessage().toString());
                } catch (IOException e) {
                    Log.d("IOE", e.getMessage().toString());
                } catch (Exception e){
                    Log.d("E", e.getMessage().toString());
                }
                finally{
                    client.getConnectionManager().shutdown();
                }
            }
        });
        btn_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("1", editText1.getText().toString());
                Log.d("2", editText2.getText().toString());
                mThread.start();

            }
        });
    }
}
