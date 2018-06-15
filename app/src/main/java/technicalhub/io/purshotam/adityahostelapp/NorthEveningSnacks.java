package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NorthEveningSnacks extends AppCompatActivity {

    RadioGroup rg1,rg2;
    RadioButton rb1,rb2;
    EditText editTextRemarks1,editTextRemarks2;
    EditText editTextItemName1,editTextItemName2;
    String rb1String1 = "None",rb2String2 = "None";
    String editTextRemarks1String1,editTextRemarks2String2;
    String editTextItemName1String1,editTextItemName2String2;
    String dateString,editTextRollNumberStringEveningSnacks;
    Button buttonNorthEveningSnacks;
    int count = 1;
    int selectedRadioButtonId1,selectedRadioButtonId2;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    private static final String urlNorthEveningSnacks="https://technicalhub.io/canteen_feedback/insertnorthcanteensnacks.php";
    private SharedPreferencesData sharedPreferencesData;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north_evening_snacks);
        progressDialog = new ProgressDialog(NorthEveningSnacks.this);
        progressDialog.setMessage("Please wait...");
        sharedPreferencesData=new SharedPreferencesData(NorthEveningSnacks.this);
        rg1 = findViewById(R.id.rg17);
        rg2 = findViewById(R.id.rg18);
        editTextRemarks1 = findViewById(R.id.editTextRemarks16);
        editTextRemarks2 = findViewById(R.id.editTextRemarks17);
        editTextItemName1 = findViewById(R.id.editTextItemName10);
        editTextItemName2 = findViewById(R.id.editTextItemName11);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NorthEveningSnacks.this,HomePage.class));
        finish();
    }

    public void NorthEveningSnacksMethod(View view) {
        if (sharedPreferencesData.isNetworkAvailable()) {
            selectedRadioButtonId1 = rg1.getCheckedRadioButtonId();
            selectedRadioButtonId2 = rg2.getCheckedRadioButtonId();
            if (selectedRadioButtonId1 != -1) {
                rb1 = findViewById(selectedRadioButtonId1);
                rb1String1 = rb1.getText().toString();
            }
            if (selectedRadioButtonId2 != -1) {
                rb2 = findViewById(selectedRadioButtonId2);
                rb2String2 = rb2.getText().toString();
            }
            if (rg1.getCheckedRadioButtonId() <= 0 && rg2.getCheckedRadioButtonId() <= 0) {
                Toast.makeText(NorthEveningSnacks.this, "Please give feedback for atleast one items", Toast.LENGTH_SHORT).show();
            } else {
                editTextRemarks1String1 = editTextRemarks1.getText().toString();
                editTextRemarks2String2 = editTextRemarks2.getText().toString();
                editTextItemName1String1 = editTextItemName1.getText().toString();
                editTextItemName2String2 = editTextItemName2.getText().toString();

                if (editTextRemarks1String1.equals("") || editTextRemarks1String1.equals(null)) {
                    editTextRemarks1String1 = "None";
                }
                if (editTextRemarks2String2.equals("") || editTextRemarks2String2.equals(null)) {
                    editTextRemarks2String2 = "None";
                }
                if (editTextItemName1String1.equals("") || editTextItemName1String1.equals(null)) {
                    editTextItemName1String1 = "None";
                }
                if (editTextItemName2String2.equals("") || editTextItemName2String2.equals(null)) {
                    editTextItemName2String2 = "None";
                }

                progressDialog.show();
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
                calendar = Calendar.getInstance();
                dateString = simpleDateFormat.format(calendar.getTime());
                if (count == 1) {
                    count = 0;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlNorthEveningSnacks,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equals("TRUE")) {
                                        Toast.makeText(NorthEveningSnacks.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(NorthEveningSnacks.this, HomePage.class));
                                        finish();
                                    } else if (response.equals("FALSE")) {
                                        Toast.makeText(NorthEveningSnacks.this, "Server problem. Try again later", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(NorthEveningSnacks.this, HomePage.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(NorthEveningSnacks.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NorthEveningSnacks.this, HomePage.class));
                            finish();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("rollnumber", sharedPreferencesData.GetRegistrationNo());
                            param.put("date", dateString);
                            param.put("itemname1", editTextItemName1String1);
                            param.put("question1", rb1String1);
                            param.put("remarks1", editTextRemarks1String1);
                            param.put("itemname2", editTextItemName2String2);
                            param.put("question2", rb2String2);
                            param.put("remarks2", editTextRemarks2String2);
                            return param;
                        }
                    };
                    MySingleton.getInstance(NorthEveningSnacks.this).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait !! Submission Is In Process !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(NorthEveningSnacks.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}
