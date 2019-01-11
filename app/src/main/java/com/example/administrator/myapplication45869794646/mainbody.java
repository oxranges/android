package com.example.administrator.myapplication45869794646;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class mainbody extends AppCompatActivity {
            TextView TV1;
            EditText ET1;
            Button B1;
            String this_user,username,username0;
            ListView LV;
            ArrayList AL=new ArrayList();
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg){
            if(msg.what==200){
                MBA M=new MBA();
                LV.setAdapter(M);
            }
        }
    };
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        LV=findViewById(R.id.LV);
        TV1=findViewById(R.id.textView);
        ET1=findViewById(R.id.editText);
        B1=findViewById(R.id.button);
        Intent intent=getIntent();
        String JSON_str=intent.getStringExtra("str");
        try {
            JSONObject JO=new JSONObject(JSON_str);
            this_user=JO.getString("user");
            username=JO.getString("user2");
            username0=JO.getString("username");
            TV1.setText(username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    private HttpURLConnection conn;
                    public void  run(){
                        try {

                                final String str_url="http://123.207.85.214/chat/chat1.php";
                                URL url=new URL(str_url);
                                conn=(HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setConnectTimeout(5000);
                            String Str=ET1.getText().toString().trim();
//                                String Str="{\"status\":\"JOSN_false\",\"target\":\"\",\"this_chat\":\"\",\"now_time\":"+System.currentTimeMillis()+"}";
//                                if(ET1.getText().toString().trim().equals("")){}else{
//                                    Str = "{\"status\":\"JOSN_true\",\"target\":\"\",\"this_chat\":\""
//                                            + ET1.getText().toString().trim() +
//                                            "\",\"now_time\":"
//                                            + System.currentTimeMillis()
//                                            + "}";
//                                };
                                String data="user="+ URLEncoder.encode(this_user) + "&chat="+URLEncoder.encode(Str);
                                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                                conn.setRequestProperty("Content-Length",data.length()+"");
                                conn.setDoOutput(true);
                                OutputStream os=conn.getOutputStream();
                                os.write(data.getBytes());
                                int code= conn.getResponseCode();
                                String responseMessage = conn.getResponseMessage();
                                Log.e("123", "run: "+responseMessage );
                                //System.out.println(responseMessage+""+code);
                                if (code==200){
                                    String cookieval = conn.getHeaderField("set-cookie");
                                    String sessionid = null;
                                    if(cookieval != null) {
                                        sessionid = cookieval.substring(0, cookieval.indexOf(";"));
                                    }
                                    InputStream is =conn.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                                    String strRead="";
                                    StringBuffer sbf = new StringBuffer();
                                    while ((strRead = reader.readLine()) != null) {
                                        sbf.append(strRead);
                                        sbf.append("\r\n");
                                    }
                                    reader.close();
                                    //System.out.println(sbf.toString());
                                    //{"status":"\u767b\u9646\u6210\u529f","user":"76953a8e11ed9e01be47d5d84e6d241b","name":"123"}

                                    JSONArray JAY=new JSONArray(sbf.toString());
//                                    int now_time=0;
//                                    boolean ifgive=true;
                                    AL.clear();
                                    for (int i=JAY.length()-1;i>=0;i--){
                                        AL.add(JAY.optString(i));
                                        //System.out.println(i);
                                        //                                                        System.out.println(JAY.optString(i));
//                                        JSONObject JBJ0=new JSONObject(JAY.optString(i));

//                                        if(JBJ0.optString("chat").contains("JOSN_true")){
//                                            JSONObject JBJ=new JSONObject(JBJ0.optString("chat"));
//                                            if(JBJ.optString("status").equals("JOSN_true")){
//                                                int i_now_time=JBJ.optInt("now_time");
//                                                if( i_now_time-old_time<=0){
//
//                                                }else{
//                                                    if(now_time<JBJ.optInt("now_time")){
//
//                                                        now_time=JBJ.optInt("now_time");
//                                                        //                                                                       System.out.println("old_time:"+old_time);
//                                                        //                                                                       System.out.println("now_time:"+JBJ.optString("now_time"));
//                                                        //                                                                       System.out.println("now_time2:"+now_time);
//                                                    }
//                                                    //                                                                    System.out.println(old_time+" "+JBJ.optString("now_time"));
//                                                    System.out.println(JBJ.optString("this_chat"));
//                                                }
//
//
//                                            }else {
//                                                System.out.println("null");
//                                            }
//                                        }
                                        //                                                        else if(JBJ0.optString("chat").contains("JOSN_false")){
                                        ////                                                            System.out.println("false");
                                        //                                                        }else{
                                        ////                                                            System.out.println("other:"+JBJ0.optString("chat"));
                                        //                                                        }


                                    }
//                                    if(now_time!=0){old_time=now_time;}

                                    // System.out.println("old_time2:"+old_time);
                                    //                                                    String user=JBJ.optString("user");
                                }else {

                                }
                                conn.disconnect();
                            Message msg=new Message();
                            msg.what=code;
                            handler.sendMessage(msg);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }
    class MBA extends BaseAdapter {
        @Override
        public int getCount() {
            return AL.size();
        }
        @Override
        public Object getItem(int i) {
            return AL.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String id_v="";
            String str="";
            try {
                String AL_I= (String) AL.get(i);
                JSONObject JBJ=new JSONObject(AL_I);
                id_v=JBJ.getString("name").trim();
                str=JBJ.getString("chat").trim();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            View view0;
            if(id_v.equals(username0)){
                view0=View.inflate(mainbody.this,R.layout.activity_wechatme,null);
            }else{
                view0=View.inflate(mainbody.this,R.layout.activity_wechatyou,null);
            }


            TextView id=(TextView)view0.findViewById(R.id.item_wechat_msg_tv_sender_msg);
            id.setText(str);
//            TextView username=(TextView)view0.findViewById(R.id.item_tv_username);
//            username.setText(id_v);
//            AL.add(id_v);
            return view0;
        }
    }
}
