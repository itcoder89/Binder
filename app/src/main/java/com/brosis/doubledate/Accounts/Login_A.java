package com.brosis.doubledate.Accounts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.brosis.doubledate.CodeClasses.ApiRequest;
import com.brosis.doubledate.CodeClasses.Callback;
import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Login_A extends AppCompatActivity {


    private static final String TAG = Login_A.class.getSimpleName();

    DatabaseReference rootref;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    SharedPreferences sharedPreferences;

    RelativeLayout gmail_login_layout,phone_login_layout;

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private LoginButton login_button;
    private ImageView fb;
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        login_button = (LoginButton) findViewById(R.id.login_button);
        fb = (ImageView) findViewById(R.id.fb);

        rootref= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        // if the user is already login trought facebook then we will logout the user automatically
        //LoginManager.getInstance().logOut();
        sharedPreferences=getSharedPreferences(Variables.pref_name,MODE_PRIVATE);

        init();

        //printKeyHash();

        getPackageHash();
        login_button.setReadPermissions(Arrays.asList(EMAIL));
        //    loginButton.setReadPermissions("public_profile", "email", "user_friends");
        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    private void getPackageHash() {
        try {

            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    "com.brosis.doubledate",//give your package name here
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e(TAG, "Hash  : " + Base64.encodeToString(md.digest(), Base64.NO_WRAP));//Key hash is printing in Log
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }

    }



    private  ViewPager mPager;
    private void init() {

        // here is the code of slider which is move in login screen
        // in Account folder the Sliding_Adapter is also belong to this code

        mPager = (ViewPager) findViewById(R.id.pager);

        mPager.setAdapter(new Sliding_adapter(Login_A.this));

        TabLayout indicator = (TabLayout) findViewById(R.id.indicator);
        indicator.setupWithViewPager(mPager, true);

        indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        gmail_login_layout=findViewById(R.id.gmail_login_layout);
        gmail_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign_in_with_gmail();
            }
        });


        // this is the login with phone btn and by press this button we will open the Login_phone_A Activity
        phone_login_layout=findViewById(R.id.phone_login_layout);
        phone_login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login_A.this, Login_Phone_A.class));
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }
        });




    }




    public void Login(View view) {

        /*
         if have an permision from facebook developer console to get the user_brithday
         and user_gender then you will use the below line
        */
    /*
        LoginManager.getInstance().
         logInWithReadPermissions(Login_A.this,
                 Arrays.asList("public_profile","email","user_birthday","user_gender"));

    */

        /*othetwise you will use the below line*/

        /*LoginManager.getInstance()
                .logInWithReadPermissions(Login_A.this,
                        Arrays.asList("public_profile","email"));*/



        //Loginwith_FB();
    }

    ///private CallbackManager mCallbackManager;
    //facebook implimentation
    public void Loginwith_FB(){

        // initialze the facebook sdk and request to facebook for login
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        /*mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()  {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(Login_A.this, "Login Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("resp",""+error.toString());
                Toast.makeText(Login_A.this, "Login Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }

        });*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else {
            //if(callbackManager!=null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }



    private void handleFacebookAccessToken(final AccessToken token) {
        // if user is login then this method will call and
        // facebook will return us a token which will user for get the info of user

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Functions.Show_loader(Login_A.this,false,false);

                            final FirebaseUser user_firebase = mAuth.getCurrentUser();
                            final String firebase_userid=user_firebase.getUid();
                            final String id = Profile.getCurrentProfile().getId();
                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                                    Functions.cancel_loader();

                                    Log.d("resp",user.toString());
                                    //after get the info of user we will pass to function which will store the info in our server
                                    Change_Url_to_base64(""+id,""+user.optString("first_name")
                                    ,""+user.optString("last_name"),""+user.optString("birthday")
                                            ,""+user.optString("gender"),
                                    "https://graph.facebook.com/"+id+"/picture?width=500&width=500");

                                }
                            });

                            // here is the request to facebook sdk for which type of info we have required
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "last_name,first_name,email,birthday,gender");
                            request.setParameters(parameters);
                            request.executeAsync();
                        } else {

                            Toast.makeText(Login_A.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }




    //google Implimentation
    GoogleSignInClient mGoogleSignInClient;
    public void Sign_in_with_gmail(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(Login_A.this);
        if (account != null) {
            String id=account.getId();
            String f_name=account.getGivenName();
            String l_name=account.getFamilyName();

            if(account.getPhotoUrl()!=null) {
               String pic_url = account.getPhotoUrl().toString();
                Change_Url_to_base64(id,f_name,l_name,"","" ,pic_url);
            }
            else {
               Get_User_info(id,f_name,l_name);
            }

        }
        else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 123);
        }

    }

    //Relate to google login
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String id=account.getId();
                String f_name=account.getGivenName();
                String l_name=account.getFamilyName();

                // if we do not get the picture of user then we will use default profile picture

                if(account.getPhotoUrl()!=null) {
                    String pic_url = account.getPhotoUrl().toString();
                    Change_Url_to_base64(id,f_name, l_name,"","" ,pic_url);
                }else {

                    Get_User_info(id,f_name,l_name);
                }

                
            }
        } catch (ApiException e) {
            Log.w("Error message", "signInResult:failed code=" + e.getStatusCode());
        }

    }


    public void Change_Url_to_base64(final String user_id,
                                     final String f_name,final String l_name,
                                     final String birthday,final String gender,String picture){

        Functions.Show_loader(this,false,true);

        Picasso.with(this).load(picture).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String image_base64=Functions.Bitmap_to_base64(Login_A.this,bitmap);
                Call_Api_For_Signup( user_id, f_name, l_name, birthday, gender,image_base64);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(Login_A.this, errorDrawable.toString(), Toast.LENGTH_SHORT).show();
                Functions.cancel_loader();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    public void Change_Url_to_base64_fb(final String user_id,
                                     final String name,final String l_name,
                                     final String birthday,final String gender,String picture){

        Functions.Show_loader(this,false,true);

        Picasso.with(this).load(picture).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String image_base64=Functions.Bitmap_to_base64(Login_A.this,bitmap);
                Call_Api_For_Signup( user_id, name, l_name, birthday, gender,image_base64);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Toast.makeText(Login_A.this, errorDrawable.toString(), Toast.LENGTH_SHORT).show();
                Functions.cancel_loader();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }


    // this method will store the info of user to  database
    private void Call_Api_For_Signup(String user_id,
                                     String f_name,String l_name,
                                     String birthday,String gender,String picture) {
        //String x = "Stephen Ricard";
        String[] nameparts = f_name.split(" ");
        //String initials = nameparts[0].charAt(0)+nameparts[1].charAt(0);

        //f_name=f_name.replaceAll("\\W+","");
        //l_name=l_name.replaceAll("\\W+","");

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", user_id);
            parameters.put("first_name",nameparts[0]);
            if(nameparts[0] != null){
                parameters.put("last_name", nameparts[1]);
            }else{
                parameters.put("last_name", l_name);
            }
            parameters.put("birthday", birthday);
            parameters.put("gender", gender);

            JSONObject file_data=new JSONObject();
            file_data.put("file_data",picture);
            parameters.put("image",file_data);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Functions.generateNoteOnSD("parameters_signup",parameters.toString());

        ApiRequest.Call_Api(this, Variables.SignUp, parameters, new Callback() {
            @Override
            public void Responce(String resp) {

               Functions.cancel_loader();
                Parse_signup_data(resp);
            }
        });


    }


    private void Get_User_info(final String user_id,final String f_name,final String l_name) {

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Functions.Show_loader(this,false,false);
        ApiRequest.Call_Api(this, Variables.getUserInfo, parameters, new Callback() {
            @Override
            public void Responce(String resp) {

                Functions.cancel_loader();
                try {
                    JSONObject jsonObject=new JSONObject(resp);
                    String code=jsonObject.optString("code");
                    if(code.equals("200")){

                        // if user is already logedin then we will save the user data and go to the enable location screen
                        JSONArray msg=jsonObject.getJSONArray("msg");
                        JSONObject userdata=msg.getJSONObject(0);

                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(Variables.uid,user_id);
                        editor.putString(Variables.f_name,userdata.optString("first_name"));
                        editor.putString(Variables.l_name,userdata.optString("last_name"));
                        editor.putString(Variables.birth_day,userdata.optString("age"));
                        editor.putString(Variables.gender,userdata.optString("gender"));
                        editor.putString(Variables.u_pic,userdata.optString("image1"));
                        editor.putBoolean(Variables.islogin,true);
                        editor.commit();

                        // after all things done we will move the user to enable location screen
                        enable_location();


                    }else {
                        // if user is first time login then we will get the usser picture and name
                        Intent intent=new Intent(Login_A.this, Get_User_Info_A.class);
                        intent.putExtra("id",user_id);
                        intent.putExtra("fname",f_name);
                        intent.putExtra("lname",l_name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        finish();

                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    public void Parse_signup_data(String loginData){

        try {
            JSONObject jsonObject=new JSONObject(loginData);
            String code=jsonObject.optString("code");
            if(code.equals("200")){
            JSONArray jsonArray=jsonObject.getJSONArray("msg");
                JSONObject userdata = jsonArray.getJSONObject(0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Variables.uid,userdata.optString("fb_id"));
                editor.putString(Variables.f_name,userdata.optString("first_name"));
                editor.putString(Variables.l_name,userdata.optString("last_name"));
                editor.putString(Variables.birth_day,userdata.optString("age"));
                editor.putString(Variables.gender,userdata.optString("gender"));
                editor.putString(Variables.u_pic,userdata.optString("image1"));
                editor.putBoolean(Variables.islogin,true);
                editor.commit();

                // after all things done we will move the user to enable location screen
                enable_location();
            }else {
                Toast.makeText(this, ""+jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

        }
        catch (JSONException e) {
           Functions.cancel_loader();
            e.printStackTrace();
        }

    }



    private void enable_location() {

        startActivity(new Intent(this, Enable_location_A.class));
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finishAffinity();
    }



    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName() , PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("keyhash" , Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    public void fbsignin(View view) {
        if (view == fb) {
            try {
                boolean checkfblogin = isLoggedIn();
                Log.e("checkAlreadyLogin", "" + checkfblogin);
                if (checkfblogin == true) {
                    Log.e("signOut", "fb");
                    LoginManager.getInstance().logOut();
                    login_button.performClick();
                    //startActivity(new Intent(NewSignUp.this, DietaryRestrictions.class));
                } else {
                    login_button.performClick();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                          Log.e("WebResponse", ":" + response);
                        try {
                            // tvAppId.setText("App id:"+WebResponse.getJSONObject().getString("id"));
                            //  tvName.setText("name:"+WebResponse.getJSONObject().getString("name"));
                            //    tvEmail.setText("email:"+WebResponse.getJSONObject().getString("email"));
                            //  tvDOB.setText("gender:"+ WebResponse.getJSONObject().getString("gender"));
                            //Log.e("GraphResponse", "img-url:" + "http://graph.facebook.com/" + WebResponse.getJSONObject().getString("id") + "/picture?type=large");
                            String image_url = "http://graph.facebook.com/" + response.getJSONObject().getString("id") + "/picture?type=large";
                            //http://graph.facebook.com/1887547564602481/picture?type=large
                            Log.e("fblogin", "image_url:" + image_url);

                            Functions.cancel_loader();

                           // Log.d("resp",user.toString());
                            //after get the info of user we will pass to function which will store the info in our server
                           /* Change_Url_to_base64(""+response.getJSONObject().getString("id"),""+response.getJSONObject().getString("first_name")
                                    ,""+response.getJSONObject().getString("last_name"),""+response.getJSONObject().getString("birthday")
                                    ,""+response.getJSONObject().getString("gender"),
                                    "https://graph.facebook.com/"+response.getJSONObject().getString("id")+"/picture?width=500&width=500");*/


                            Change_Url_to_base64_fb(""+response.getJSONObject().getString("id"),
                                    ""+response.getJSONObject().getString("name")
                                    ,"",
                                    "",
                                    "",
                                    "https://graph.facebook.com/"+response.getJSONObject().getString("id")+"/picture?width=500&width=500");

                            // {"id":"3591901744167046","name":"Sanjay Chourasia","email":"sanjaychourasia1989@gmail.com",
                            // "picture":{"data":{"height":50,"is_silhouette":false,
                            // "url":"https:\/\/platform-lookaside.fbsbx.com\/platform\/profilepic\/?asid=3591901744167046&height=50&width=50&ext=1602858000&hash=AeTJ_eqtsqJ6XICm","width":50}}}
                            // , error: null}

                            /*faceBookLoginAPI(response.getJSONObject().getString("id")
                                    , response.getJSONObject().getString("name"),
                                    response.getJSONObject().getString("email")
                                    , image_url);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


}