package com.example.ddschedule.network;

import android.content.Context;

import java.util.Comparator;
import java.util.List;

import static com.example.ddschedule.network.BiliRequest.bili_urls;

public class MainRequest implements YTBRequest.NetDataCallback, BiliRequest.NetDataCallback {

    Boolean YTB_OK = false;
    Boolean Bili_OK = false;
    int YTB_code;
    int Bili_code;
    String YTB_Str;
    String Bili_Str;

    public MainRequest(List<String> groups, Context context, final MainRequest.NetDataCallback netDataCallback) {
        YTBRequest req_ytb = new YTBRequest(groups, context);
        req_ytb.postData(this);
        BiliRequest req_bili = new BiliRequest(context);
        bili_urls.forEach((k,v)->{
            req_bili.getData(k, v, this);
            netDataCallback.NetCallback();
        });
    }

    public interface NetDataCallback {
        void NetCallback();
        void NetErr(int code1,String s1, int code2,String s2);
    }

    @Override
    public void BiliCallback() {
        Bili_OK = true;
    }

    @Override
    public void BiliErr(int code, String s) {
        Bili_code = code;
        Bili_Str = s;
    }

    @Override
    public void YTBCallback() {
        YTB_OK = true;
    }

    @Override
    public void YTBErr(int code, String s) {
        YTB_code = code;
        YTB_Str = s;
    }
}