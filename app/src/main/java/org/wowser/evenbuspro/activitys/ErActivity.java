package org.wowser.evenbuspro.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CaptureResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.R;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ErActivity extends BaseActiviy implements ZXingScannerView.ResultHandler{
    public static  int QR_SCANNER_TEST = 0001;


    private ZXingScannerView mScannerView;
    private FrameLayout framLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er);
        initView();
    }

    private void initView() {
        framLayout = (FrameLayout) findViewById(R.id.frameLayout_qrscanner);

        mScannerView = new ZXingScannerView(this);
        framLayout.addView(mScannerView,0);

        List<BarcodeFormat> barcodeFormatList = new ArrayList<BarcodeFormat>();
        barcodeFormatList.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(barcodeFormatList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
            if(!TextUtils.isEmpty(result.getText())){
                String handleResult = result.getText();
                Log.d("wanli","结果："+handleResult);
                Intent intent = new Intent();
                intent.putExtra("handleResult",handleResult);
                setResult(RESULT_OK,intent);
                finish();
            }
    }
}
