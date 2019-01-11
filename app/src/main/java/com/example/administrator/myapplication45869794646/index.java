package com.example.administrator.myapplication45869794646;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class index extends AppCompatActivity {
    static String obj_key,sessionid,user,username;
    static String[] AR_data;
    private ListView LV;
    private ArrayList AL = new ArrayList();
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg){
            if(msg.what==200){
                MBA M=new MBA();
                LV.setAdapter(M);

                LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(AL.get(position));
                        Intent intent=new Intent(index.this,mainbody.class);
                       String str="{\"sessionid\":\""+sessionid+"\",\"user\":\""+user+"\",\"user2\":\""+AL.get(position)+"\",\"username\":\"" + username + "\"}";
                        intent.putExtra("str",str);
                        startActivity(intent);
                    }
                });
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_index_activity);
        LV=(ListView) findViewById(R.id.lv);
        Intent I=getIntent();
        obj_key=I.getStringExtra("msg");
        System.out.println(obj_key);
        try {
            JSONObject obj=new JSONObject(obj_key);
            sessionid=obj.getString("sessionid");
            user=obj.getString("user");
            username=obj.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(){
            private HttpURLConnection conn;
            public void run(){
                try {
                    final String Str1="http://123.207.85.214/chat/member.php";
                    URL url=new URL(Str1);
                    conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    if(obj_key != null) {
                        conn.setRequestProperty("cookie", sessionid);
                    }
                    int code= conn.getResponseCode();
                    if(code==200){
                        InputStream is =conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String strRead="";
                        StringBuffer sbf = new StringBuffer();
                        while ((strRead = reader.readLine()) != null) {
                            sbf.append(strRead);
                            sbf.append("\r\n");
                        }
                        reader.close();
                        String data=sbf.toString();
                        data=data.replace("[","");
                        data=data.replace("]","");
                        data=data.replace("},{","}!A!{");
                        AR_data=data.split("!A!");
                        Message msg=new Message();
                        msg.what=code;
                        handler.sendMessage(msg);
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    class MBA extends BaseAdapter{
        @Override
        public int getCount() {
            return AR_data.length;
        }
        @Override
        public Object getItem(int i) {
            return AR_data[i];
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String id_v="";
            String username_v="";
            try {
                JSONObject JBJ=new JSONObject(AR_data[i]);
                id_v=JBJ.getString("name").trim();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(id_v.equals("")){
                id_v="null";
            }
            View view0=View.inflate(index.this,R.layout.users_index_activity_son,null);
            TextView id=(TextView)view0.findViewById(R.id.item_tv_id);
            id.setText(username_v);
            TextView username=(TextView)view0.findViewById(R.id.item_tv_username);
            username.setText(id_v);
            AL.add(id_v);
            return view0;
        }
    }
}
