package com.brosis.doubledate.Users;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;
import com.brosis.doubledate.Accounts.Login_A;
import com.brosis.doubledate.Boost.Boost_F;
import com.brosis.doubledate.CodeClasses.ApiRequest;
import com.brosis.doubledate.CodeClasses.Callback;
import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.InAppSubscription.InApp_Subscription_A;
import com.brosis.doubledate.Inbox.Match_Get_Set;
import com.brosis.doubledate.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.brosis.doubledate.Matchs.Match_F;
import com.brosis.doubledate.R;
import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.model.GetNearByUserData;
import com.brosis.doubledate.newtab.NewTabHome;
import com.brosis.doubledate.storage.LocalStorage;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;
import com.zhouyou.view.seekbar.SignSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Users_F extends RootFragment implements View.OnClickListener {
    View view;
    Context context;

    CardStackView card_viewstack;

    ImageButton detail_btn;

    User_Adapter adapter;

    ImageButton refresh_btn,cross_btn,heart_btn,supperlike_btn,boost_btn;


    public Users_F() {
        // Required empty public constructor
    }

    RelativeLayout user_list_layout,find_nearby_User;
    DatabaseReference rootref;
    boolean is_Api_running=false;
    boolean is_view_load=false;
    public static GetNearByUserData getNearByUserData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_users, container, false);

        context=getContext();

        adapter=new User_Adapter(context);


        rootref= FirebaseDatabase.getInstance().getReference();



        ImageView profile_image=view.findViewById(R.id.profileimage);
        if(!NewTabHome.user_pic.equals(""))
            Picasso.with(context).
                    load(NewTabHome.user_pic)
                    .placeholder(R.drawable.image_placeholder)
                    .into(profile_image);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!is_Api_running){
                    is_Api_running=true;
                    //before
                    ///GetPeople_nearby(false);
                }
            }
        });



        GetPeople_nearby(false);


        card_viewstack=view.findViewById(R.id.card_viewstack);
        card_viewstack.setAdapter(adapter);
        card_viewstack.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {

                // card swipe get the top user index for get the data in list
                int positon=card_viewstack.getTopIndex()-1;


                if(positon<adapter.getCount()) {

                    final Nearby_User_Get_Set item = adapter.getItem(positon);


                    // if swipe left the we will call a fuction for update the value in firebase
                    if (direction.equals(SwipeDirection.Left)) {

                        updatedata_onLeftSwipe(item);

                    }

                    // if swipe Right the we will call a fuction for update the value in firebase

                    else if (direction.equals(SwipeDirection.Right)) {

                        if(NewTabHome.sharedPreferences.getInt(Variables.user_like_limit,0)>0){
                            updatedata_onrightSwipe(item);
                        }
                        else if(!NewTabHome.purduct_purchase){
                            card_viewstack.reverse();
                            open_subscription_view();
                        }
                        else {
                            card_viewstack.reverse();
                            Toast.makeText(context, "Your Likes Limit exceeded", Toast.LENGTH_SHORT).show();
                        }


                    }

                    else if(direction.equals(SwipeDirection.Top)){

                        if(NewTabHome.sharedPreferences.getInt(Variables.user_superlike_limit,0)>0){
                            updatedata_onTopSwipe(item);
                        }
                        else if(!NewTabHome.purduct_purchase){
                            card_viewstack.reverse();
                            open_subscription_view();
                        }
                        else {
                            card_viewstack.reverse();
                            Toast.makeText(context, "Your SuperLikes Limit exceeded", Toast.LENGTH_SHORT).show();
                        }

                    }

                    // find if the swipes card is last or not
                    if (card_viewstack.getTopIndex() == adapter.getCount()) {
                        // if last then we will replace the view and show the ad
                        //before show ads when last swipe comment
                        /*if(mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }*/

                        ShowfindingView();
                    }

                }
            }

            @Override
            public void onCardReversed() {
                // card swipe get the top user index for get the data in list
                int positon=card_viewstack.getTopIndex();

                if(positon<adapter.getCount()) {
                    final Nearby_User_Get_Set item = adapter.getItem(positon);
                    updatedata_onreverse(item);
                }

                ShowUser_ListView();

                }

            @Override
            public void onCardMovedToOrigin() {

            }

            @Override
            public void onCardClicked(int index) {

            }
        });



        // on press detail btn we will open the detail of user
        detail_btn=view.findViewById(R.id.detail_btn);
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_user_detail();
            }
        });


        user_list_layout=view.findViewById(R.id.user_list_layout);
        find_nearby_User=view.findViewById(R.id.find_nearby_User);


        // intialize the bottom three btns
        init_bottom_view();

        is_view_load=true;
        return view;

    }





    // when we swipe left , right or reverse then this method is call and update the value in firebase database
    public void updatedata_onLeftSwipe(final Nearby_User_Get_Set item){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh");
        final String formattedDate = df.format(c);


        Map mymap=new HashMap<>();
        mymap.put("match","false");
        mymap.put("type","dislike");
        mymap.put("status","0");
        mymap.put("time",formattedDate);
        mymap.put("name",item.getName());
        mymap.put("effect","true");


        Map othermap=new HashMap<>();
        othermap.put("match","false");
        othermap.put("type","dislike");
        othermap.put("status","0");
        othermap.put("time",formattedDate);
        othermap.put("name",NewTabHome.user_name);
        othermap.put("effect","false");

        rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).updateChildren(mymap);
        rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).updateChildren(othermap);


        SendPushNotification(item.fb_id,NewTabHome.user_name+" has Dislike your Profile");

    }



    public void updatedata_onrightSwipe(final Nearby_User_Get_Set item){

        NewTabHome.sharedPreferences.edit()
                .putInt(Variables.user_like_limit,
                        (NewTabHome.sharedPreferences.getInt
                                (Variables.user_like_limit,0)-1))
                .commit();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh");
        final String formattedDate = df.format(c);

        Query query=rootref.child("Match").child(item.getFb_id()).child(NewTabHome.user_id);
        query.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(((dataSnapshot.exists() && (dataSnapshot.child("type").getValue().equals("like"))) || item.getSwipe().equals("like"))
                        ||
                        (dataSnapshot.exists() &&(dataSnapshot.child("type").getValue().equals("superLike"))) || item.getSwipe().equals("superLike")){

                    Map mymap=new HashMap<>();
                    mymap.put("match","true");
                    mymap.put("type","like");
                    mymap.put("status","0");
                    mymap.put("time",formattedDate);
                    mymap.put("name",item.getName());
                    mymap.put("effect","true");

                    Map othermap=new HashMap<>();
                    othermap.put("match","true");
                    othermap.put("type","like");
                    othermap.put("status","0");
                    othermap.put("time",formattedDate);
                    othermap.put("name",NewTabHome.user_name);
                    othermap.put("effect","false");

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).updateChildren(mymap);
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).updateChildren(othermap);

                    Match_Get_Set match_get_set=new Match_Get_Set();
                    match_get_set.setU_id(item.getFb_id());
                    match_get_set.setUsername(item.getFirst_name());
                    match_get_set.setPicture(item.getImagesurl().get(0));
                    openMatch(match_get_set);
                }
                else {
                    Map mymap=new HashMap<>();
                    mymap.put("match","false");
                    mymap.put("type","like");
                    mymap.put("status","0");
                    mymap.put("time",formattedDate);
                    mymap.put("name",item.getName());
                    mymap.put("effect","true");

                    Map othermap=new HashMap<>();
                    othermap.put("match","false");
                    othermap.put("type","like");
                    othermap.put("status","0");
                    othermap.put("time",formattedDate);
                    othermap.put("name",NewTabHome.user_name);
                    othermap.put("effect","false");

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).setValue(mymap);
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).setValue(othermap);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SendPushNotification(item.fb_id,NewTabHome.user_name+" has Likes your Profile");


    }


    public void updatedata_onTopSwipe(final Nearby_User_Get_Set item){

        NewTabHome.sharedPreferences.edit()
                .putInt(Variables.user_superlike_limit,
                        (NewTabHome.sharedPreferences.getInt
                                (Variables.user_superlike_limit,0)-1))
                .commit();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("hh");
        final String formattedDate = df.format(c);

        Query query=rootref.child("Match").child(item.getFb_id()).child(NewTabHome.user_id);
        query.keepSynced(true);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(((dataSnapshot.exists() && (dataSnapshot.child("type").getValue().equals("like"))) || item.getSwipe().equals("like"))
                         ||
                        (dataSnapshot.exists() &&(dataSnapshot.child("type").getValue().equals("superLike"))) || item.getSwipe().equals("superLike")){

                    Map mymap=new HashMap<>();
                    mymap.put("match","true");
                    mymap.put("type","superLike");
                    mymap.put("status","0");
                    mymap.put("time",formattedDate);
                    mymap.put("name",item.getName());
                    mymap.put("effect","true");

                    Map othermap=new HashMap<>();
                    othermap.put("match","true");
                    othermap.put("type","superLike");
                    othermap.put("status","0");
                    othermap.put("time",formattedDate);
                    othermap.put("name",NewTabHome.user_name);
                    othermap.put("effect","false");

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).updateChildren(mymap);
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).updateChildren(othermap);



                    Match_Get_Set match_get_set=new Match_Get_Set();
                    match_get_set.setU_id(item.getFb_id());
                    match_get_set.setUsername(item.getName());
                    match_get_set.setPicture(item.getImagesurl().get(0));
                    openMatch(match_get_set);
                }
                else {
                    Map mymap=new HashMap<>();
                    mymap.put("match","false");
                    mymap.put("type","superLike");
                    mymap.put("status","0");
                    mymap.put("time",formattedDate);
                    mymap.put("name",item.getName());
                    mymap.put("effect","true");

                    Map othermap=new HashMap<>();
                    othermap.put("match","false");
                    othermap.put("type","superLike");
                    othermap.put("status","0");
                    othermap.put("time",formattedDate);
                    othermap.put("name",NewTabHome.user_name);
                    othermap.put("effect","false");

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).setValue(mymap);
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).setValue(othermap);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SendPushNotification(item.fb_id,NewTabHome.user_name+" has Likes your Profile");

    }


    public void updatedata_onreverse(final Nearby_User_Get_Set item){

        Query query=rootref.child("Match").child(item.getFb_id()).child(NewTabHome.user_id);
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.child("match").getValue().equals("true")){

                    Map mymap=new HashMap<>();
                    mymap.put("match","false");

                    Map othermap=new HashMap<>();
                    othermap.put("match","false");

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).updateChildren(mymap);
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).updateChildren(othermap);

                }
                else {

                    rootref.child("Match").child(NewTabHome.user_id+"/"+item.getFb_id()).removeValue();
                    rootref.child("Match").child(item.getFb_id()+"/"+NewTabHome.user_id).removeValue();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




// when ever a user go to the main center view then we call a api of nearby
    //before
    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if((isVisibleToUser && Variables.is_reload_users) && is_view_load){
            if(!is_Api_running){
                is_Api_running=true;
                Variables.is_reload_users =false;
                GetPeople_nearby(false);
            }
        }
    }*/


   // when all the card is swiped then this mehtod will call and replace the view and show the ad
    public void ShowfindingView(){
        Variables.is_reload_users =true;
        user_list_layout.setVisibility(View.GONE);
        find_nearby_User.setVisibility(View.VISIBLE);

        final PulsatorLayout pulsator = (PulsatorLayout) view.findViewById(R.id.pulsator);
        pulsator.start();

        view.findViewById(R.id.change_setting_btn).setOnClickListener(this);
        view.findViewById(R.id.finding_refresh_btn).setOnClickListener(this);

    }


    public void ShowUser_ListView(){
        user_list_layout.setVisibility(View.VISIBLE);
        find_nearby_User.setVisibility(View.GONE);
    }




    // this method will intializae the inertial add will will show when user swipe all the users
    private InterstitialAd mInterstitialAd;
    @Override
    public void onResume() {
        super.onResume();
        //before
        /*MobileAds.initialize(context, context.getResources().getString(R.string.admob_appid));

        //code for intertial add
        mInterstitialAd = new InterstitialAd(context);

        //here we will get the add id keep in mind above id is app id and below Id is add Id
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.my_Interstitial_Add));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });*/


    }



    // intilize the bottom three buttons
    public void init_bottom_view(){

        refresh_btn=view.findViewById(R.id.refresh_btn);
        cross_btn=view.findViewById(R.id.cross_btn);
        heart_btn=view.findViewById(R.id.heart_btn);
        supperlike_btn=view.findViewById(R.id.supperlike_btn);
        boost_btn=view.findViewById(R.id.boost_btn);

        refresh_btn.setOnClickListener(this);
        cross_btn.setOnClickListener(this);
        heart_btn.setOnClickListener(this);
        supperlike_btn.setOnClickListener(this);
        boost_btn.setOnClickListener(this);

        //before
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(fm.findFragmentByTag("User_detail_F") instanceof User_detail_F){
                  if(fm.getBackStackEntryCount()>0) {
                      boost_btn.setVisibility(View.INVISIBLE);
                      refresh_btn.setVisibility(View.INVISIBLE);
                  }else {
                      boost_btn.setVisibility(View.VISIBLE);
                      refresh_btn.setVisibility(View.VISIBLE);
                  }
                }

            }
        });

    }




    /// / all the action that will performe the bottom 3 buttons
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.cross_btn:
                Clearbackstack();
                swipeLeft();
                break;

            case R.id.heart_btn:
                Clearbackstack();
                if( NewTabHome.sharedPreferences.getInt(Variables.user_like_limit,0)>0){
                swipeRight();
                }
                else if(!NewTabHome.purduct_purchase){
                    open_subscription_view();
                }
                else
                    Toast.makeText(context, context.getResources().getString(R.string.likes_limit), Toast.LENGTH_SHORT).show();

                break;

            case R.id.boost_btn:
                if(NewTabHome.purduct_purchase){
                    openBoost();
                }
                else
                    open_subscription_view();
                break;

            case R.id.supperlike_btn:
                Clearbackstack();
                if(NewTabHome.sharedPreferences.getInt(Variables.user_superlike_limit,0)>0){
                    swipeTop();
                }
                else if(!NewTabHome.purduct_purchase){
                    open_subscription_view();
                }
                else
                    Toast.makeText(context, context.getResources().getString(R.string.superlikes_limit), Toast.LENGTH_SHORT).show();

                break;

            case R.id.refresh_btn:

                if(NewTabHome.purduct_purchase){
                    card_viewstack.reverse();
                }
                else
                open_subscription_view();
                break;

            case R.id.finding_refresh_btn:
                if(!NewTabHome.purduct_purchase){
                    card_viewstack.reverse();
                }
                else
                    open_subscription_view();
                break;

            case R.id.change_setting_btn:
                open_discoverysetting_dialog();
                break;

        }
    }




    public void swipeLeft() {

        View target = card_viewstack.getTopView();
        View targetOverlay = card_viewstack.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(400);
        translateY.setStartDelay(400);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        card_viewstack.swipe(SwipeDirection.Left, cardAnimationSet, overlayAnimationSet);
    }

    public void swipeRight() {

        View target = card_viewstack.getTopView();
        View targetOverlay = card_viewstack.getTopView().getOverlayContainer();

        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", 10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(400);
        translateY.setStartDelay(400);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether(rotation, translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        card_viewstack.swipe(SwipeDirection.Right, cardAnimationSet, overlayAnimationSet);
    }


    public void swipeTop() {

        View target = card_viewstack.getTopView();
        View targetOverlay = card_viewstack.getTopView().getOverlayContainer();


        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, 0f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, -2000f));
        translateX.setStartDelay(400);
        translateY.setStartDelay(400);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet cardAnimationSet = new AnimatorSet();
        cardAnimationSet.playTogether( translateX, translateY);

        ObjectAnimator overlayAnimator = ObjectAnimator.ofFloat(targetOverlay, "alpha", 0f, 1f);
        overlayAnimator.setDuration(200);
        AnimatorSet overlayAnimationSet = new AnimatorSet();
        overlayAnimationSet.playTogether(overlayAnimator);

        card_viewstack.swipe(SwipeDirection.Top, cardAnimationSet, overlayAnimationSet);
    }



    public void Clearbackstack(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

    }


     private void GetPeople_nearby(final boolean is_show_loader) {

        if(is_show_loader){
           Functions.Show_loader(context,false,false);
        }

        String latlong="";
        if(NewTabHome.sharedPreferences.getBoolean(Variables.is_seleted_location_selected,false)){

            latlong=NewTabHome.sharedPreferences.getString(Variables.seleted_Lat,"33.738045")+", "+NewTabHome.sharedPreferences.getString(Variables.selected_Lon,"73.084488");
        }else {
            latlong=NewTabHome.sharedPreferences.getString(Variables.current_Lat,"33.738045")+", "+NewTabHome.sharedPreferences.getString(Variables.current_Lon,"73.084488");
        }



        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", NewTabHome.user_id);
            parameters.put("lat_long", latlong);
            parameters.put("gender", NewTabHome.sharedPreferences.getString(Variables.show_me,"all"));
            parameters.put("age_min", ""+NewTabHome.sharedPreferences.getInt(Variables.min_age,Variables.min_default_age));
            parameters.put("age_max", ""+NewTabHome.sharedPreferences.getInt(Variables.max_age,Variables.max_default_age));
            parameters.put("distance", ""+NewTabHome.sharedPreferences.getInt(Variables.max_distance,Variables.default_distance));
            parameters.put("version",Variables.versionname);
            parameters.put("device_token",NewTabHome.token);
            parameters.put("device",context.getResources().getString(R.string.device));
            if(NewTabHome.purduct_purchase)
                parameters.put("purchased","1");
            else
                parameters.put("purchased","0");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(context, Variables.userNearByMe, parameters, new Callback() {
            @Override
            public void Responce(String resp) {
                Functions.cancel_loader();
                getNearByUserData = new Gson().fromJson(resp, GetNearByUserData.class);
                is_Api_running=false;
                Parse_user_info(resp);
            }
        });

    }


    String block="0";
    public void Parse_user_info(String loginData){
        try {
            JSONObject jsonObject=new JSONObject(loginData);
            String code=jsonObject.optString("code");
            if(code.equals("200")){
                adapter.clear();
                JSONArray msg=jsonObject.getJSONArray("msg");
                JSONArray promoted=jsonObject.getJSONArray("promoted");
                JSONArray user_info=jsonObject.getJSONArray("user_info");

                if(promoted!=null) {

                    for (int i = 0; i < promoted.length(); i++) {
                        JSONObject userdata = promoted.getJSONObject(i);
                        parse_User_Data(userdata);
                    }
                }

                if(msg!=null) {

                    for (int i = 0; i < msg.length(); i++) {
                        JSONObject userdata = msg.getJSONObject(i);

                        parse_User_Data(userdata);
                    }

                }

                if(user_info!=null){
                    JSONObject my_data_onject=user_info.getJSONObject(0);
                    SharedPreferences.Editor editor=NewTabHome.sharedPreferences.edit();
                    editor.putInt(Variables.user_like_limit,Integer.parseInt(my_data_onject.optString("like_limit","0")));
                    editor.putInt(Variables.user_superlike_limit,Integer.parseInt(my_data_onject.optString("super_like_limit","0")));

                    LocalStorage.setPersonalCode(getActivity(),my_data_onject.optString("personal_code"));
                    LocalStorage.setPartnerCode(getActivity(),my_data_onject.optString("partner_code"));
                    Log.e("LocalStorage",LocalStorage.getPersonalCode(getActivity()));

                    if(my_data_onject.optString("hide_location","0").equals("1")){
                        editor.putBoolean(Variables.hide_Distance,true);

                    }

                    if(my_data_onject.optString("hide_age","0").equals("1")){
                        editor.putBoolean(Variables.hide_age,true);
                    }


                    editor.commit();
                }

                if(!(msg.length()>0) && !(promoted.length()>0)){
                    ShowfindingView();
                }

                else {
                    ShowUser_ListView();
                }

                is_Api_running=false;
                adapter.notifyDataSetChanged();

                if(block.equals("1")){
                    NewTabHome.sharedPreferences.edit().putBoolean(Variables.islogin,false).commit();
                    startActivity(new Intent(getActivity(), Login_A.class));
                    getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    getActivity().finish();

                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
            ShowfindingView();
        }





    }


    public void parse_User_Data(JSONObject userdata) throws JSONException {
        Nearby_User_Get_Set item=new Nearby_User_Get_Set();
        item.setFb_id(userdata.optString("fb_id"));
        item.setFirst_name(userdata.optString("first_name"));
        item.setLast_name(userdata.optString("last_name"));
        item.setName(userdata.optString("first_name")+" "+userdata.optString("last_name"));
        item.setJob_title(userdata.optString("job_title"));
        item.setCompany(userdata.optString("company"));
        item.setSchool(userdata.optString("school"));
        item.setBirthday(userdata.optString("birthday"));
        item.setAbout(userdata.optString("about_me"));
        item.setLocation(userdata.optString("distance"));
        item.setGender(""+userdata.optString("gender"));
        item.setSwipe(""+userdata.optString("swipe"));
        item.setSuper_like(""+userdata.optString("super_like"));

        ArrayList<String> images=new ArrayList<>();
        images.add(userdata.optString("image1"));
        if(!userdata.optString("image2").equals(""))
            images.add(userdata.optString("image2"));
        if(!userdata.optString("image3").equals(""))
            images.add(userdata.optString("image3"));
        if(!userdata.optString("image4").equals(""))
            images.add(userdata.optString("image4"));
        if(!userdata.optString("image5").equals(""))
            images.add(userdata.optString("image5"));
        if(!userdata.optString("image6").equals(""))
            images.add(userdata.optString("image6"));
        item.setImagesurl(images);

        item.setHide_age(""+userdata.optString("hide_age"));
        item.setHide_location(""+userdata.optString("hide_location"));
        item.setSmoke(""+userdata.optString("smoke"));
        item.setDrink(""+userdata.optString("drink"));
        item.setHobbies(""+userdata.optString("hobbies"));
        item.setPartner_code(""+userdata.optString("partner_code"));
        item.setPersonal_code(""+userdata.optString("personal_code"));

        block=userdata.optString("block");


        adapter.add(item);
    }


      // this mehtod will show the view which will show the more detail about the user
    public void open_user_detail(){

        //before
        Nearby_User_Get_Set item = adapter.getItem(card_viewstack.getTopIndex());
        Log.e("getTopIndex", "pos "+card_viewstack.getTopIndex());
        User_detail_F user_detail_f = new User_detail_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("data",item);
        args.putString("first_name",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getFirst_name());
        args.putString("last_name",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getLast_name());
        args.putString("about_me",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getAbout_me());
        args.putString("job_title",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getJob_title());
        args.putString("gender",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getGender());
        args.putString("birthday",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getBirthday());
        args.putString("company",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getCompany());
        args.putString("school",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getSchool());
        args.putString("age",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getAge()+"");
        args.putString("partner_code",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getPartner_code());
        args.putString("personal_code",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getPersonal_code());
        args.putString("image1",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage1());
        args.putString("image2",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage2());
        args.putString("image3",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage3());
        args.putString("image4",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage4());
        args.putString("image5",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage5());
        args.putString("image6",getNearByUserData.getMsg().get(card_viewstack.getTopIndex()).getPartner_details().getImage6());
        user_detail_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, user_detail_f,"User_detail_F").commit();

        /*Intent intent=new Intent(getActivity(),User_detail_F.class);
        intent.putExtra("data",item);
        intent.putExtra("from_where","user_list");
        startActivity(intent);*/

    }
    // when user will click the refresh btn  then this view will be open for subscribe it in our app
    public void open_subscription_view(){
        /*InApp_Subscription_A inApp_subscription_a = new InApp_Subscription_A();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainMenuFragment, inApp_subscription_a)
                .addToBackStack(null)
                .commit();*/

        Intent intent=new Intent(getActivity(),InApp_Subscription_A.class);
        startActivity(intent);

    }



    private void open_discoverysetting_dialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_discovery_setting_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.d_top_bottom_border_line));


        TextView cancel_btn=dialog.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        TextView update_btn=dialog.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPeople_nearby(true);
                dialog.cancel();
            }
        });


        SignSeekBar distance_bar=dialog.findViewById(R.id.distance_bar);
        final TextView distance_txt=dialog.findViewById(R.id.distance_txt);
        distance_bar.setProgress(NewTabHome.sharedPreferences.getInt(Variables.max_distance,Variables.default_distance));
        distance_txt.setText(NewTabHome.sharedPreferences.getInt(Variables.max_distance,Variables.default_distance)+" "+ context.getResources().getString(R.string.miles));
        distance_bar.setOnProgressChangedListener(new SignSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(SignSeekBar signSeekBar, int progress, float progressFloat,boolean fromUser) {
                distance_txt.setText(progress+" "+ context.getResources().getString(R.string.miles));
            }

            @Override
            public void getProgressOnActionUp(SignSeekBar signSeekBar, int progress, float progressFloat) {
                Variables.is_reload_users =true;
                NewTabHome.sharedPreferences.edit().putInt(Variables.max_distance,progress).commit();
            }

            @Override
            public void getProgressOnFinally(SignSeekBar signSeekBar, int progress, float progressFloat,boolean fromUser) {

            }
        });



        // this is the age seek bar. when we change the progress of seek bar it will be locally save
        RangeBar age_seekbar=dialog.findViewById(R.id.age_seekbar);
        final TextView age_range_txt=dialog.findViewById(R.id.age_range_txt);

        age_range_txt.setText(NewTabHome.sharedPreferences.getInt(Variables.min_age,Variables.min_default_age)+" - "+
                NewTabHome.sharedPreferences.getInt(Variables.max_age,Variables.max_default_age)+" "+ context.getResources().getString(R.string.years));
        age_seekbar.setRangePinsByValue(NewTabHome.sharedPreferences.getInt(Variables.min_age,Variables.min_default_age),
                NewTabHome.sharedPreferences.getInt(Variables.max_age,Variables.max_default_age));
        age_seekbar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {

                Variables.is_reload_users =true;

                SharedPreferences.Editor editor=NewTabHome.sharedPreferences.edit();
                editor.putInt(Variables.min_age,Integer.parseInt(leftPinValue));
                editor.putInt(Variables.max_age,Integer.parseInt(rightPinValue));
                editor.commit();

                age_range_txt.setText(leftPinValue+" - "+rightPinValue+" "+ context.getResources().getString(R.string.years));


            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {

            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {

            }
        });




        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.9f);
        layoutParams.width = dialogWindowWidth;
        dialog.getWindow().setAttributes(layoutParams);
    }


    // when a match is build between user then this method is call and open the view if match screen
    public void openMatch(Match_Get_Set item){
        Match_F match_f = new Match_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putSerializable("data",item);
        match_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, match_f).commit();

    }


    public void openBoost(){
        Boost_F match_f = new Boost_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.MainMenuFragment, match_f).commit();

    }


    // when this screen open it will send the notification to other user that
    // both are like the each other and match will build between the users
    public void SendPushNotification(final String receverid, final String message){
        rootref.child("Users").child(receverid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String token=dataSnapshot.child("token").getValue().toString();
                    JSONObject notimap=new JSONObject();
                    try {
                        notimap.put("title",NewTabHome.user_name);
                        notimap.put("message",message);

                        String image=NewTabHome.user_pic;
                        if(!image.contains("http")){
                            image=Variables.image_base_url+NewTabHome.user_pic;
                        }
                        notimap.put("icon", image);
                        notimap.put("tokon",token);
                        notimap.put("senderid",NewTabHome.user_id);
                        notimap.put("receiverid", receverid);
                        notimap.put("action_type", "Like_Dislike");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ApiRequest.Call_Api(context,Variables.sendPushNotification, notimap,null);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


}
