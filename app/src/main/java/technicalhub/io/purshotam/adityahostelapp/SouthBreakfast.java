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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SouthBreakfast extends AppCompatActivity {

    RadioGroup rgsb1,rgsb2,rgsb3,rgsb4,rgsb5;
    RadioButton rbsb1,rbsb2,rbsb3,rbsb4,rbsb5;
    EditText editTextRemarksSB1,editTextRemarksSB2,editTextRemarksSB3,editTextRemarksSB4,editTextRemarksSB5;
    EditText editTextItemNameSB3,editTextItemNameSB4,editTextItemNameSB5;
    String rbsb1String1 = "None",rbsb2String2 = "None",rbsb3String3 = "None",rbsb4String4 = "None",rbsb5String5 = "None";
    String editTextRemarksSB1String1,editTextRemarksSB2String2,editTextRemarksSB3String3,editTextRemarksSB4String4,editTextRemarksSB5String5;
    String editTextItemNameSB3String3,editTextItemNameSB4String4,editTextItemNameSB5String5;
    Button buttonSouthBreakfast;
    String dateString,editTextRollNumberStringBreakfast;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    int count = 1;
    private static final String urlSouthBreakfast="https://technicalhub.io/canteen_feedback/insertsouthcanteenbreakfast.php";
    private static final String urlSouthItems = "https://technicalhub.io/canteen_feedback/RetrievingCanteenItems.php";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_breakfast);
        sharedPreferencesData=new SharedPreferencesData(SouthBreakfast.this);
        rgsb1 = findViewById(R.id.rgsb1);
        rgsb2 = findViewById(R.id.rgsb2);
        rgsb3 = findViewById(R.id.rgsb3);
        rgsb4 = findViewById(R.id.rgsb4);
        rgsb5 = findViewById(R.id.rgsb5);
        editTextRemarksSB1 = findViewById(R.id.editTextRemarksSB1);
        editTextRemarksSB2 = findViewById(R.id.editTextRemarksSB2);
        editTextRemarksSB3 = findViewById(R.id.editTextRemarksSB3);
        editTextRemarksSB4 = findViewById(R.id.editTextRemarksSB4);
        editTextRemarksSB5 = findViewById(R.id.editTextRemarksSB5);
        editTextItemNameSB3 = findViewById(R.id.editTextItemNameSB3);
        editTextItemNameSB4 = findViewById(R.id.editTextItemNameSB4);
        editTextItemNameSB5 = findViewById(R.id.editTextItemNameSB5);
        southBreakfastItems();
    }

    private void southBreakfastItems() {
        if (sharedPreferencesData.isNetworkAvailable())
        {
            StringRequest stringRequest=new StringRequest(Request.Method.POST,urlSouthItems,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //progressDialog.dismiss();
                            //if(!response.equals("False")) {
                            try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String scbcolthree = jsonObject.getString("scbcolthree");
                                    String scbcolfour = jsonObject.getString("scbcolfour");
                                    String scbcolfive = jsonObject.getString("scbcolfive");
                                    editTextItemNameSB3.setText(scbcolthree);
                                    editTextItemNameSB4.setText(scbcolfour);
                                    editTextItemNameSB5.setText(scbcolfive);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //}
                            //else{
                            //history_not_found.setVisibility(View.VISIBLE);
                            //}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param=new HashMap<>();
                    param.put("mess",sharedPreferencesData.GetMess());
                    return param;
                }
            };
            MySingleton.getInstance(SouthBreakfast.this).addToRequestQueue(stringRequest);
        }
        else
        {
            Toast.makeText(SouthBreakfast.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SouthBreakfast.this,HomePage.class));
        finish();
    }

    public void SouthBreakfastMethod(View view) {
        if (sharedPreferencesData.isNetworkAvailable()) {
            final ProgressDialog progressDialog = new ProgressDialog(SouthBreakfast.this);
            progressDialog.setMessage("Please wait...");
            int selectedRadioButtonId1, selectedRadioButtonId2, selectedRadioButtonId3, selectedRadioButtonId4, selectedRadioButtonId5;
            selectedRadioButtonId1 = rgsb1.getCheckedRadioButtonId();
            selectedRadioButtonId2 = rgsb2.getCheckedRadioButtonId();
            selectedRadioButtonId3 = rgsb3.getCheckedRadioButtonId();
            selectedRadioButtonId4 = rgsb4.getCheckedRadioButtonId();
            selectedRadioButtonId5 = rgsb5.getCheckedRadioButtonId();

            if (selectedRadioButtonId1 != -1) {
                rbsb1 = findViewById(selectedRadioButtonId1);
                rbsb1String1 = rbsb1.getText().toString();
            }
            if (selectedRadioButtonId2 != -1) {
                rbsb2 = findViewById(selectedRadioButtonId2);
                rbsb2String2 = rbsb2.getText().toString();
            }
            if (selectedRadioButtonId3 != -1) {
                rbsb3 = findViewById(selectedRadioButtonId3);
                rbsb3String3 = rbsb3.getText().toString();
            }
            if (selectedRadioButtonId4 != -1) {
                rbsb4 = findViewById(selectedRadioButtonId4);
                rbsb4String4 = rbsb4.getText().toString();
            }
            if (selectedRadioButtonId5 != -1) {
                rbsb5 = findViewById(selectedRadioButtonId5);
                rbsb5String5 = rbsb5.getText().toString();
            }
            if (rgsb1.getCheckedRadioButtonId() <= 0 && rgsb2.getCheckedRadioButtonId() <= 0 && rgsb3.getCheckedRadioButtonId() <= 0 && rgsb4.getCheckedRadioButtonId() <= 0 && rgsb5.getCheckedRadioButtonId() <= 0) {
                Toast.makeText(SouthBreakfast.this, "Please give feedback for atleast one items", Toast.LENGTH_SHORT).show();
            } else {
                editTextRemarksSB1String1 = editTextRemarksSB1.getText().toString();
                editTextRemarksSB2String2 = editTextRemarksSB2.getText().toString();
                editTextRemarksSB3String3 = editTextRemarksSB3.getText().toString();
                editTextRemarksSB4String4 = editTextRemarksSB4.getText().toString();
                editTextRemarksSB5String5 = editTextRemarksSB5.getText().toString();
                editTextItemNameSB3String3 = editTextItemNameSB3.getText().toString();
                editTextItemNameSB4String4 = editTextItemNameSB4.getText().toString();
                editTextItemNameSB5String5 = editTextItemNameSB5.getText().toString();
                if (editTextRemarksSB1String1.equals("") || editTextRemarksSB1String1.equals(null)) {
                    editTextRemarksSB1String1 = "None";
                }
                if (editTextRemarksSB2String2.equals("") || editTextRemarksSB2String2.equals(null)) {
                    editTextRemarksSB2String2 = "None";
                }
                if (editTextRemarksSB3String3.equals("") || editTextRemarksSB3String3.equals(null)) {
                    editTextRemarksSB3String3 = "None";
                }
                if (editTextRemarksSB4String4.equals("") || editTextRemarksSB4String4.equals(null)) {
                    editTextRemarksSB4String4 = "None";
                }
                if (editTextRemarksSB5String5.equals("") || editTextRemarksSB5String5.equals(null)) {
                    editTextRemarksSB5String5 = "None";
                }
                if (editTextItemNameSB3String3.equals("") || editTextItemNameSB3String3.equals(null)) {
                    editTextItemNameSB3String3 = "None";
                }
                if (editTextItemNameSB4String4.equals("") || editTextItemNameSB4String4.equals(null)) {
                    editTextItemNameSB4String4 = "None";
                }
                if (editTextItemNameSB5String5.equals("") || editTextItemNameSB5String5.equals(null)) {
                    editTextItemNameSB5String5 = "None";
                }
                progressDialog.show();
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
                calendar = Calendar.getInstance();
                dateString = simpleDateFormat.format(calendar.getTime());
                if (count == 1) {
                    count = 0;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSouthBreakfast,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equals("TRUE")) {
                                        Toast.makeText(SouthBreakfast.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthBreakfast.this, HomePage.class));
                                        finish();
                                    } else if (response.equals("FALSE")) {
                                        Toast.makeText(SouthBreakfast.this, "Server problem. Try again later", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthBreakfast.this, HomePage.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SouthBreakfast.this, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("rollnumber", sharedPreferencesData.GetRegistrationNo());
                            //param.put("date",dateString);
                            param.put("question1", rbsb1String1);
                            param.put("remarks1", editTextRemarksSB1String1);
                            param.put("question2", rbsb2String2);
                            param.put("remarks2", editTextRemarksSB2String2);
                            param.put("itemname3", editTextItemNameSB3String3);
                            param.put("question3", rbsb3String3);
                            param.put("remarks3", editTextRemarksSB3String3);
                            param.put("itemname4", editTextItemNameSB4String4);
                            param.put("question4", rbsb4String4);
                            param.put("remarks4", editTextRemarksSB4String4);
                            param.put("itemname5", editTextItemNameSB5String5);
                            param.put("question5", rbsb5String5);
                            param.put("remarks5", editTextRemarksSB5String5);
                            return param;
                        }
                    };
                    MySingleton.getInstance(SouthBreakfast.this).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait !! Submission Is In Process !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(SouthBreakfast.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}