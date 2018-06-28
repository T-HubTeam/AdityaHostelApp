package technicalhub.io.purshotam.adityahostelapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplainStatus extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SharedPreferencesData sharedPreferencesData;
    private static final String urlStatus = "https://technicalhub.io/service_connect/app/ProcessingStatus.php";
    private static final String urlFixStatus = "https://technicalhub.io/service_connect/app/FixStatus.php";
    private static final String urlReOpenStatus = "https://technicalhub.io/service_connect/app/reraisecomplain.php";
    List<StatusData> stat_list;
    TextView status_not_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_status);
        sharedPreferencesData=new SharedPreferencesData(ComplainStatus.this);
        status_not_found =findViewById(R.id.status_not_found);
        recyclerView=findViewById(R.id.status_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ComplainStatus.this);
        recyclerView.setLayoutManager(layoutManager);
        stat_list=new ArrayList<>();
        Display();
    }

    public void Display()
    {
        final ProgressDialog progressDialog=new ProgressDialog(ComplainStatus.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlStatus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (!response.equals("FALSE")) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = jsonArray.length()-1; i >=0 ; i--) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String string_description = jsonObject.getString("description");
                                    String string_category = jsonObject.getString("category");
                                    String string_code = jsonObject.getString("complain_id");
                                    String string_date = jsonObject.getString("date_time").substring(0,10);
                                    String date[] = (string_date.split("-"));
                                    String Stringdate = date[2]+"-"+date[1]+"-"+date[0];
                                    StatusData statusData = new StatusData(string_category, string_description, string_code,Stringdate);
                                    stat_list.add(statusData);
                                }
                                StatusAdapter statusAdapter = new StatusAdapter(stat_list, ComplainStatus.this);
                                recyclerView.setAdapter(statusAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            status_not_found.setVisibility(View.VISIBLE);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ComplainStatus.this,ServiceConnect.class));
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("registration",sharedPreferencesData.GetRegistrationNo());
                return param;
            }
        };
        MySingleton.getInstance(ComplainStatus.this).addToRequestQueue(stringRequest);
    }

    public class StatusAdapter extends RecyclerView.Adapter<ViewHolder>
    {
        private List<StatusData> list;
        private Context context;

        private StatusAdapter(List<StatusData> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status_card,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final StatusData statusData=list.get(position);
            holder.descripton.setText(statusData.getString_des());
            holder.category.setText(statusData.getString_cat());
            holder.code.setText(statusData.getString_code());
            holder.date.setText(statusData.getString_date());
            holder.btn_fix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog progressDialog = new ProgressDialog(ComplainStatus.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlFixStatus,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    switch (response) {
                                        case "TRUE":
                                            if(holder.feedback.getText().toString().equals("")){
                                                Toast.makeText(ComplainStatus.this, "Thank you...", Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Toast.makeText(ComplainStatus.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                                            }
                                            startActivity(new Intent(ComplainStatus.this,ServiceConnect.class));
                                            finish();
                                            break;
                                        case "FALSE2":
                                            Toast.makeText(ComplainStatus.this, "Unable to process. Try again later...", Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(ComplainStatus.this, "Failed to connect. Try again later", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(ComplainStatus.this,"Something went wrong...",Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("registration",sharedPreferencesData.GetRegistrationNo());
                            param.put("code",statusData.getString_code());
                            param.put("feedback",holder.feedback.getText().toString());
                            return param;
                        }
                    };
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }
            });
            holder.btn_reRaise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!holder.feedback.getText().toString().equals("") && !holder.feedback.getText().toString().equals(null)) {
                    final ProgressDialog progressDialog = new ProgressDialog(ComplainStatus.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlReOpenStatus,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    holder.feedback.setText("");
                                    switch (response) {
                                        case "FALSE1":
                                            Toast.makeText(ComplainStatus.this, "Your complain not found...", Toast.LENGTH_LONG).show();
                                            break;
                                        case "TRUE":
                                            Toast.makeText(ComplainStatus.this, "Your complaint is raised again...", Toast.LENGTH_LONG).show();
                                            break;
                                        case "FALSE2":
                                            Toast.makeText(ComplainStatus.this, "Please try again later", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            holder.feedback.setText("");
                            Toast.makeText(ComplainStatus.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("regNo", sharedPreferencesData.GetRegistrationNo());
                            param.put("ComplainCode", statusData.getString_code());
                            param.put("feedback",holder.feedback.getText().toString());
                            return param;
                        }
                    };
                    MySingleton.getInstance(ComplainStatus.this).addToRequestQueue(stringRequest);
                }
                else{
                        holder.feedback.setError("Please give the feedback");
                }
            }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView category,descripton,code,date;
        private Button btn_fix,btn_reRaise;
        private EditText feedback;

        private ViewHolder(View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.status_category);
            descripton=itemView.findViewById(R.id.status_description);
            code=itemView.findViewById(R.id.status_code);
            btn_fix=itemView.findViewById(R.id.btn_fix);
            feedback=itemView.findViewById(R.id.et_feedback);
            date = itemView.findViewById(R.id.status_date);
            btn_reRaise = itemView.findViewById(R.id.btn_reRaise);
        }
    }

    public class StatusData{
        private String string_cat,string_des,string_code,string_date;

        private StatusData(String string_cat, String string_des, String string_code,String string_date) {
            this.string_cat = string_cat;
            this.string_des = string_des;
            this.string_code = string_code;
            this.string_date = string_date;
        }

        public String getString_cat() {
            return string_cat;
        }

        public String getString_des() {
            return string_des;
        }

        public String getString_code() {
            return string_code;
        }

        public String getString_date() {
            return string_date;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ComplainStatus.this,ServiceConnect.class));
        finish();
    }
}
