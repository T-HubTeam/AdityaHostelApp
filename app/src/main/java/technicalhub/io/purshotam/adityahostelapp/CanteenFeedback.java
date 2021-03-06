package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CanteenFeedback extends AppCompatActivity {

    private static final String url = "https://technicalhub.io/canteen_feedback/feedbackCheck.php";
    ProgressDialog progressDialog;
    LinearLayout canteenlayout;
    SharedPreferencesData sharedPreferencesData;
    String mess,food;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen_feedback);
        sharedPreferencesData = new SharedPreferencesData(this);
        canteenlayout = findViewById(R.id.canteenlayout);
        Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat1=new SimpleDateFormat("HH");
        String hour=dateFormat1.format(calendar.getTime());
        int Hour=Integer.parseInt(hour);
        DateFormat dateFormat2=new SimpleDateFormat("mm");
        String min=dateFormat2.format(calendar.getTime());
        int Min=Integer.parseInt(min);
        int Time = Hour*60+Min;
        progressDialog = new ProgressDialog(this);
        if(sharedPreferencesData.GetMess().equals("North")){
            mess = "n";
        }
        else if(sharedPreferencesData.GetMess().equals("South")){
            mess = "s";
        }
        //Previous timing 7:30 to 9:00
        //Change 2 BBK 7:30 AM to 8:30 AM
        if(Time >= (7*60+30) && Time <= (8*60+30)){
            food = mess+"Breakfast";
        }
        //Previous timing 12:00 to 2:00
        //Change 2 BBK 12:00 PM to 1:30 PM
        else if(Time >= (12*60) && Time <= (13*60+30)){
            food = mess+"Lunch";
        }
        //Previous timing 4:30 to 6
        //Change 2 BBK 4:30 AM to 5:30 AM
        else if(Time >= (16*60+30) && Time <= (17*60+30)){
            food = mess+"EveningSnacks";
        }
        //Previous timing 7:00 to 9:00
        //Change 2 BBK 7:00 PM to 8:00 PM
        else if(Time >= (19*60) && Time <= (20*60)){
            food = mess+"Dinner";
        }
        else{
            food = "None";
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final SharedPreferencesData sharedPreferencesData=new SharedPreferencesData(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.cancel();
                        int time = Integer.parseInt(response);
                        if(time == 12345){
                            canteenlayout.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"You have already submitted the feedback...",Toast.LENGTH_SHORT).show();
                        }else if(time == 54321){
                            canteenlayout.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Sorry !! please check the feedback timings",Toast.LENGTH_SHORT).show();
                        }
                        else if(time >= (7*60+30) && time <=(20*60))
                        {
                            if(time >= (7*60+30) && time <= (8*60+30)){
                                //Breakfast Intent
                                switch (sharedPreferencesData.GetMess()){
                                    case "North":
                                        startActivity(new Intent(getApplicationContext(),NorthBreakfast.class));
                                        finish();
                                        break;
                                    case "South":
                                        startActivity(new Intent(getApplicationContext(),SouthBreakfast.class));
                                        finish();
                                        break;
                                }
                            }
                            else if(time >= (12*60) && time <= (13*60+30)){
                                //Lunch Intent
                                switch (sharedPreferencesData.GetMess()){
                                    case "North":
                                        startActivity(new Intent(getApplicationContext(),NorthLunch.class));
                                        finish();
                                        break;
                                    case "South":
                                        startActivity(new Intent(getApplicationContext(),SouthLunch.class));
                                        finish();
                                        break;
                                }
                            }else if(time >= (16*60+30) && time <= (17*60+30)){
                                //Evening Snacks
                                switch (sharedPreferencesData.GetMess()){
                                    case "North":
                                        startActivity(new Intent(getApplicationContext(),NorthEveningSnacks.class));
                                        finish();
                                        break;
                                    case "South":
                                        startActivity(new Intent(getApplicationContext(),SouthEveningSnacks.class));
                                        finish();
                                        break;
                                }
                            }else if(time >= (19*60) && time <= (20*60)){
                                //Dinner Intent
                                switch (sharedPreferencesData.GetMess()){
                                    case "North":
                                        startActivity(new Intent(getApplicationContext(),NorthDinner.class));
                                        finish();
                                        break;
                                    case "South":
                                        startActivity(new Intent(getApplicationContext(),SouthDinner.class));
                                        finish();
                                        break;
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                canteenlayout.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("regNo",sharedPreferencesData.GetRegistrationNo());
                param.put("food",food);
                return param;
            }
        };
        MySingleton.getInstance(CanteenFeedback.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CanteenFeedback.this,HomePage.class));
        finish();
    }
}