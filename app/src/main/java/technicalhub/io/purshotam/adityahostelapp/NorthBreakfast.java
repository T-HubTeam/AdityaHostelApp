package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class NorthBreakfast extends AppCompatActivity {

    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
    EditText editTextRemarks1,editTextRemarks2,editTextRemarks3,editTextRemarks4,editTextRemarks5,editTextRemarks6;
    EditText editTextItemName2,editTextItemName4,editTextItemName5,editTextItemName6;
    String rb1String1 = "None",rb2String2 = "None",rb3String3 = "None",rb4String4 = "None",rb5String5 = "None",rb6String6 = "None";
    String editTextRemarks1String1,editTextRemarks2String2,editTextRemarks3String3,editTextRemarks4String4,editTextRemarks5String5,editTextRemarks6String6;
    String editTextItemName2String2,editTextItemName4String4,editTextItemName5String5,editTextItemName6String6;
    String dateString;
    int count = 1;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    private static final String urlNorthBreakfast="https://technicalhub.io/canteen_feedback/insertnorthcanteenbreakfast.php";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north_breakfast);
        sharedPreferencesData=new SharedPreferencesData(this);
        rg1 = findViewById(R.id.rg1);
        rg2 = findViewById(R.id.rg2);
        rg3 = findViewById(R.id.rg3);
        rg4 = findViewById(R.id.rg4);
        rg5 = findViewById(R.id.rg5);
        rg6 = findViewById(R.id.rg6);
        editTextRemarks1 = findViewById(R.id.editTextRemarks1);
        editTextRemarks2 = findViewById(R.id.editTextRemarks2);
        editTextRemarks3 = findViewById(R.id.editTextRemarks3);
        editTextRemarks4 = findViewById(R.id.editTextRemarks4);
        editTextRemarks5 = findViewById(R.id.editTextRemarks5);
        editTextRemarks6 = findViewById(R.id.editTextRemarks6);
        editTextItemName2 = findViewById(R.id.editTextItemName2);
        editTextItemName4 = findViewById(R.id.editTextItemName4);
        editTextItemName5 = findViewById(R.id.editTextItemName5);
        editTextItemName6 = findViewById(R.id.editTextItemName6);
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
        calendar = Calendar.getInstance();
        dateString = simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NorthBreakfast.this,HomePage.class));
        finish();
    }


    public void NorthBreakfastMethod(View view) {
        if (sharedPreferencesData.isNetworkAvailable()) {
            int selectedRadioButtonId1, selectedRadioButtonId2, selectedRadioButtonId3, selectedRadioButtonId4, selectedRadioButtonId5, selectedRadioButtonId6;
            selectedRadioButtonId1 = rg1.getCheckedRadioButtonId();
            selectedRadioButtonId2 = rg2.getCheckedRadioButtonId();
            selectedRadioButtonId3 = rg3.getCheckedRadioButtonId();
            selectedRadioButtonId4 = rg4.getCheckedRadioButtonId();
            selectedRadioButtonId5 = rg5.getCheckedRadioButtonId();
            selectedRadioButtonId6 = rg6.getCheckedRadioButtonId();
            if (selectedRadioButtonId1 != -1) {
                rb1 = findViewById(selectedRadioButtonId1);
                rb1String1 = rb1.getText().toString();
            }
            if (selectedRadioButtonId2 != -1) {
                rb2 = findViewById(selectedRadioButtonId2);
                rb2String2 = rb2.getText().toString();
            }
            if (selectedRadioButtonId3 != -1) {
                rb3 = findViewById(selectedRadioButtonId3);
                rb3String3 = rb3.getText().toString();
            }
            if (selectedRadioButtonId4 != -1) {
                rb4 = findViewById(selectedRadioButtonId4);
                rb4String4 = rb4.getText().toString();
            }
            if (selectedRadioButtonId5 != -1) {
                rb5 = findViewById(selectedRadioButtonId5);
                rb5String5 = rb5.getText().toString();
            }
            if (selectedRadioButtonId6 != -1) {
                rb6 = findViewById(selectedRadioButtonId6);
                rb6String6 = rb6.getText().toString();
            }


            if (rg1.getCheckedRadioButtonId() <= 0 && rg2.getCheckedRadioButtonId() <= 0 && rg3.getCheckedRadioButtonId() <= 0 && rg4.getCheckedRadioButtonId() <= 0 && rg5.getCheckedRadioButtonId() <= 0 && rg6.getCheckedRadioButtonId() <= 0) {
                Toast.makeText(NorthBreakfast.this, "Please give feedback for atleast one items", Toast.LENGTH_SHORT).show();
            } else {

                editTextRemarks1String1 = editTextRemarks1.getText().toString();
                editTextRemarks2String2 = editTextRemarks2.getText().toString();
                editTextRemarks3String3 = editTextRemarks3.getText().toString();
                editTextRemarks4String4 = editTextRemarks4.getText().toString();
                editTextRemarks5String5 = editTextRemarks5.getText().toString();
                editTextRemarks6String6 = editTextRemarks6.getText().toString();
                editTextItemName2String2 = editTextItemName2.getText().toString();
                editTextItemName4String4 = editTextItemName4.getText().toString();
                editTextItemName5String5 = editTextItemName5.getText().toString();
                editTextItemName6String6 = editTextItemName6.getText().toString();

                //Assigning the values to avoid null pointer exception
                if (editTextRemarks1String1.equals("") || editTextRemarks1String1.equals(null)) {
                    editTextRemarks1String1 = "None";
                }
                if (editTextRemarks2String2.equals("") || editTextRemarks2String2.equals(null)) {
                    editTextRemarks2String2 = "None";
                }
                if (editTextRemarks3String3.equals("") || editTextRemarks3String3.equals(null)) {
                    editTextRemarks3String3 = "None";
                }
                if (editTextRemarks4String4.equals("") || editTextRemarks4String4.equals(null)) {
                    editTextRemarks4String4 = "None";
                }
                if (editTextRemarks5String5.equals("") || editTextRemarks5String5.equals(null)) {
                    editTextRemarks5String5 = "None";
                }
                if (editTextRemarks6String6.equals("") || editTextRemarks6String6.equals(null)) {
                    editTextRemarks6String6 = "None";
                }
                if (editTextItemName2String2.equals("") || editTextItemName2String2.equals(null)) {
                    editTextItemName2String2 = "None";
                }
                if (editTextItemName4String4.equals("") || editTextItemName4String4.equals(null)) {
                    editTextItemName4String4 = "None";
                }
                if (editTextItemName5String5.equals("") || editTextItemName5String5.equals(null)) {
                    editTextItemName5String5 = "None";
                }
                if (editTextItemName6String6.equals("") || editTextItemName6String6.equals(null)) {
                    editTextItemName6String6 = "None";
                }

                if (count == 1) {
                    count = 0;
                    final ProgressDialog progressDialog = new ProgressDialog(NorthBreakfast.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlNorthBreakfast,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("DONE")) {
                                        Toast.makeText(NorthBreakfast.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(NorthBreakfast.this, HomePage.class));
                                        finish();
                                    } else if (response.equals("'FALSE'")) {
                                        Toast.makeText(NorthBreakfast.this, "Server problem. Try again later", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(NorthBreakfast.this, HomePage.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(NorthBreakfast.this, "Some thing went wrong...", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("rollnumber", sharedPreferencesData.GetRegistrationNo());
                            //param.put("date",dateString);
                            param.put("question1", rb1String1);
                            param.put("remarks1", editTextRemarks1String1);
                            param.put("itemname2", editTextItemName2String2);
                            param.put("question2", rb2String2);
                            param.put("remarks2", editTextRemarks2String2);
                            param.put("question3", rb3String3);
                            param.put("remarks3", editTextRemarks3String3);
                            param.put("itemname4", editTextItemName4String4);
                            param.put("question4", rb4String4);
                            param.put("remarks4", editTextRemarks4String4);
                            param.put("itemname5", editTextItemName5String5);
                            param.put("question5", rb5String5);
                            param.put("remarks5", editTextRemarks5String5);
                            param.put("itemname6", editTextItemName6String6);
                            param.put("question6", rb6String6);
                            param.put("remarks6", editTextRemarks6String6);
                            return param;
                        }
                    };
                    MySingleton.getInstance(NorthBreakfast.this).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait !! Submission Is In Process !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(NorthBreakfast.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}