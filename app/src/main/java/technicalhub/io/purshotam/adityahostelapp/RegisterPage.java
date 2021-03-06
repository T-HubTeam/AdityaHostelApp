package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
This page is used to verify either the student who is trying to login is hostler or not ....
If the student is hostler then go to login page
*/
public class RegisterPage extends AppCompatActivity {

    private EditText registrationNo;
    static final String urlString="https://technicalhub.io/service_connect/app/RequestPassword.php";
    private SharedPreferencesData sharedPreferencesData;
    private int TrialCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        registrationNo=findViewById(R.id.editText_registartionNo);
        sharedPreferencesData=new SharedPreferencesData(RegisterPage.this);
        Button button = findViewById(R.id.button_register);
        String buttonText=getIntent().getStringExtra("ButtonText");
        button.setText(buttonText);
    }


    /*
    This method is called when the register button is pressed
     */
    public void Register(View view) {
        if(!registrationNo.getText().toString().equals("")){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading Please Wait...");
            progressDialog.show();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage.this);
                            builder.setCancelable(false);
                            View view = getLayoutInflater().inflate(R.layout.custom_alert_dialog,null);
                            TextView alertTxt = view.findViewById(R.id.txtViewCusAlertDlg);
                            TextView msg = view.findViewById(R.id.txtViewCusAlertDlgMsg);
                            Button btnOK = view.findViewById(R.id.btnCusAlertDlg);
                            builder.setView(view);
                            final AlertDialog dialog = builder.create();
                            String mobile="";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                                JSONObject statusObject = jsonArray.getJSONObject(0);
                                String status = statusObject.getString("status");
                                if (status.equals("TRUE")){
                                    JSONObject mobileObject = jsonArray.getJSONObject(1);
                                    mobile = mobileObject.getString("mobile");
                                }
                                switch (status){
                                    case "TRUE":
                                        alertTxt.setText("Alert");
                                        dialog.show();
                                        if(!mobile.equals("Mobile number not found")){
                                            String first = mobile.substring(0,2);
                                            String last = mobile.substring(7,10);
                                            sharedPreferencesData.SaveInstallation(true);
                                            sharedPreferencesData.SaveBlocked(false);
                                            msg.setText("Temporary password sent to "+first+"*****"+last);
                                            btnOK.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.cancel();
                                                    startActivity(new Intent(RegisterPage.this,LoginPage.class));
                                                    finish();
                                                }
                                            });
                                        }
                                        else{
                                            msg.setText(mobile);
                                            btnOK.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.cancel();
                                                }
                                            });
                                        }
                                        //Toast.makeText(RegisterPage.this,"OTP sent to your official EmailID",Toast.LENGTH_SHORT).show();
                                        break;
                                    case "User not found":
                                        msg.setText("Sorry !!! User not found");
                                        dialog.show();
                                        btnOK.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.cancel();
                                            }
                                        });
                                        //Toast.makeText(RegisterPage.this,"",Toast.LENGTH_SHORT).show();
                                        //Trialcount is taken to get record of how many times a invalid-attempts are made
                                        TrialCount++;
                                        if(TrialCount>=3){
                                            msg.setText("Number of trails exceeded and you have no more access to use this app...");
                                            dialog.show();
                                            btnOK.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    dialog.cancel();
                                                }
                                            });
                                            //Toast.makeText(RegisterPage.this,"You have no access to use this app...",Toast.LENGTH_LONG).show();
                                            sharedPreferencesData.SaveBlocked(true);
                                        }
                                        break;
                                    default:
                                        msg.setText("Unable to process your request. Try again Later.");
                                        dialog.show();
                                        btnOK.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.cancel();
                                            }
                                        });
                                        //Toast.makeText(RegisterPage.this,"Unable to process your request. Try again Later.",Toast.LENGTH_LONG).show();
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterPage.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    param.put("regNo",registrationNo.getText().toString());
                    param.put("emailid",sharedPreferencesData.GetEmailId());
                    return param;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(RegisterPage.this).addToRequestQueue(stringRequest);
        }
        else{
            registrationNo.setError("Enter the registration number");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterPage.this,LoginPage.class));
        finish();
    }
}














/*
btn=findViewById(R.id.btn);
        textView=findViewById(R.id.txt);
        calendar=Calendar.getInstance();
        dateFormat=new SimpleDateFormat("HH:mm");
        date=dateFormat.format(calendar.getTime());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(date);
            }
        });

 */

/*
StringRequest request=new StringRequest(Request.Method.POST,urlString, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if(response.equals("TRUE")){
                        startActivity(new Intent(RegisterPage.this,LoginPage.class));
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterPage.this,"User not found",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterPage.this, ""+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("regNo",registrationNo.getText().toString());
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(600000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(request);
 */

//Working properly
/*
StringRequest stringRequest=new StringRequest(Request.Method.POST, urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                progressDialog.dismiss();
                                JSONObject jsonObject=new JSONObject(response);
                                String s=jsonObject.getString("stat");
                                if(s.equals("TRUE")){
                                    String name=jsonObject.getString("name");
                                    String email=jsonObject.getString("email");
                                    String pass=jsonObject.getString("password");
                                    Toast.makeText(getApplicationContext(),"name = "+name+"  email = "+email+"  pass = "+pass,Toast.LENGTH_SHORT).show();
                                }
                                else if(s.equals("FALSE")){
                                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("regNo",registrationNo.getText().toString());
                    return params;
                }
            };

 */

/*
 JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, urlString, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                progressDialog.dismiss();
                                String s=response.getString("stat");
                                if(s.equals("TRUE")){
                                    String name=response.getString("name");
                                    String email=response.getString("email");
                                    String pass=response.getString("password");
                                    Toast.makeText(getApplicationContext(),"name = "+name+"  email = "+email+"  pass = "+pass,Toast.LENGTH_SHORT).show();
                                }
                                else if(s.equals("FALSE")){
                                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                progressDialog.dismiss();
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Error : "+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("regNo",registrationNo.getText().toString());
                    return params;
                }
            };

 */