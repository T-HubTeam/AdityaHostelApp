package technicalhub.io.purshotam.adityahostelapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import java.util.List;
import java.util.Map;

public class ImportantContact extends AppCompatActivity {

    private RecyclerView recyclerViewContact;
    private ProgressBar progressBarContact;
    List<ContactData> contactDataList;
    private static final String urlContact = "https://technicalhub.io/service_connect/app/contacts.php";
    private SharedPreferencesData sharedPreferencesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_contact);
        progressBarContact = findViewById(R.id.progressBarContact);
        sharedPreferencesData = new SharedPreferencesData(this);
        recyclerViewContact = findViewById(R.id.recyclerViewContact);
        recyclerViewContact.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ImportantContact.this);
        recyclerViewContact.setLayoutManager(layoutManager);
        contactDataList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlContact,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBarContact.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                //Toast.makeText(ImportantContact.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String rank = jsonObject.getString("rank");
                                String contact = jsonObject.getString("contactno");
                                ContactData contactData = new ContactData(name,rank,contact);
                                contactDataList.add(contactData);
                            }
                            MyContactAdapter myContactAdapter = new MyContactAdapter(contactDataList,ImportantContact.this);
                            recyclerViewContact.setAdapter(myContactAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarContact.setVisibility(View.GONE);
                Toast.makeText(ImportantContact.this,"Something went wrong",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("rank",getIntent().getStringExtra("rank"));
                if(sharedPreferencesData.GetGender().equals("Male")){
                    param.put("hostel","Boys Hostel");
                }else if(sharedPreferencesData.GetGender().equals("Female")){
                    param.put("hostel","Girls Hostel");
                }
                return param;
            }
        };
        MySingleton.getInstance(ImportantContact.this).addToRequestQueue(stringRequest);
    }

    public class MyContactAdapter extends RecyclerView.Adapter<MyContactViewHolder>{

        List<ContactData> contactDataList;
        Context context;

        MyContactAdapter(List<ContactData> contactDataList, Context context) {
            this.contactDataList = contactDataList;
            this.context = context;
        }

        @Override
        public MyContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card,parent,false);
            return new MyContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyContactViewHolder holder, int position) {
            final ContactData contactData = contactDataList.get(position);
            holder.contact_name.setText(contactData.getContact_name());
            holder.contact_rank.setText(contactData.getContact_rank());
            holder.contact_number.setText(contactData.getContact_number());
            holder.contact_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(ImportantContact.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ImportantContact.this,new String[] {Manifest.permission.CALL_PHONE},1);
                    }
                    else{
                        Intent intentCall = new Intent(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:"+holder.contact_number.getText().toString()));
                        startActivity(intentCall);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return contactDataList.size();
        }
    }

    class MyContactViewHolder extends RecyclerView.ViewHolder{

        TextView contact_name,contact_rank,contact_number;
        ImageButton contact_call;

        MyContactViewHolder(View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_rank = itemView.findViewById(R.id.contact_rank);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_call = itemView.findViewById(R.id.contact_call);
        }
    }

    public class ContactData{
        String contact_name,contact_rank,contact_number;

        ContactData(String contact_name, String contact_rank, String contact_number) {
            this.contact_name = contact_name;
            this.contact_rank = contact_rank;
            this.contact_number = contact_number;
        }

        String getContact_name() {
            return contact_name;
        }

        String getContact_rank() {
            return contact_rank;
        }

        String getContact_number() {
            return contact_number;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
