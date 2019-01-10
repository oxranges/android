package com.example.administrator.myapplication45869794646;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editText1;
    Button button;
    TextView TV;
    String this_user;
    Typeface typeFace;
    int old_time=0;
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
//        super.handleMessage(msg);
        String msg0=(String) msg.obj;
        Intent intent=new Intent(MainActivity.this,index.class);
        intent.putExtra("msg",msg0);
        startActivity(intent);

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //      typeFace =Typeface.createFromAsset(getAssets(),"font/a.ttf");
        editText =(EditText)findViewById(R.id.et_number);
        editText1 =(EditText)findViewById(R.id.et_password);
        button=(Button) findViewById(R.id.btn_login);
        TV=(TextView) findViewById(R.id.add_user);
        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,add.class);

                startActivity(intent);
            }});
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(){
                    private HttpURLConnection conn;
                    public void  run(){
                        try {
                            final String str_url="http://123.207.85.214/chat/login.php";
                            URL url=new URL(str_url);
                            conn=(HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setConnectTimeout(5000);
                            String data="user="+ URLEncoder.encode(editText.getText().toString()) + "&password="+URLEncoder.encode(editText1.getText().toString());
                            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                            conn.setRequestProperty("Content-Length",data.length()+"");
                            conn.setDoOutput(true);
                            OutputStream os=conn.getOutputStream();
                            os.write(data.getBytes());
                            int code= conn.getResponseCode();
                            String responseMessage = conn.getResponseMessage();
                            Log.e("123", "run: "+responseMessage );
                            System.out.println(code+"");
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
                                System.out.println(sbf.toString());
                                //{"status":"\u767b\u9646\u6210\u529f","user":"76953a8e11ed9e01be47d5d84e6d241b","name":"123"}
                                JSONObject JBJ=new JSONObject(sbf.toString());
                                this_user=JBJ.optString("user");
//                                if(this_user!=""){
//                                    //.................................................
//                                    new Thread(){
//                                        private HttpURLConnection conn;
//                                        public void  run(){
//                                            try {
//                                                final String str_url="http://123.207.85.214/chat/chat1.php";
//                                                URL url=new URL(str_url);
//                                                conn=(HttpURLConnection) url.openConnection();
//                                                conn.setRequestMethod("POST");
//                                                conn.setConnectTimeout(5000);
//                                                EditText TV=findViewById(R.id.editText);
//                                                String Str="{\"status\":\"JOSN_false\",\"target\":\"\",\"this_chat\":\"\",\"now_time\":"+System.currentTimeMillis()+"}";
//                                                if(TV.getText().toString().trim().equals("")){}else{
//                                                    Str = "{\"status\":\"JOSN_true\",\"target\":\"\",\"this_chat\":\""
//                                                            + TV.getText().toString().trim() +
//                                                            "\",\"now_time\":"
//                                                            + System.currentTimeMillis()
//                                                            + "}";
//                                                };
//                                                String data="user="+ URLEncoder.encode(this_user) + "&chat="+URLEncoder.encode(Str);
//                                                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//                                                conn.setRequestProperty("Content-Length",data.length()+"");
//                                                conn.setDoOutput(true);
//                                                OutputStream os=conn.getOutputStream();
//                                                os.write(data.getBytes());
//                                                int code= conn.getResponseCode();
//                                                String responseMessage = conn.getResponseMessage();
//                                                Log.e("123", "run: "+responseMessage );
//                                                //System.out.println(responseMessage+""+code);
//                                                if (code==200){
//                                                    String cookieval = conn.getHeaderField("set-cookie");
//                                                    String sessionid = null;
//                                                    if(cookieval != null) {
//                                                        sessionid = cookieval.substring(0, cookieval.indexOf(";"));
//                                                    }
//                                                    InputStream is =conn.getInputStream();
//                                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                                                    String strRead="";
//                                                    StringBuffer sbf = new StringBuffer();
//                                                    while ((strRead = reader.readLine()) != null) {
//                                                        sbf.append(strRead);
//                                                        sbf.append("\r\n");
//                                                    }
//                                                    reader.close();
//                                                    //System.out.println(sbf.toString());
//                                                    //{"status":"\u767b\u9646\u6210\u529f","user":"76953a8e11ed9e01be47d5d84e6d241b","name":"123"}
//
//                                                    JSONArray JAY=new JSONArray(sbf.toString());
//                                                    int now_time=0;
//                                                    boolean ifgive=true;
//                                                    for (int i=JAY.length()-1;i>=0;i--){
//                                                        //System.out.println(i);
//                                                        //                                                        System.out.println(JAY.optString(i));
//                                                        JSONObject JBJ0=new JSONObject(JAY.optString(i));
//
//                                                        if(JBJ0.optString("chat").contains("JOSN_true")){
//                                                            JSONObject JBJ=new JSONObject(JBJ0.optString("chat"));
//                                                            if(JBJ.optString("status").equals("JOSN_true")){
//                                                                int i_now_time=JBJ.optInt("now_time");
//                                                                if( i_now_time-old_time<=0){
//
//                                                                }else{
//                                                                    if(now_time<JBJ.optInt("now_time")){
//
//                                                                        now_time=JBJ.optInt("now_time");
//                                                                        //                                                                       System.out.println("old_time:"+old_time);
//                                                                        //                                                                       System.out.println("now_time:"+JBJ.optString("now_time"));
//                                                                        //                                                                       System.out.println("now_time2:"+now_time);
//                                                                    }
//                                                                    //                                                                    System.out.println(old_time+" "+JBJ.optString("now_time"));
//                                                                    System.out.println(JBJ.optString("this_chat"));
//                                                                }
//
//
//                                                            }else {
//                                                                System.out.println("null");
//                                                            }
//                                                        }
//                                                        //                                                        else if(JBJ0.optString("chat").contains("JOSN_false")){
//                                                        ////                                                            System.out.println("false");
//                                                        //                                                        }else{
//                                                        ////                                                            System.out.println("other:"+JBJ0.optString("chat"));
//                                                        //                                                        }
//
//
//                                                    }
//                                                    if(now_time!=0){old_time=now_time;}
//
//                                                    // System.out.println("old_time2:"+old_time);
//                                                    //                                                    String user=JBJ.optString("user");
//                                                }else {
//
//                                                }
//                                                conn.disconnect();
//
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }.start();
//                                    //.................................................
//                                }
                                String status=JBJ.optString("status");

                                                                if(status.equals("登陆成功")){
                                                                    System.out.println(status);
                                                                    String user=JBJ.optString("user");
                                                                    Message msg=new Message();
                                                                    msg.what=code;
                                                                    msg.obj="{\"sessionid\":\""+sessionid+"\",\"user\":\""+user+"\",\"username\":\""+editText.getText().toString()+"\"}";
                                                                    handler.sendMessage(msg);
                                                                }

                            }else {

                            }
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        //        editText1.setTypeface(typeFace);
        //        editText.setTypeface(typeFace);
        //        button.setTypeface(typeFace);


    }
}
