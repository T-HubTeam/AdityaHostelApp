package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    private EditText old_password,new_password,confirm_password;
    private static final String urlChangePassword = "https://technicalhub.io/service_connect/app/ChangePassword.php";
    private SharedPreferencesData sharedPreferencesData;
    private TextView show_old_password,show_new_password,show_confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        old_password=findViewById(R.id.old_password);
        new_password=findViewById(R.id.new_password);
        confirm_password=findViewById(R.id.confirm_password);
        sharedPreferencesData=new SharedPreferencesData(ChangePassword.this);
        show_old_password=findViewById(R.id.show_old_password);
        show_new_password=findViewById(R.id.show_new_password);
        show_confirm_password=findViewById(R.id.show_confirm_password);
        old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        old_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(old_password.getText().toString().length()>0){
                    show_old_password.setVisibility(View.VISIBLE);
                }
                else{
                    show_old_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        show_old_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_old_password.getText().toString().equals("SHOW")){
                    show_old_password.setText("HIDE");
                    old_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    old_password.setSelection(old_password.getText().toString().length());
                }
                else{
                    show_old_password.setText("SHOW");
                    old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    old_password.setSelection(old_password.getText().toString().length());
                }
            }
        });
        new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(new_password.getText().toString().length()>0){
                    show_new_password.setVisibility(View.VISIBLE);
                }
                else{
                    show_new_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        show_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_new_password.getText().toString().equals("SHOW")){
                    show_new_password.setText("HIDE");
                    new_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    new_password.setSelection(new_password.getText().toString().length());
                }
                else{
                    show_new_password.setText("SHOW");
                    new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    new_password.setSelection(new_password.getText().toString().length());
                }
            }
        });
        confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(confirm_password.getText().toString().length()>0){
                    show_confirm_password.setVisibility(View.VISIBLE);
                }
                else{
                    show_confirm_password.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        show_confirm_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show_confirm_password.getText().toString().equals("SHOW")){
                    show_confirm_password.setText("HIDE");
                    confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirm_password.setSelection(confirm_password.getText().toString().length());
                }
                else{
                    show_confirm_password.setText("SHOW");
                    confirm_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirm_password.setSelection(confirm_password.getText().toString().length());
                }
            }
        });
    }

    public void changePassword(View view) {
        if (old_password.getText().toString().equals("") || new_password.getText().toString().equals("") || confirm_password.getText().toString().equals("")) {
            if (old_password.getText().toString().equals("")) {
                old_password.setError("Enter the old password");
            }
            if (new_password.getText().toString().equals("")) {
                new_password.setError("Enter the new password");
            }
            if (confirm_password.getText().toString().equals("")) {
                confirm_password.setError("Confirm your password");
            }
        } else if (sharedPreferencesData.isNetworkAvailable()) {
            if (new_password.getText().toString().equals(confirm_password.getText().toString())) {
                final ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlChangePassword,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                switch (response) {
                                    case "TRUE":
                                        Toast.makeText(ChangePassword.this, "Password changed successfully...", Toast.LENGTH_LONG).show();
                                        sharedPreferencesData.SaveLoggedIn(false);
                                        sharedPreferencesData.SaveProfileImage("none");
                                        startActivity(new Intent(ChangePassword.this, LoginPage.class));
                                        finish();
                                        break;
                                    case "FALSE":
                                        Toast.makeText(ChangePassword.this, "Unable to process your request", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "WrongPassword":
                                        Toast.makeText(ChangePassword.this, "Sorry the password you entered is wrong", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(ChangePassword.this, response, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ChangePassword.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("regNo", sharedPreferencesData.GetRegistrationNo());
                        param.put("oldPass", old_password.getText().toString());
                        param.put("newPass", new_password.getText().toString());
                        return param;
                    }
                };
                MySingleton.getInstance(ChangePassword.this).addToRequestQueue(stringRequest);
            } else {
                Toast.makeText(ChangePassword.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                new_password.setText("");
                confirm_password.setText("");
            }
        } else {
            Toast.makeText(ChangePassword.this, "No network", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChangePassword.this,HomePage.class));
        finish();
    }
}
