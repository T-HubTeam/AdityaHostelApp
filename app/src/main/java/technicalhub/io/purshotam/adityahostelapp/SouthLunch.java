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

public class SouthLunch extends AppCompatActivity {

    RadioGroup rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7,rb8,rb9,rb10;
    EditText editTextRemarks1,editTextRemarks2,editTextRemarks3,editTextRemarks4,editTextRemarks5,editTextRemarks6,editTextRemarks7,editTextRemarks8,editTextRemarks9,editTextRemarks10;
    EditText editTextItemName2,editTextItemName4,editTextItemName5,editTextItemName7,editTextItemName8,editTextItemName9;
    String rb1String1 = "None",rb2String2 = "None",rb3String3 = "None",rb4String4 = "None",rb5String5 = "None",rb6String6 = "None",rb7String7 = "None",rb8String8 = "None",rb9String9 = "None",rb10String10 = "None";
    String editTextRemarks1String1,editTextRemarks2String2,editTextRemarks3String3,editTextRemarks4String4,editTextRemarks5String5,editTextRemarks6String6,editTextRemarks7String7,editTextRemarks8String8,editTextRemarks9String9,editTextRemarks10String10;
    String editTextItemName2String2,editTextItemName4String4,editTextItemName5String5,editTextItemName7String7,editTextItemName8String8,editTextItemName9String9;
    String dateString,editTextRollNumberStringLunch;
    Button buttonSouthLunch;
    int count = 1;
    SimpleDateFormat simpleDateFormat;
    Calendar calendar;
    private static final String urlSouthLunch="https://technicalhub.io/canteen_feedback/insertsouthcanteenlunch.php";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south_lunch);
        sharedPreferencesData=new SharedPreferencesData(SouthLunch.this);
        rg1 = findViewById(R.id.rgsl1);
        rg2 = findViewById(R.id.rgsl2);
        rg3 = findViewById(R.id.rgsl3);
        rg4 = findViewById(R.id.rgsl4);
        rg5 = findViewById(R.id.rgsl5);
        rg6 = findViewById(R.id.rgsl6);
        rg7 = findViewById(R.id.rgsl7);
        rg8 = findViewById(R.id.rgsl8);
        rg9 = findViewById(R.id.rgsl9);
        rg10 = findViewById(R.id.rgsl10);
        editTextRemarks1 = findViewById(R.id.editTextRemarksSL1);
        editTextRemarks2 = findViewById(R.id.editTextRemarksSL2);
        editTextRemarks3 = findViewById(R.id.editTextRemarksSL3);
        editTextRemarks4 = findViewById(R.id.editTextRemarksSL4);
        editTextRemarks5 = findViewById(R.id.editTextRemarksSL5);
        editTextRemarks6 = findViewById(R.id.editTextRemarksSL6);
        editTextRemarks7 = findViewById(R.id.editTextRemarksSL7);
        editTextRemarks8 = findViewById(R.id.editTextRemarksSL8);
        editTextRemarks9 = findViewById(R.id.editTextRemarksSL9);
        editTextRemarks10 = findViewById(R.id.editTextRemarksSL10);
        editTextItemName2 = findViewById(R.id.editTextItemNameSL2);
        editTextItemName4 = findViewById(R.id.editTextItemNameSL4);
        editTextItemName5 = findViewById(R.id.editTextItemNameSL5);
        editTextItemName7 = findViewById(R.id.editTextItemNameSL7);
        editTextItemName8 = findViewById(R.id.editTextItemNameSL8);
        editTextItemName9 = findViewById(R.id.editTextItemNameSL9);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SouthLunch.this,HomePage.class));
        finish();
    }

    public void SouthLunchMethod(View view) {
        if (sharedPreferencesData.isNetworkAvailable()) {
            final ProgressDialog progressDialog = new ProgressDialog(SouthLunch.this);
            progressDialog.setMessage("Please wait...");
            int selectedRadioButtonId1, selectedRadioButtonId2, selectedRadioButtonId3, selectedRadioButtonId4, selectedRadioButtonId5, selectedRadioButtonId6, selectedRadioButtonId7, selectedRadioButtonId8, selectedRadioButtonId9, selectedRadioButtonId10;
            selectedRadioButtonId1 = rg1.getCheckedRadioButtonId();
            selectedRadioButtonId2 = rg2.getCheckedRadioButtonId();
            selectedRadioButtonId3 = rg3.getCheckedRadioButtonId();
            selectedRadioButtonId4 = rg4.getCheckedRadioButtonId();
            selectedRadioButtonId5 = rg5.getCheckedRadioButtonId();
            selectedRadioButtonId6 = rg6.getCheckedRadioButtonId();
            selectedRadioButtonId7 = rg7.getCheckedRadioButtonId();
            selectedRadioButtonId8 = rg8.getCheckedRadioButtonId();
            selectedRadioButtonId9 = rg9.getCheckedRadioButtonId();
            selectedRadioButtonId10 = rg10.getCheckedRadioButtonId();

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
            if (selectedRadioButtonId10 != -1) {
                rb10 = findViewById(selectedRadioButtonId10);
                rb10String10 = rb10.getText().toString();
            }

            if (rg1.getCheckedRadioButtonId() <= 0 && rg2.getCheckedRadioButtonId() <= 0 && rg3.getCheckedRadioButtonId() <= 0 && rg4.getCheckedRadioButtonId() <= 0 && rg5.getCheckedRadioButtonId() <= 0 && rg6.getCheckedRadioButtonId() <= 0 && rg7.getCheckedRadioButtonId() <= 0 && rg8.getCheckedRadioButtonId() <= 0 && rg9.getCheckedRadioButtonId() <= 0 && rg10.getCheckedRadioButtonId() <= 0) {
                Toast.makeText(SouthLunch.this, "Please give feedback for atleast one item", Toast.LENGTH_SHORT).show();
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
                editTextRemarks10String10 = editTextRemarks10.getText().toString();
                editTextItemName2String2 = editTextItemName2.getText().toString();
                editTextItemName4String4 = editTextItemName4.getText().toString();
                editTextItemName5String5 = editTextItemName5.getText().toString();
                editTextItemName7String7 = editTextItemName7.getText().toString();
                editTextItemName8String8 = editTextItemName8.getText().toString();
                editTextItemName9String9 = editTextItemName9.getText().toString();

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
                if (editTextRemarks10String10.equals("") || editTextRemarks10String10.equals(null)) {
                    editTextRemarks10String10 = "None";
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
                if (editTextItemName7String7.equals("") || editTextItemName7String7.equals(null)) {
                    editTextItemName7String7 = "None";
                }
                if (editTextItemName8String8.equals("") || editTextItemName8String8.equals(null)) {
                    editTextItemName8String8 = "None";
                }
                if (editTextItemName9String9.equals("") || editTextItemName9String9.equals(null)) {
                    editTextItemName9String9 = "None";
                }

                progressDialog.show();
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/DD");
                calendar = Calendar.getInstance();
                dateString = simpleDateFormat.format(calendar.getTime());
                if (count == 1) {
                    count = 0;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSouthLunch,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    if (response.equals("TRUE")) {
                                        Toast.makeText(SouthLunch.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthLunch.this, HomePage.class));
                                        finish();
                                    } else if (response.equals("FALSE")) {
                                        Toast.makeText(SouthLunch.this, "Server problem. Try again later", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SouthLunch.this, HomePage.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(SouthLunch.this, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("rollnumber", sharedPreferencesData.GetRegistrationNo());
                            // param.put("date",dateString);
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
                            param.put("question6", rb6String6);
                            param.put("remarks6", editTextRemarks6String6);
                            param.put("itemname7", editTextItemName7String7);
                            param.put("question7", rb7String7);
                            param.put("remarks7", editTextRemarks7String7);
                            param.put("itemname8", editTextItemName8String8);
                            param.put("question8", rb8String8);
                            param.put("remarks8", editTextRemarks8String8);
                            param.put("itemname9", editTextItemName9String9);
                            param.put("question9", rb9String9);
                            param.put("remarks9", editTextRemarks9String9);
                            param.put("question10", rb10String10);
                            param.put("remarks10", editTextRemarks10String10);
                            return param;
                        }
                    };
                    MySingleton.getInstance(SouthLunch.this).addToRequestQueue(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Wait !! Submission Is In Process !!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(SouthLunch.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}




