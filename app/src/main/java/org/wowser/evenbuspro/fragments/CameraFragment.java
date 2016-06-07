package org.wowser.evenbuspro.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.utils.MLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {
    public static String TAG = "CameraFragment";
    private static final int UI_SYSTEM_CAMERA_BACK = 100;
    private File photoFile;

    private ImageView imageView;
    private Button btnPic;

    private Bitmap bitmapPic;
    final int REQUESTCODE_CAMERA = 1;

    public static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_pic);
        imageView.setBackgroundColor(Color.RED);


        btnPic = (Button) view.findViewById(R.id.btn_pic);

        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hasCamera = ContextCompat.checkSelfPermission(getActivity(), CAMERA_PERMISSION);  //兼容所有api
                if (hasCamera != PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA); //兼容所有api
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA);


//                    //不能没有权限还打开相机，请求时异步的
//                    Intent intentCamera = new Intent();
//                    intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                    startActivityForResult(intentCamera, UI_SYSTEM_CAMERA_BACK);
                } else {
                    invokeCamera();
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUESTCODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    invokeCamera();
                }else{
                    Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT)
                            .show();
                    if(shouldShowRequestPermissionRationale(CAMERA_PERMISSION)){
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private String pathAbsolute = Environment.getExternalStorageDirectory().getAbsolutePath();  // /storage/emulated/0   ====/storage/sdcard0


    private String picName = "7777777777777";

    void invokeCamera() {

        MLog.d("pathAbsolute", "是否有SD卡:" + Environment.getExternalStorageDirectory().exists());
        MLog.d("pathAbsolute", "pathAbsolute:" + pathAbsolute);

        String thisPath = getActivity().getFilesDir().getAbsolutePath();   ///data/data/org.wowser.evenbuspro/files
        MLog.d("pathAbsolute", "thisPath:" + thisPath);

        Intent intentCamera = new Intent();
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamera.resolveActivity(getActivity().getPackageManager()) != null) {
            String filePath = pathAbsolute;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            photoFile = new File(file, picName);
            if (!photoFile.exists()) {
                try {
                    photoFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            MLog.d("photoFile", "photoFile:" + photoFile);
            if (photoFile != null) {
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intentCamera, UI_SYSTEM_CAMERA_BACK);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UI_SYSTEM_CAMERA_BACK) {
            //MLog.d("photoFile", "photoFileonActivityResult:" + photoFile.getAbsolutePath());

            //读取文件到bitMap
            File picFile = new File(pathAbsolute, picName);
            if (!picFile.exists()) {
                return;
            }
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(picFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmapPic = BitmapFactory.decodeStream(fileInputStream);
            imageView.setImageBitmap(bitmapPic);

            //保存一张图片至磁盘
            File filePng = new File(Environment.getExternalStorageDirectory() + "/hahahahahahahahahahaha.png");
            if (filePng.exists()) {
                filePng.delete();
            }
            try {
                filePng.createNewFile();
                FileOutputStream out = new FileOutputStream(filePng);
                bitmapPic.compress(Bitmap.CompressFormat.PNG, 50, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
