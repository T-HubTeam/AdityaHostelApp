package technicalhub.io.purshotam.adityahostelapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import java.util.Map;

public class RulesRegulation extends AppCompatActivity {

    private static final String urlRulesandRegulations = "https://technicalhub.io/service_connect/app/rulesandregulations.php";
    private ProgressBar progressBarRulesandRegulations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_regulation);
        progressBarRulesandRegulations = findViewById(R.id.progressBarRulesandRegulations);
        progressBarRulesandRegulations.setVisibility(View.VISIBLE);
        final RecyclerView recyclerView_RulesandRegulation = findViewById(R.id.recyclerView_RulesandRegulation);
        recyclerView_RulesandRegulation.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RulesRegulation.this);
        recyclerView_RulesandRegulation.setLayoutManager(layoutManager);
        final ArrayList<String> stringList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlRulesandRegulations,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarRulesandRegulations.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0 ; i < jsonArray.length() ; i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                stringList.add(jsonObject.getString("rules"));
                            }
                            MyRulesRegulationAdapter myRulesRegulationAdapter = new MyRulesRegulationAdapter(stringList,RulesRegulation.this);
                            recyclerView_RulesandRegulation.setAdapter(myRulesRegulationAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RulesRegulation.this,"Something went wrong"+error,Toast.LENGTH_LONG).show();
                progressBarRulesandRegulations.setVisibility(View.GONE);
                startActivity(new Intent(RulesRegulation.this,HomePage.class));
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> param = new HashMap<>();
                param.put("category",getIntent().getStringExtra("category"));
                return param;
            }
        };
        MySingleton.getInstance(RulesRegulation.this).addToRequestQueue(stringRequest);
    }

    public class MyRulesRegulationAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private ArrayList<String> list;
        private Context context;

        MyRulesRegulationAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rules_regulations,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textViewNumber.setText((position+1)+" . ");
            holder.textViewRules.setText(list.get(position));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewRules,textViewNumber;
        MyViewHolder(View itemView) {
            super(itemView);
            textViewRules = itemView.findViewById(R.id.textViewRules);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RulesRegulation.this,RulesRegulationsType.class));
        finish();
    }
}
