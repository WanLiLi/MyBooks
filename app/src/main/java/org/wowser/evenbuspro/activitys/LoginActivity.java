package org.wowser.evenbuspro.activitys;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.MyLocationData;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.preferencesUtil.pp.Loginpreferences;
import org.wowser.evenbuspro.utils.MLog;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.ContactsPage;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout linearLayout;
    private ImageView imageView;
    private Button btnWeixin, btnSMS, btn_SMS_cantan,main;

    private UMShareAPI mShareAPI = null;

    // 填写从短信SDK应用后台注册得到的APPKEY
    //此APPKEY仅供测试使用，且不定期失效，请到mob.com后台申请正式APPKEY
    private static String APPKEY = "12ea0cb763690";   //"f3fc6baa9ac4";

    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "42f096cbabc78dc15230c99bd89d867a";   //"7f3dedcb36d92deebcb373af921d635a";


    private boolean ready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /** umeng init auth api**/
        mShareAPI = UMShareAPI.get(this);

        /** SMS**/
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        ready = true;


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        imageView = (ImageView) findViewById(R.id.image_login);
        btnWeixin = (Button) findViewById(R.id.btn_weixin);


        initLoginEvent();

        ShowGuiPage();


        btnSMS = (Button) findViewById(R.id.btn_SMS);
        btnSMS.setOnClickListener(this);

        btn_SMS_cantan = (Button) findViewById(R.id.btn_SMS_cantan);
        btn_SMS_cantan.setOnClickListener(this);


        main = (Button) findViewById(R.id.main);
        main.setOnClickListener(this);

        Button btn = (Button) findViewById(R.id.btn_Login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runPost();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SHARE_MEDIA platform = null;





    public void initLoginEvent() {
        btnWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shareToFriend(new File(getFilesDir().getAbsolutePath()));
                platform = SHARE_MEDIA.WEIXIN;
                //platform = SHARE_MEDIA.QQ;
            }
        });
        /**begin invoke umeng api**/
        mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("auth", "on activity re 3");
    }

    /**
     * 分享图片给好友
     *
     * @param file
     */
    private void shareToFriend(File file) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(intent);
    }

    /**
     * 分享多图到朋友圈，多张图片加文字
     *
     * @param uris
     */
    private void shareToTimeLine(String title, ArrayList<Uri> uris) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");

        intent.putExtra("Kdescription", title);

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        startActivity(intent);
    }


    public void runPost() throws IOException {
        OkHttpClient client = new OkHttpClient();
        //post
        FormBody body = new FormBody.Builder()
                .add("empid", "A0000000000000000000000000000000")
                .add("password", "7C54A435DEC7F01B58451B1ECAC954EC")
                .build();

        Request requestPosst = new Request.Builder().url("http://115.28.224.106:8080/eke/Emp/employee/login.do").post(body)
                .build();  //.addHeader("Content-Type","application/json")

        client.newCall(requestPosst).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new Loginpreferences(LoginActivity.this).setLoginCacheCishu(1);
                String data = response.body().string();
                Log.d("wanli", data);
                Intent intent = new Intent(LoginActivity.this, ShowDataActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
    }

    void ShowGuiPage() {
        if (new Loginpreferences(this).getLoginCacheCishu() == 1) {


//            linearLayout.setVisibility(View.GONE);
//            imageView.setVisibility(View.VISIBLE);
//            imageView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(LoginActivity.this,ShowDataActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            },6000);
//            Intent intent = new Intent(LoginActivity.this, ShowDataActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_SMS:
                MLog.d("ssssss", "duanxin");
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void onRegister() {
                    }
                    public void beforeEvent(int var1, Object var2) {
                        MLog.d("wanli_sms", "var1:"+var1+" var2:"+var2.toString());
                    }
                    public void afterEvent(int event, int result, Object data) {
                        MLog.d("wanli_sms", "data:"+data.toString());
// 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            MLog.d("wanli_sms", "data:"+data.toString());
                            MLog.d("wanli_sms", "country:" + country + "  phone:" + phone + "");
// 提交用户信息
                            registerUser(country, phone);
                        }
                    }

                    public void onUnregister() {
                    }
                });
                registerPage.show(this);
                break;

            case R.id.btn_SMS_cantan:
                //打开通信录好友列表页面
                ContactsPage contactsPage = new ContactsPage();
                contactsPage.show(this);
                break;

            case R.id.main:
                Intent intent = new Intent();
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };

    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    @Override
    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
//            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }
}
