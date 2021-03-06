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

public class SouthDinner extends AppCompatActivity {

    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9;
    EditText editTextRemarks1,editTextRemarks2,editTextRemarks3,editTextRemarks4,editTextRemarks5,editTextRemarks6,editTextRemarks7,editTextRemarks8,editTextRemarks9;
    EditText editTextItemName2,editTextItemName3,editTextItemName4,editTextItemName5,editTextItemName7,editTextItemName8;
    String rb1String1 = "None",rb2String2 = "None",rb3String3 = "None",rb4String4 = "None",rb5String5 = "None",rb6String6 = "None",rb7String7 = "None",rb8String8 = "None",rb9String9 = "None";
    String editTextRemarks1String1,editTextRemarks2String2,editTextRemarks3String3,editTextRemarks4String4,editTextRemarks5String5,editTextRemarks6String6,editTextRemarks7String7,editTextRemarks8String8,editTextRemarks9String9;
    String editTextItemName2String2,editTextItemName3String3,editTextItemName4String4,editTextItemName5String5,editTextItemName7String7,editTextItemName8String8;
    String dateString,editTextRollNumberStringLunch;
    Button buttonSouthDinner;
    int count = 1;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    private static final String urlSouthDinner="https://technicalhub.io/canteen_feedback/insertsouthcanteendinner.php";
    private static final String urlSouthItems = "https://technicalhub.io/canteen_feedback/RetrievingCanteenItems.php";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_dinner);
        sharedPreferencesData=new SharedPreferencesData(SouthDinner.this);
        rg1 = findViewById(R.id.rgsd1);
        rg2 = findViewById(R.id.rgsd2);
        rg3 = findViewById(R.id.rgsd3);
        rg4 = findViewById(R.id.rgsd4);
        rg5 = findViewById(R.id.rgsd5);
        rg6 = findViewById(R.id.rgsd6);
        rg7 = findViewById(R.id.rgsd7);
        rg8 = findViewById(R.id.rgsd8);
        rg9 = findViewById(R.id.rgsd9);
        editTextRemarks1 = findViewById(R.id.editTextRemarksSD1);
        editTextRemarks2 = findViewById(R.id.editTextRemarksSD2);
        editTextRemarks3 = findViewById(R.id.editTextRemarksSD3);
        editTextRemarks4 = findViewById(R.id.editTextRemarksSD4);
        editTextRemarks5 = findViewById(R.id.editTextRemarksSD5);
        editTextRemarks6 = findViewById(R.id.editTextRemarksSD6);
        editTextRemarks7 = findViewById(R.id.editTextRemarksSD7);
        editTextRemarks8 = findViewById(R.id.editTextRemarksSD8);
        editTextRemarks9 = findViewById(R.id.editTextRemarksSD9);
        editTextItemName2 = findViewById(R.id.editTextItemNameSD2);
        editTextItemName3 = findViewById(R.id.editTextItemNameSD3);
        editTextItemName4 = findViewById(R.id.editTextItemNameSD4);
        editTextItemName5 = findViewById(R.id.editTextItemNameSD5);
        editTextItemName7 = findViewById(R.id.editTextItemNameSD7);
        editTextItemName8 = findViewById(R.id.editTextItemNameSD8);
        southDinnerItems();
    }

    private void southDinnerItems() {
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
                                    String scdcoltwo = jsonObject.getString("scdcoltwo");
                                    String scdcolthree = jsonObject.getString("scdcolthree");
                                    String scdcolfour = jsonObject.getString("scdcolfour");
                                    String scdcolfive = jsonObject.getString("scdcolfive");
                                    String scdcolseven = jsonObject.getString("scdcolseven");
                                    String scdcoleight = jsonObject.getString("scdcoleight");
                                    editTextItemName2.setText(scdcoltwo);
                                    editTextItemName3.setText(scdcolthree);
                                    editTextItemName4.setText(scdcolfour);
                                    editTextItemName5.setText(scdcolfive);
                                    editTextItemName7.setText(scdcolseven);
                                    editTextItemName8.setText(scdcoleight);
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
            MySingleton.getInstance(SouthDinner.this).addToRequestQueue(stringRequest);
        }
        else
        {
            Toast.makeText(SouthDinner.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SouthDinner.this,HomePage.class));
        finish();
    }

    public void SouthDinnerMethod(View view) {
        if (sharedPreferencesData.isNetworkAvailable()) {
            final ProgressDialog progressDialog = new ProgressDialog(SouthDinner.this);
            progressDialog.setMessage("Please wait...");
            int selectedRadioButtonId1, selectedRadioButtonId2, selectedRadioButtonId3, selectedRadioButtonId4, selectedRadioButtonId5, selectedRadioButtonId6, selectedRadioButtonId7, selectedRadioButtonId8, selectedRadioButtonId9;
            selectedRadioButtonId1 = rg1.getCheckedRadioButtonId();
            selectedRadioButtonId2 = rg2.getCheckedRadioButtonId();
            selectedRadioButtonId3 = rg3.getCheckedRadioButtonId();
            selectedRadioButtonId4 = rg4.getCheckedRadioButtonId();
            selectedRadioButtonId5 = rg5.getCheckedRadioButtonId();
            selectedRadioButtonId6 = rg6.getCheckedRadioButtonId();
            selectedRadioButtonId7 = rg7.getCheckedRadioButtonId();
            selectedRadioButtonId8 = rg8.getCheckedRadioButtonId();
            selectedRadioButtonId9 = rg9.getCheckedRadioButtonId();

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
            if (selectedRadioButtonId7 != -1) {
                rb7 = findViewById(selectedRadioButtonId7);
                rb7String7 = rb7.getText().toString();
            }
            if (selectedRadioButtonId8 != -1) {
                rb8 = findViewById(selectedRadioButtonId8);
                rb8String8 = rb8.getText().toString();
            }
            if (selectedRadioButtonId9 != -1) {
                rb9 = findViewById(selectedRadioButtonId9);
                rb9String9 = rb9.getText().toString();
            }
            if (rg1.getCheckedRadioButtonId() <= 0 && rg2.getCheckedRadioButtonId() <= 0 && rg3.getCheckedRadioButtonId() <= 0 && rg4.getCheckedRadioButtonId() <= 0 && rg5.getCheckedRadioButtonId() <= 0 && rg6.getCheckedRadioButtonId() <= 0 && rg7.getCheckedRadioButtonId() <= 0 && rg8.getCheckedRadioButtonId() <= 0 && rg9.getCheckedRadioButtonId() <= 0) {
                Toast.makeText(SouthDinner.this, "Please give feedback for atleast one items", Toast.LENGTH_SHORT).show();
            } else {
                editTextRemarks1String1 = editTextRemarks1.getText().toString();
                editTextRemarks2String2 = editTextRemarks2.getText().toString();
                editTextRemarks3String3 = editTextRemarks3.getText().toString();
                editTextRemarks4String4 = editTextRemarks4.getText().toString();
                editTextRemarks5String5 = editTextRemarks5.getText().toString();
                editTextRemarks6String6 = editTextRemarks6.getText().toString();
                editTextRemarks7String7 = editTextRemarks7.getText().toString();
                editTextRemarks8String8 = editTextRemarks8.getText().toString();
                editTextRemarks9String9 = editTextRemarks9.getText().toString();
                editTextItemName2String2 = editTextItemName2.getText().toString();
                editTextItemName3String3 = editTextItemName3.getText().toString();
                editTextItemName4String4 = editTextItemName4.getText().toString();
                editTextItemName5String5 = editTextItemName5.getText().toString();
                editTextItemName7String7 = editTextItemName7.getText().toString();
                editTextItemName8String8 = editTextItemName8.getText().toString();

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
                if (editTextRemarks7String7.equals("") || editTextRemarks7String7.equals(null)) {
                    editTextRemarks7String7 = "None";
                }
                if (editTextRemarks8String8.equals("") || editTextRemarks8String8.equals(null)) {
                    editTextRemarks8String8 = "None";
                }
                if (editTextRemarks9String9.equals("") || editTextRemarks9String9.equals(null)) {
                    editTextRemarks9String9 = "None";
                }
                if (editTextItemName2String2.equals("") || editTextItemName2String2.equals(null)) {
                    editTextItemName2String2 = "None";
                }
                if (editTextItemName3String3.equals("") || editTextItemName3String3.equals(null)) {
                    editTextItemName3String3 = "None";
                }
                if (editTextItemName4String4.equals("") || editTextItemName4String4.equals(null)) {
                    editTextItemName4String4 = "None";
                }
                if (editTextItemName5String5.equals("") || editTextItemName5String5.equals(null)) {
                    editTextItemName5String5 = "None";
                }
                if (editTextItemName7String7.equals("") || editTextItemName7String7.equals(null)) {
                    editTextItemName7String7 = "None";
                }
                if (editTextItemName8String8.equals("") || editTextItemName8String8.equals(null)) {
                    editTextItemName8String8 = "None";
                }

                progressDialog.show();
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
                calendar = Calendar.getInstance();
                dateString = simpleDateFormat.format(calendar.getTime());
                if (count == 1) {
                    count = 0;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSouthDinner,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equals("TRUE")) {
                                        Toast.makeText(SouthDinner.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthDinner.this, HomePage.class));
                                        finish();
                                    } else if (response.equals("FALSE")) {
                                        Toast.makeText(SouthDinner.this, "Server problem. Try again later", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthDinner.this, HomePage.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SouthDinner.this, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
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
                            param.put("itemname3", editTextItemName3String3);
                            param.put("question3", rb3String3);
                            param.put("remarks3", editTextRemarks3String3);
                            param.put("itemname4", editTextItemName4String4);
                            param.put("question4", rb4String4);
                            param.put("remarks4", editTextRemarks4String4);
                            param.put("itemname5", editTextItemName5String5);
                            param.put("question5", rb5String5);
                            param.put("remarks5", editTextRemarks5String5);
                            param.put("question6", rb6String6);
                            param.put("remarks6", editTextRemarks6String6);
                            param.put("itemname7", editTextItemName7String7);
                            param.put("question7", rb7String7);
                            param.put("remarks7", editTextRemarks7String7);
                            param.put("itemname8", editTextItemName8String8);
                            param.put("question8", rb8String8);
                            param.put("remarks8", editTextRemarks8String8);
                            param.put("question9", rb9String9);
                            param.put("remarks9", editTextRemarks9String9);
                            return param;
                        }
                    };
                    MySingleton.getInstance(SouthDinner.this).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait !! Submission Is In Process !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(SouthDinner.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}