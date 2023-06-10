package com.example.smarttransportation.Utility;

import java.util.Objects;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class H {
             private static String URL="http://10.212.52.246:8080/traffic/";
             public void sendResutil(String uil, String msg, String method, Callback callback){
                 OkHttpClient client=new OkHttpClient();
                 RequestBody body=null;
                 if(Objects.equals(method,"POST")){
                    body=RequestBody.create(MediaType.parse("application/json"),msg);
                 }
                 Request request=new Request.Builder()
                         .method(method.toUpperCase(),body)
                         .addHeader("content-type","application/json")
                         .url(URL+uil)
                         .build();
                 client.newCall(request).enqueue(callback);

             }
}
