package com.work.unknown.absence;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import java.util.HashMap;
import java.util.Map;
import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import com.work.unknown.absence.Admin.tasksactivity;
import com.work.unknown.absence.Student.currentsesstion;
import com.work.unknown.absence.Tutor.SessionOptions;

public class Login extends AppCompatActivity {
    FloatingActionButton share;
    public static final String MyPREFERENCES = "logdata" ;
    EditText Email,Password;
    android.app.AlertDialog dialog;
    String unique_id;
    String Request_url="";
    final String user_email="email";
    final String user_password="password";
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                .setDefaultFontPath("Sail-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_login);
        sharedPreference =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
        Email=findViewById(R.id.LoginuserName);
        Password=findViewById(R.id.Loginpassword);
        dialog=new SpotsDialog(this);
         share=findViewById(R.id.fab_1);
         share.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                 sharingIntent.setType("text/plain");
                 sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Attendance(Open it in Google Play Store to Download the Application)");
                 sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"shareAPP");
                 startActivity(Intent.createChooser(sharingIntent,"Share via"));
             }
         });
      }
    public void Loginprocess(View view)
    {
        if(Email.getText().toString().contains("@tutor"))
        {
          Request_url="http://192.168.43.21/attendance_app/public/attendance/tutor/login";

        }else if(Email.getText().toString().contains("@admin"))
        {
            Request_url="http://192.168.43.21/attendance_app/public/attendance/admin/login";
        }
          if(!Email.getText().toString().contains("@tutor")&&!Email.getText().toString().contains("@admin")){
            Request_url="http://192.168.43.21/attendance_app/public/attendance/student/login";
            unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            final Intent i;
            if(check()) {
                /////////////
//                i=new Intent(Login.this,currentsesstion.class);
//                startActivity(i);
//                /////
                dialog.show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Request_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.i("res", response);
                        if(response.contains("successfully")){
                            alerter(response,R.color.RippleEffectcolor);
                             editor.putString("Email",Email.getText().toString());
                             editor.putString("deviceID",unique_id);
                             editor.commit();
                                Intent i;
                                i=new Intent(Login.this,currentsesstion.class);
                                startActivity(i);
                        }
                        else
                        {

                            alerter(response,R.color.darkRed);


                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.i("dars", ""+error.getMessage());
                      alerter(error.getMessage(),R.color.darkRed);

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>values=new HashMap<String,String>();
                        values.put(" national_id",Password.getText().toString());
                        values.put("seating_number",Email.getText().toString());
                        values.put("device_id",unique_id);
                        return values;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }
        else
         {
                login();
         }
    }
    public boolean check()
    {
        if(TextUtils.isEmpty(Email.getText().toString()))
        {
            return false;
        }
        else if(TextUtils.isEmpty(Password.getText().toString()))
        {
            return false;
        }
        return true;
    }
    public void alerter(String v, int c)
    {
        Alerter.create(Login.this)
                .setTitle("alert")
                .setText(v)
                .setBackgroundColorRes(c)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();
    }
    public void login()
    {
        if(check()) {
//            if(Email.getText().toString().contains("@tutor"))
//            {
//                Intent i=new Intent(Login.this,SessionOptions.class);
//                startActivity(i);
//            }
//            else if(Email.getText().toString().contains("@admin"))
//            {
//                Intent i=new Intent(Login.this,tasksactivity.class);
//                startActivity(i);
//            }
            dialog.show();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Request_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    if(response.contains("successfully")){
                        alerter(response,R.color.RippleEffectcolor);
                        if(Email.getText().toString().contains("@tutor"))
                        {
                             Intent i=new Intent(Login.this,SessionOptions.class);
                             startActivity(i);
                        }
                        else if(Email.getText().toString().contains("@admin"))
                        {
                              Intent i=new Intent(Login.this,tasksactivity.class);
                              startActivity(i);
                        }
                    }else

                    {
                        alerter(response,R.color.darkRed);
                        Log.i("ress", response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Log.i("dars", ""+error);
                    alerter(error.getMessage(),R.color.darkRed);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String>values=new HashMap<String,String>();
                    values.put(user_password,Password.getText().toString());
                    values.put(user_email,Email.getText().toString());
                    return values;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
