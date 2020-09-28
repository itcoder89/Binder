package com.brosis.doubledate.newtab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.brosis.doubledate.CodeClasses.ApiRequest;
import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.CodeClasses.VersionChecker;
import com.brosis.doubledate.Inbox.Inbox_F;
import com.brosis.doubledate.Profile.Profile_F;
import com.brosis.doubledate.R;
import com.brosis.doubledate.Splash_A;
import com.brosis.doubledate.Users.Users_F;
import com.brosis.doubledate.UsersLikes.User_likes_F;
import com.brosis.doubledate.storage.LocalStorage;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class NewTabHome extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    long mBackPressed;
    public  static  NewTabHome newTabHome;
    public static SharedPreferences sharedPreferences;
    public static String user_id;
    public static String user_name;
    public static String user_pic;
    public static String birthday;
    public static String token;

    DatabaseReference rootref;

    BillingProcessor billingProcessor;

    public static boolean purduct_purchase = false;

    public static String action_type = "none";
    public static String receiverid = "none";
    public static String title = "none";
    public static String Receiver_pic = "none";

    private int[] tabIcons = {//ic_binder_color
            R.drawable.ic_user_inactive,
            R.drawable.ic_logo_active,
            R.drawable.ic_star_inactive,
            R.drawable.ic_msg_inactive
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (LocalStorage.getLaunchActivity(NewTabHome.this).equals("true")) {
          //  LocalStorage.setLaunchActivity(NewTabHome.this, "false");
            setContentView(R.layout.new_tab_layout);
        // places during using app
        sharedPreferences = getSharedPreferences(Variables.pref_name, MODE_PRIVATE);
        user_id = sharedPreferences.getString(Variables.uid, "null");
        user_name = sharedPreferences.getString(Variables.f_name, "") + " " +
                sharedPreferences.getString(Variables.l_name, "");
        birthday = sharedPreferences.getString(Variables.birth_day, "");
        user_pic = sharedPreferences.getString(Variables.u_pic, "null");

        token = FirebaseInstanceId.getInstance().getToken();
        if (token == null || (token.equals("") || token.equals("null")))
            token = sharedPreferences.getString(Variables.device_token, "null");

        if (getIntent().hasExtra("action_type")) {
            action_type = getIntent().getExtras().getString("action_type");
            receiverid = getIntent().getExtras().getString("receiverid");
            title = getIntent().getExtras().getString("title");
            Receiver_pic = getIntent().getExtras().getString("icon");
        }


        purduct_purchase = sharedPreferences.getBoolean(Variables.ispuduct_puchase, false);

        // check user if subscript or not both status we will save
        billingProcessor = new BillingProcessor(this, Variables.licencekey, this);
        billingProcessor.initialize();


        set_language_local();


        // get version of currently running app
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Variables.versionname = packageInfo.versionName;


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    //}
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Profile_F(), "ONE");
        adapter.addFragment(new Users_F(), "TWO");
        adapter.addFragment(new User_likes_F(null, true), "THREE");
        adapter.addFragment(new Inbox_F(), "Fourth");//http://doubledate.brosisbiz.com/android/API/index.php?p=myMatch
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(1);
        adapter.notifyDataSetChanged();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //int tabIconColor = ContextCompat.getColor(NewTabHome.this, R.color.pink_color);
                //tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.ic_user_active);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo_inactive);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_inactive);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_msg_inactive);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_logo_active);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_inactive);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_user_inactive);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_msg_inactive);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_star_active);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_user_inactive);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo_inactive);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_msg_inactive);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_msg_active);
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_user_inactive);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_logo_inactive);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star_inactive);
                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(NewTabHome.this, R.color.gray);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // below all method is belong to get the info that user is subscribe our app or not
    // keep in mind it is a listener so we will close the listener after checking in onBillingInitialized method
    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        sharedPreferences.edit().putBoolean(Variables.ispuduct_puchase, true).commit();
        purduct_purchase = true;
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

        if (billingProcessor.loadOwnedPurchasesFromGoogle()) {
            if ((billingProcessor.isSubscribed(Variables.product_ID) ||
                    billingProcessor.isSubscribed(Variables.product_ID2)) || billingProcessor.isSubscribed(Variables.product_ID3)) {

                sharedPreferences.edit().putBoolean(Variables.ispuduct_puchase, true).commit();
                purduct_purchase = true;
                billingProcessor.release();
                Call_Api_For_update_purchase("1");

            } else {

                sharedPreferences.edit().putBoolean(Variables.ispuduct_puchase, true).commit();
                purduct_purchase = true;
                Call_Api_For_update_purchase("0");

            }


        }
    }

    @Override
    protected void onDestroy() {

        if (billingProcessor != null) {
            billingProcessor.release();
        }


        Functions.unRegisterConnectivity(this);
        super.onDestroy();


    }


    // this method will get the version of app from play store and complate it with our present app version
    // and show the update message to update the application
    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }


    private void Call_Api_For_update_purchase(String purchase_value) {

        JSONObject parameters = new JSONObject();
        try {

            parameters.put("fb_id", NewTabHome.user_id);
            parameters.put("purchased", purchase_value);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //before
        ApiRequest.Call_Api(this, Variables.update_purchase_Status, parameters, null);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            // return null to display only the icon
            return null;
        }
    }


    public void set_language_local() {
        String language = sharedPreferences.getString(Variables.selected_language, null);
        Locale myLocale = new Locale(Locale.getDefault().getLanguage());
        if (language != null && language.equalsIgnoreCase(getString(R.string.english))) {
            myLocale = new Locale("en");

        } else if (language != null && language.equalsIgnoreCase(getString(R.string.arabic))) {
            myLocale = new Locale("ar");
        }

        if (myLocale.getLanguage().equalsIgnoreCase("en") || myLocale.getLanguage().equalsIgnoreCase("ar")) {

            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = new Configuration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            onConfigurationChanged(conf);

            Resources applicationRes = getApplicationContext().getResources();
            Configuration applicationConf = applicationRes.getConfiguration();
            applicationConf.setLocale(myLocale);
            applicationRes.updateConfiguration(applicationConf,
                    applicationRes.getDisplayMetrics());
        }

    }
}
