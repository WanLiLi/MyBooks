package org.wowser.evenbuspro.activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.wowser.evenbuspro.BusProvider;
import org.wowser.evenbuspro.R;
import org.wowser.evenbuspro.fragments.CameraFragment;
import org.wowser.evenbuspro.fragments.ChartFragment;
import org.wowser.evenbuspro.fragments.CustomDragViewFragment;
import org.wowser.evenbuspro.fragments.CustomGalleryFragment;
import org.wowser.evenbuspro.fragments.FragmentFour;
import org.wowser.evenbuspro.fragments.FragmentOne;
import org.wowser.evenbuspro.fragments.FragmentThree;
import org.wowser.evenbuspro.fragments.FragmentTwo;
import org.wowser.evenbuspro.fragments.GreedaoFragment;
import org.wowser.evenbuspro.utils.MLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MainActivity extends BaseActiviy implements ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener {
    final int REQUESTCODE_CAMERA = 1;
    DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;

    ViewPager viewpager;
    private Fragment[] fragments;
    private TabLayout tabLayout;

    private static final String[] titles = new String[]{"Tab 1", "Tab 2", "Tab 3", "Tab 4"};
    private static final Integer[] tabIma = new Integer[]{R.drawable.daily_normal, R.drawable.daily_normal, R.drawable.daily_normal, R.drawable.daily_normal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        File filePath = this.getFilesDir().getAbsoluteFile(); // /data/data/com.mbox.cn/files

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();  // /storage/emulated/0
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String picName = "wanli";
        File photoFile = new File(file, picName);  // /storage/emulated/0/wanli


//        File png = new File(Environment.getExternalStorageDirectory()+"/eee.png");
//        try {
//            png.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        MLog.d("path", "getAbsolutePath:" + photoFile.getAbsolutePath());
    }


    void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_public);
        setSupportActionBar(toolbar);    //显示标题
        toolbar.setTitle("MyApp");   //等同getSupportActionBar().setTitle("TabbedCoordinatorLayout");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //返回图标<
        //toolbar.setSubtitle("Tab1");
//      toolbar.setNavigationIcon(R.drawable.iconfont_menu);


        //设置actionBar中显示的打开draweLayout的图z标，
//        ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.iconfont_menu);
//        ab.setDisplayHomeAsUpEnabled(true);


        //绑定drawerLayout，并自动设置三变一图标，自动监听打开drawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.txt_Press, R.string.txt_Press);
        //mDrawerLayout.setDrawerListener(toggle);  //已废弃
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        //tabLayout.addTab(tabLayout.newTab().setText("a"));
        //tabLayout.addTab(tabLayout.newTab().setText("b"));
        fragments = new Fragment[4];
        FragmentOne one = new FragmentOne();
        FragmentTwo two = new FragmentTwo();
        FragmentThree three = new FragmentThree();
        FragmentFour four = new FragmentFour();

        fragments[0] = one;
        fragments[1] = two;
        fragments[2] = three;
        fragments[3] = four;

        myViewPagerAdapter adapter = new myViewPagerAdapter(getSupportFragmentManager(), fragments, this);
        viewpager.setAdapter(adapter);
        //viewpager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewpager);

//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if(tab!=null)
//            {
//                tab.setCustomView(adapter.getTabView(i));
//            }
//        }


        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);
        collapsingToolbar.setTitleEnabled(false);
//        collapsingToolbar.setTitle("Title");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "我从下面出来", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_recent) {
            viewpager.setCurrentItem(0);
        } else if (itemId == R.id.nav_installed) {
            viewpager.setCurrentItem(1);
        } else if (itemId == R.id.nav_exported) {
            viewpager.setCurrentItem(2);
        } else if (itemId == R.id.nav_tab4) {
            viewpager.setCurrentItem(3);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    static class myViewPagerAdapter extends FragmentPagerAdapter {
        private Fragment[] fragments;
        private Context context;
        OnChangeTab onChangeTab;

        public myViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public myViewPagerAdapter(FragmentManager fm, Fragment[] fragments, Context context) {
            super(fm);
            this.fragments = fragments;
            this.context = context;
        }

        public Fragment[] getFragments() {
            return fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }


        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(titles[position]);
            ImageView img = (ImageView) v.findViewById(R.id.image);
            img.setImageResource(tabIma[position]);
            return v;
        }

        public void setTabColor(int color) {
            if (onChangeTab != null) {
                onChangeTab.onChangColor(color);
            }
        }

        public interface OnChangeTab {
            void onChangColor(int color);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**权限解决方案**/
    /**
     * 询问是否授权 camera 1
     */
    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(PermissionRequest request) {
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        request.proceed();
    }

    /**
     * 询问是否授权 contact   2
     */
    @OnShowRationale({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showRationaleForContact(PermissionRequest request) {

    }

//    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION})
//    void showRationaleForLocation(PermissionRequest request) {
//
//    }


    /**
     * 处理当用户允许该权限时需要处理的方法  Camera 1
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    void showCameras() {
        Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent4, 1);
    }

    /**
     * 处理当用户允许该权限时需要处理的方法   Contacts 2
     */
    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    void showContacts() {

    }


    //location
//    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION})
//    void showLocation(PermissionRequest request) {
//
//    }

    /**
     * 如果用户不再提示权限执行的方法 不再提示
     */
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, "不再提示", Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果用户拒绝该权限执行的方法  拒绝了
     */
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(this, "拒绝了", Toast.LENGTH_SHORT).show();
    }

    public static String CUSTOMDIBUFRAGMENT = "customDibuFragment";


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);   //只要设置了toolBar的setNavigationIconh或者actionBar的setHome。。
                break;
            case R.id.menu_qr:
                Intent intent = new Intent();
                intent.setClass(this, ErActivity.class);
                startActivityForResult(intent, ErActivity.QR_SCANNER_TEST);
                break;
            case R.id.menu_ottoTest:
                Integer color = 0;
                BusProvider.getInstance().post(color);
                break;
            case R.id.customViewTestActivity:
                Intent intent1 = new Intent(this, CustomViewTest.class);
                startActivity(intent1);
                break;
            case R.id.menu_service:
                Intent intent2 = new Intent(this, Service2Activity.class);
                startActivity(intent2);
                break;
            case R.id.menu_cantact:
                int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    insertDummyContact();
                }
                break;
            case R.id.capture:
                //int hasCamera = checkSelfPermission(Manifest.permission_group.CAMERA);  //6.0以下异常
                int hasCamera = ContextCompat.checkSelfPermission(this, Manifest.permission_group.CAMERA);  //兼容所有api
                if (hasCamera != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUESTCODE_CAMERA); //兼容所有api
                    //requestPermissions(new String[]{Manifest.permission.CAMERA},      //6.0以下异常
                    //        REQUESTCODE_CAMERA);
                } else {
                    Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent4, 1);
                }
                //MainActivityPermissionsDispatcher.showCamerasWithCheck(this);
                break;
            case R.id.custom_dibugundong:
                Intent intent3 = new Intent(this, ShowBaseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tag", CUSTOMDIBUFRAGMENT);
                intent3.putExtra("bund_Key", bundle);
                startActivity(intent3);
                break;
            case R.id.custom_dragHelper:
                Intent intent4 = new Intent(this, ShowBaseActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("tag", CustomDragViewFragment.TAG);
                intent4.putExtra("bund_Key", bundle4);
                startActivity(intent4);
                break;
            case R.id.baiduMap:
                Intent intentBaidu = new Intent(this, BaiduMapActivity.class);
                startActivity(intentBaidu);
                break;
            case R.id.photograph:
                Intent intentCamera = new Intent(this, ShowBaseActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("tag", CameraFragment.TAG);
                intentCamera.putExtra("bund_Key", bundle1);
                startActivity(intentCamera);
                break;
            case R.id.helloChart:
                Intent intentHelloChart = new Intent(this, ShowBaseActivity.class);
                Bundle bundleChart = new Bundle();
                bundleChart.putString("tag", ChartFragment.TAG);

                intentHelloChart.putExtra("bund_Key", bundleChart);
                startActivity(intentHelloChart);
                break;
            case R.id.customRecycGellery:
                Intent intent5 = new Intent(this, ShowBaseActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("tag", CustomGalleryFragment.TAG);
                intent5.putExtra("bund_Key", bundle2);
                startActivity(intent5);
                break;
            case R.id.greedao:
                Intent intent6 = new Intent(this, ShowBaseActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putString("tag", GreedaoFragment.TAG);
                intent6.putExtra("bund_Key", bundle6);
                startActivity(intent6);
                break;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent4);
                } else {
                    Toast.makeText(MainActivity.this, "相机没有授权", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case 2:
                if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意使用write
                    insertDummyContact();
                } else {
                    Toast.makeText(MainActivity.this, "联系人", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    private static final String TAG = "Contacts";

    private void insertDummyContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }


    String handleResult;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ErActivity.QR_SCANNER_TEST && resultCode == RESULT_OK) {
            handleResult = data.getStringExtra("handleResult");
            if (handleResult.contains("http")) {
                openWeb(data);
            } else {
                //btn2.setText(handleResult);
                //Log.d("wanli","handleResult："+handleResult);
            }
        }
    }

    public void openWeb(Intent data) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, WebViewActivity.class);
        intent.putExtra("handleResult", handleResult);
        startActivity(intent);
        finish();
    }


    @Subscribe
    public void changeImgColor(Integer colorId) {
        Toast.makeText(MainActivity.this, "4444", Toast.LENGTH_LONG).show();
    }


//    class myViewPagerAdapter2 extends PagerAdapter {
//        private List<Fragment> fragments;
//
//        myViewPagerAdapter2(List<Fragment> fragments) {
//            this.fragments = fragments;
//        }
//
//        //返回要滑动的VIew的个数
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//        //对于这个函数就先不做讲解，大家目前先知道它要这样重写就行了，后面我们会对它进行改写。
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//
//        @Override
//        public int getItemPosition(Object object) {
//            return super.getItemPosition(object);
//        }
//
//
//        //做了两件事，第一：将当前视图添加到container中，第二：返回当前View
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            return fragments.get(position);
//            //container.addView();
//        }
//
//
//        //从当前container中删除指定位置（position）的View;
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
//        }
//    }

}
