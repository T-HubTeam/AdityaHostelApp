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

public class ComplainHistory extends AppCompatActivity {

    private static final String urlHistory="https://technicalhub.io/service_connect/app/History.php";
    private RecyclerView recyclerView;
    private List<HistoryData> hist_list;
    private SharedPreferencesData sharedPreferencesData;
    private TextView history_not_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_history);
        sharedPreferencesData=new SharedPreferencesData(ComplainHistory.this);
        history_not_found = findViewById(R.id.history_not_found);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(ComplainHistory.this);
        recyclerView.setLayoutManager(layoutManager);
        hist_list=new ArrayList<>();
        Display();
    }
    public void Display(){
        final ProgressDialog progressDialog=new ProgressDialog(ComplainHistory.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlHistory,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if(!response.equals("False")) {
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
                                    HistoryData h = new HistoryData(string_category, string_code, string_description,Stringdate);
                                    hist_list.add(h);
                                }
                                HistoryAdapter historyAdapter = new HistoryAdapter(hist_list, ComplainHistory.this);
                                recyclerView.setAdapter(historyAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            history_not_found.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("registration",sharedPreferencesData.GetRegistrationNo());
                return param;
            }
        };
        MySingleton.getInstance(ComplainHistory.this).addToRequestQueue(stringRequest);
    }



    /*
    Adapter class for holding the data fetched by the volley
     */

    public class HistoryAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<HistoryData> list;
        private Context context;

        HistoryAdapter(List<HistoryData> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HistoryData historyData=list.get(position);
            holder.descripton.setText(historyData.getHist_description());
            holder.category.setText(historyData.getHist_category());
            holder.code.setText(historyData.getHist_code());
            holder.date.setText(historyData.getHist_date());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView category,descripton,code,date;

        ViewHolder(View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.history_category);
            descripton=itemView.findViewById(R.id.history_description);
            code=itemView.findViewById(R.id.history_code);
            date = itemView.findViewById(R.id.history_date);
        }
    }

    public class HistoryData
    {
        private String hist_category,hist_code,hist_description,hist_date;

        public HistoryData(String hist_category, String hist_code, String hist_description, String hist_date) {
            this.hist_category = hist_category;
            this.hist_code = hist_code;
            this.hist_description = hist_description;
            this.hist_date = hist_date;
        }

        public String getHist_category() {
            return hist_category;
        }

        public String getHist_code() {
            return hist_code;
        }

        public String getHist_description() {
            return hist_description;
        }

        public String getHist_date() {
            return hist_date;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ComplainHistory.this,ServiceConnect.class));
        finish();
    }
}
