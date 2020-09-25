package com.brosis.doubledate.Profile;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brosis.doubledate.BuildConfig;
import com.brosis.doubledate.CodeClasses.ApiRequest;
import com.brosis.doubledate.CodeClasses.Callback;
import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.brosis.doubledate.Profile.EditProfile.EditProfile_F;
import com.brosis.doubledate.Profile.Profile_Details.Profile_Details_F;
import com.brosis.doubledate.Profile.adapter.CustomPagerAdapter;
import com.brosis.doubledate.R;
import com.brosis.doubledate.Settings.Setting_F;
import com.brosis.doubledate.Users.User_detail_F;
import com.brosis.doubledate.model.AddPartnerData;
import com.brosis.doubledate.newtab.NewTabHome;
import com.brosis.doubledate.storage.LocalStorage;
import com.google.gson.Gson;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_F extends RootFragment {

    View view;
    Context context;

     public static ImageView profile_image;
      TextView user_name;
      public static TextView age;


    LinearLayout setting_layout,edit_profile_layout,lladdmedia;
    TextView tvCopyCode;
    ImageView ivShare;
    String checktype="";
    LinearLayout llGenerteCode;
    AddPartnerData addPartnerData;
    EditText edEnterPartnerCode;
    public Profile_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        context=getContext();


        SpringDotsIndicator dots_indicator = (SpringDotsIndicator)view.findViewById(R.id.dots_indicator);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(getActivity()));
        dots_indicator.setViewPager(viewPager);

        edEnterPartnerCode=view.findViewById(R.id.edEnterPartnerCode);
        ivShare=view.findViewById(R.id.ivShare);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out our app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID+'\n'
                                +"Personal Code: "+ LocalStorage.getPersonalCode(getActivity()));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });



        tvCopyCode=view.findViewById(R.id.tvCopyCode);
        tvCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Copy Code",Toast.LENGTH_SHORT).show();
            }
        });

        lladdmedia=view.findViewById(R.id.lladdmedia);
        lladdmedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checktype="true";
                Editprofile();
            }
        });

        edit_profile_layout=view.findViewById(R.id.edit_profile_layout);
        edit_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checktype="false";
                Editprofile();
            }
        });



        llGenerteCode=view.findViewById(R.id.llGenerteCode);
        llGenerteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edEnterPartnerCode.getText().toString().trim().length() == 0){
                    Toast.makeText(context, "call generate code api..", Toast.LENGTH_SHORT).show();
                    /*JSONObject parameters = new JSONObject();
                    try {
                        parameters.put("fb_id", NewTabHome.user_id);
                        //parameters.put("partner_code",edEnterPartnerCode.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Functions.Show_loader(context,false,false);
                    ApiRequest.Call_Api(context, Variables.add_partner_info, parameters, new Callback() {
                        @Override
                        public void Responce(String resp) {
                            Functions.cancel_loader();
                            //addPartnerData = new Gson().fromJson(resp, AddPartnerData.class);
                            //Parse_user_info(resp);
                        }
                    });*/
                }else {
                    JSONObject parameters = new JSONObject();
                    try {
                        parameters.put("fb_id", NewTabHome.user_id);
                        parameters.put("partner_code",edEnterPartnerCode.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Functions.Show_loader(context,false,false);
                    ApiRequest.Call_Api(context, Variables.add_partner_info, parameters, new Callback() {
                        @Override
                        public void Responce(String resp) {
                            try {
                                Functions.cancel_loader();
                                //addPartnerData = new Gson().fromJson(resp, AddPartnerData.class);
                                try {
                                    JSONObject response = new JSONObject(resp);
                                    JSONArray jsonArray=response.optJSONArray("msg");
                                    Toast.makeText(context, ""+jsonArray.optJSONObject(0).optString("response"), Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(context, ""+addPartnerData.getMsg().get(0).getResponse(), Toast.LENGTH_SHORT).show();
                            }catch (Exception e){e.printStackTrace();}
                        }
                    });
                }
            }
        });
        setting_layout=view.findViewById(R.id.setting_layout);
        setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting_profile();
            }
        });


        profile_image=view.findViewById(R.id.profile_image);
        user_name=view.findViewById(R.id.user_name);
        age=view.findViewById(R.id.age);


        // show the picture age and username of the user

        if(NewTabHome.user_pic!=null  && !NewTabHome.user_pic.equals("") ){

            Uri uri;
            if(NewTabHome.user_pic.contains("http"))
                uri = Uri.parse(NewTabHome.user_pic);
            else
                uri = Uri.parse(Variables.image_base_url+NewTabHome.user_pic);

            Log.d(Variables.tag,Variables.image_base_url+NewTabHome.user_pic);
            profile_image.setImageURI(uri);
        }


        user_name.setText(NewTabHome.user_name);
        age.setText(" "+NewTabHome.birthday);


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile_detail();
            }
        });



        return view;
    }




    // open the view of Edit profile where 6 pic is visible
        public void Profile_detail(){

        Profile_Details_F profile_details_f = new Profile_Details_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, profile_details_f).commit();

    }



    // open the view of Edit profile where 6 pic is visible
    public void Editprofile(){
        /*EditProfile_F editProfile_f = new EditProfile_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        transaction.addToBackStack(null);
        Bundle args = new Bundle();
        args.putString("checktype",checktype);
        editProfile_f.setArguments(args);
        transaction.replace(R.id.MainMenuFragment, editProfile_f,"EditProfile_F").commit();*/

        Intent intent=new Intent(getActivity(), EditProfile_F.class);
        intent.putExtra("checktype",checktype);
        startActivity(intent);
    }


    // open the view of Edit profile where 6 pic is visible
    public void Setting_profile(){
        /*Setting_F setting_f = new Setting_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, setting_f).commit();*/


        Intent intent=new Intent(getActivity(), Setting_F.class);
        //intent.putExtra("checktype",checktype);
        startActivity(intent);
    }





}
