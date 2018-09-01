package technicalhub.io.purshotam.adityahostelapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangeContactNumber extends AppCompatActivity {

    private EditText editTextField;
    private Button buttonFiled;
    private static final String urlSendOTP="https://technicalhub.io/service_connect/app/sendOtp.php";
    private static final String urlConfirmOTP="https://technicalhub.io/service_connect/app/confirmOtp.php";
    SharedPreferencesData sharedPreferencesData;
    ProgressBar progressBar;
    private String contactNo;
    AlertDialog.Builder builder;
    View view;
    TextView msg;
    Button btnOK;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact_number);
        sharedPreferencesData = new SharedPreferencesData(this);
        progressBar = findViewById(R.id.progressBar);
        editTextField=findViewById(R.id.editTextField);
        buttonFiled = findViewById(R.id.buttonFiled);
        buttonFiled.setText(R.string.change);
        editTextField.setHint("Enter your mobile number");
        builder = new AlertDialog.Builder(ChangeContactNumber.this);
        builder.setCancelable(false);
        view = getLayoutInflater().inflate(R.layout.custom_alert_dialog,null);
        msg = view.findViewById(R.id.txtViewCusAlertDlgMsg);
        btnOK = view.findViewById(R.id.btnCusAlertDlg);
        builder.setView(view);
        alertDialog = builder.create();
    }

    public void ChangeNumber(View view) {
        if(buttonFiled.getText().toString().equals("Change")) {
            if(editTextField.getText().toString().length()==10 || editTextField.getText().toString().length()==12 || editTextField.getText().toString().length()==13){
                if (sharedPreferencesData.GetContactNo().equals(editTextField.getText().toString())) {
                    msg.setText("No change found in mobile number");
                    alertDialog.show();
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });
                }
                else {
                    if (sharedPreferencesData.isNetworkAvailable()) {
                        contactNo = editTextField.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        editTextField.setVisibility(View.GONE);
                        buttonFiled.setVisibility(View.GONE);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, urlSendOTP,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressBar.setVisibility(View.GONE);
                                        editTextField.setVisibility(View.VISIBLE);
                                        buttonFiled.setVisibility(View.VISIBLE);
                                        switch (response) {
                                            case "SENT":
                                                Toast.makeText(ChangeContactNumber.this, "OTP sent to your new number", Toast.LENGTH_SHORT).show();
                                                editTextField.setText("");
                                                editTextField.setHint("Enter the OTP");
                                                buttonFiled.setText("Confirm OTP");
                                                break;
                                            case "NOTSENT":
                                                msg.setText("Unable to process your request.Please try again later");
                                                alertDialog.show();
                                                btnOK.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        alertDialog.cancel();
                                                    }
                                                });
                                                editTextField.setText("");
                                                editTextField.setHint("Enter the OTP");
                                                buttonFiled.setText("Confirm OTP");
                                                //Toast.makeText(ChangeContactNumber.this, "Please try again", Toast.LENGTH_LONG).show();
                                                break;
                                            default:
                                                Toast.makeText(ChangeContactNumber.this, "Something went wrong", Toast.LENGTH_LONG).show();
                                                break;
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressBar.setVisibility(View.GONE);
                                editTextField.setVisibility(View.VISIBLE);
                                buttonFiled.setVisibility(View.VISIBLE);
                                Toast.makeText(ChangeContactNumber.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> param = new HashMap<>();
                                param.put("mobNo", editTextField.getText().toString());
                                param.put("regNo", sharedPreferencesData.GetRegistrationNo());
                                return param;
                            }
                        };
                        MySingleton.getInstance(ChangeContactNumber.this).addToRequestQueue(stringRequest1);
                    } else {
                        Toast.makeText(ChangeContactNumber.this, "No network", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else {
                editTextField.setError("Invalid number");
            }

        }
        else if(buttonFiled.getText().toString().equals("Confirm OTP")) {
            if (sharedPreferencesData.isNetworkAvailable()) {
                progressBar.setVisibility(View.VISIBLE);
                editTextField.setVisibility(View.GONE);
                buttonFiled.setVisibility(View.GONE);
                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, urlConfirmOTP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Confirmed")) {
                                    progressBar.setVisibility(View.GONE);
                                    sharedPreferencesData.SaveContactNo(contactNo);
                                    Toast.makeText(ChangeContactNumber.this, "Number changed successfully", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ChangeContactNumber.this, HomePage.class));
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    msg.setText("You entered wrong OTP. Please check and verify");
                                    alertDialog.show();
                                    btnOK.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            alertDialog.cancel();
                                        }
                                    });
                                    progressBar.setVisibility(View.GONE);
                                    editTextField.setVisibility(View.VISIBLE);
                                    buttonFiled.setVisibility(View.VISIBLE);
                                    //Toast.makeText(ChangeContactNumber.this, "Wrong OTP", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        editTextField.setVisibility(View.VISIBLE);
                        buttonFiled.setVisibility(View.VISIBLE);
                        Toast.makeText(ChangeContactNumber.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("otp", editTextField.getText().toString());
                        param.put("regNo", sharedPreferencesData.GetRegistrationNo());
                        param.put("contactNo", contactNo);
                        return param;
                    }
                };
                MySingleton.getInstance(ChangeContactNumber.this).addToRequestQueue(stringRequest2);
            }
            else{
                Toast.makeText(ChangeContactNumber.this,"No network",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChangeContactNumber.this,HomePage.class));
        finish();
    }
}