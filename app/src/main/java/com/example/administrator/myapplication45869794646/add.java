package com.example.administrator.myapplication45869794646;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class add  extends AppCompatActivity {
    EditText E1,E2,E3;
    Button B1;
    Thread thread =new Thread(){
        public void  run(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent=new Intent(add.this,MainActivity.class);
            startActivity(intent);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        E1=findViewById(R.id.add_name);
        E2=findViewById(R.id.add_password);
        E3=findViewById(R.id.add_user);
        B1=findViewById(R.id.btn_ok);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    private HttpURLConnection conn;
                    public void  run(){
                        try {
                            final String str_url="http://123.207.85.214/chat/register.php";
                            URL url=new URL(str_url);
                            conn=(HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setConnectTimeout(5000);
                            String data="user="+ URLEncoder.encode(E3.getText().toString()) + "&password="+URLEncoder.encode(E2.getText().toString())+ "&name="+URLEncoder.encode(E1.getText().toString());
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
                                JSONObject JBJ=new JSONObject(sbf.toString());
                                String status=JBJ.optString("status");
                                if(status.equals("注册成功")){
                                    thread.start();
                                    Looper.prepare();
                                    Toast.makeText(add.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }else if(status.equals("用户名重复")){
                                    Looper.prepare();
                                    Toast.makeText(add.this,"用户名重复",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }else { }
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
}
