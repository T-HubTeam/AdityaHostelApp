package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    private EditText et_regNo,et_password;
    private CheckBox checkBox_login;
    public static final String login_url="https://technicalhub.io/service_connect/app/Login.php";
    private SharedPreferencesData sharedPreferencesData;
    private TextView show_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        et_password=findViewById(R.id.password);
        et_regNo=findViewById(R.id.reg_no);
        checkBox_login=findViewById(R.id.stay_logged_in);
        show_password=findViewById(R.id.show_password);
        sharedPreferencesData=new SharedPreferencesData(this);
        if(sharedPreferencesData.IsBlocked()){
            Toast.makeText(getApplicationContext(),"Sorry !!! You are blocked...",Toast.LENGTH_LONG).show();
            Intent exitt=new Intent(Intent.ACTION_MAIN);
            exitt.addCategory(Intent.CATEGORY_HOME);
            exitt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exitt);
            System.exit(0);
            finish();
        }
        if(sharedPreferencesData.IsLoggedIn()){
            startActivity(new Intent(LoginPage.this,HomePage.class));
            finish();
        }
        et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_password.getText().toString().length()>0){
                    show_password.setVisibility(View.VISIBLE);
                }
                else{
                    show_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_password.getText().toString().equals("SHOW")){
                    show_password.setText("HIDE");
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.setSelection(et_password.getText().toString().length());
                }
                else{
                    show_password.setText("SHOW");
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.setSelection(et_password.getText().toString().length());
                }
            }
        });
    }

    public void Login(View view) {
        if(et_regNo.getText().toString().equals("")||et_password.getText().toString().equals("")){
            if(et_regNo.getText().toString().equals("")){
                et_regNo.setError("Enter the Registration number");
            }
            if(et_password.getText().toString().equals("")){
                et_password.setError("Enter the password");
            }
        }
        else {
            if (sharedPreferencesData.isNetworkAvailable()) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                                    builder.setCancelable(false);
                                    View view = getLayoutInflater().inflate(R.layout.custom_alert_dialog,null);
                                    TextView msg = view.findViewById(R.id.txtViewCusAlertDlgMsg);
                                    Button btnOk = view.findViewById(R.id.btnCusAlertDlg);
                                    builder.setView(view);
                                    final AlertDialog alertDialog = builder.create();
                                    switch (jsonObject.getString("status")) {
                                        case "TRUE":
                                            sharedPreferencesData.SaveName(jsonObject.getString("name"));
                                            sharedPreferencesData.SaveRegistrationNo(jsonObject.getString("regNo"));
                                            sharedPreferencesData.SaveRoom(jsonObject.getString("roomNo"));
                                            sharedPreferencesData.SaveGender(jsonObject.getString("gender"));
                                            sharedPreferencesData.SaveEmailId(jsonObject.getString("email"));
                                            if (jsonObject.getString("mess").equals("S")) {
                                                sharedPreferencesData.SaveMess("South");
                                            } else if (jsonObject.getString("mess").equals("N")) {
                                                sharedPreferencesData.SaveMess("North");
                                            } else {
                                                sharedPreferencesData.SaveMess("Not found");
                                            }
                                            sharedPreferencesData.SaveContactNo(jsonObject.getString("contactNo"));
                                            if (checkBox_login.isChecked()) {
                                                sharedPreferencesData.SaveLoggedIn(true);
                                            }
                                            startActivity(new Intent(LoginPage.this, HomePage.class));
                                            finish();
                                            break;
                                        case "UserNotFound":
                                            msg.setText("User not found");
                                            alertDialog.show();
                                            btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    alertDialog.cancel();
                                                }
                                            });
                                            //Toast.makeText(LoginPage.this, "User not found", Toast.LENGTH_SHORT).show();
                                            et_regNo.setText("");
                                            break;
                                        case "WrongPassword":
                                            msg.setText("Wrong password");
                                            alertDialog.show();
                                            btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    alertDialog.cancel();
                                                }
                                            });
                                            //Toast.makeText(LoginPage.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                            et_password.setText("");
                                            break;
                                        default:
                                            msg.setText("Wrong password");
                                            alertDialog.show();
                                            btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    alertDialog.cancel();
                                                }
                                            });
                                            //Toast.makeText(LoginPage.this, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginPage.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("regNo", et_regNo.getText().toString());
                        param.put("pass", et_password.getText().toString());
                        return param;
                    }
                };
                MySingleton.getInstance(LoginPage.this).addToRequestQueue(stringRequest);
            }
            else{
                Toast.makeText(LoginPage.this,"No network",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ForgotPassword(View view) {
        if(sharedPreferencesData.isNetworkAvailable()) {
            Intent toRegister = new Intent(LoginPage.this, RegisterPage.class);
            toRegister.putExtra("ButtonText", "Request Password");
            startActivity(toRegister);
            //finish();
        }
        else{
            Toast.makeText(LoginPage.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void NewUser(View view) {
        if(sharedPreferencesData.isNetworkAvailable()) {
            Intent toRegister = new Intent(LoginPage.this, RegisterPage.class);
            toRegister.putExtra("ButtonText", "Register");
            startActivity(toRegister);
            //finish();
        }
        else{
            Toast.makeText(LoginPage.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(LoginPage.this);
        alertDialog.setMessage("Are you sure you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginPage.super.onBackPressed();
                        Intent exitt=new Intent(Intent.ACTION_MAIN);
                        exitt.addCategory(Intent.CATEGORY_HOME);
                        exitt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exitt);
                        System.exit(0);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}