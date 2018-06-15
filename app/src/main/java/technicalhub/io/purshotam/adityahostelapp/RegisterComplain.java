package technicalhub.io.purshotam.adityahostelapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterComplain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText description;
    private ImageView imageView;
    public String spiner[]={" ","Electricity","Plumbing","Carpentry","Others"};
    private static final int CAM=100,GAL=101;
    private SharedPreferencesData sharedPreferencesData;
    private String image="NoImage",category,code;
    private static final String urlRegisterComplain="https://technicalhub.io/service_connect/app/RegisterComplain.php";
    private TextView drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complain);
        Spinner spinner = findViewById(R.id.spinner);
        description=findViewById(R.id.description);
        imageView=findViewById(R.id.imageView2);
        sharedPreferencesData=new SharedPreferencesData(RegisterComplain.this);
        spinner.setOnItemSelectedListener(RegisterComplain.this);
        ArrayAdapter adapter = new ArrayAdapter(RegisterComplain.this,R.layout.support_simple_spinner_dropdown_item,spiner);
        spinner.setAdapter(adapter);
        drop=findViewById(R.id.drop);
    }

    public void UseCamera(View view) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAM);
    }

    public void UseGallery(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if(requestCode==CAM && resultCode==RESULT_OK && data!=null){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
            image=sharedPreferencesData.BitmapToString(bitmap);
        }else if(requestCode==GAL && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                if(height > 2000 && width > 2000) {
                    imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 200, false));
                }
                else{
                    imageView.setImageBitmap(bitmap);
                }
                imageView.setVisibility(View.VISIBLE);
                image = sharedPreferencesData.BitmapToString(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category=spiner[position];
        switch (position){
            case 0: code=" ";  drop.setVisibility(View.VISIBLE); break;
            case 1: code="EL"; drop.setVisibility(View.GONE); break;
            case 2: code="PL"; drop.setVisibility(View.GONE); break;
            case 3: code="CP"; drop.setVisibility(View.GONE); break;
            case 4: code="FD"; drop.setVisibility(View.GONE); break;
            case 5: code="OT"; drop.setVisibility(View.GONE); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(RegisterComplain.this,"Please select something",Toast.LENGTH_SHORT).show();
    }

    public void Submit(View view) {
        if(description.getText().toString().equals("") || category.equals(" ")){
            if(description.getText().toString().equals("")){
                description.setError("Write some description of your complain");
            }
            if(category.equals("")){
                Toast.makeText(RegisterComplain.this,"Select the category of the complain",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(sharedPreferencesData.isNetworkAvailable()){
                final ProgressDialog progressDialog=new ProgressDialog(RegisterComplain.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                final StringRequest stringRequest=new StringRequest(Request.Method.POST, urlRegisterComplain,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                switch(response){
                                    case "SavedWithImage":
                                    case "SavedWithoutImage":
                                        Toast.makeText(RegisterComplain.this,"Complain registered successfully",Toast.LENGTH_SHORT).show();
                                        Notification.Builder nofication=new Notification.Builder(RegisterComplain.this);
                                        nofication.setContentTitle("Service Connect");
                                        nofication.setContentText("The complain you registered is under processing");
                                        nofication.setTicker("New Notification");
                                        nofication.setSmallIcon(R.drawable.aditya_logo);
                                        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        nofication.setSound(soundUri);
                                        nofication.setAutoCancel(true);
                                        //Starts
                                        Intent resultIntent = new Intent(RegisterComplain.this, ComplainStatus.class);
                                        PendingIntent resultPendingIntent =
                                                PendingIntent.getActivity(
                                                        RegisterComplain.this,
                                                        0,
                                                        resultIntent,
                                                        PendingIntent.FLAG_UPDATE_CURRENT
                                                );
                                        nofication.setContentIntent(resultPendingIntent);
                                        //Ends
                                        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        assert nm != null;
                                        nm.notify(1,nofication.build());
                                        startActivity(new Intent(RegisterComplain.this,ServiceConnect.class));
                                        finish();
                                        break;
                                    case "FailedToSaveWithImage":
                                    case "FailedToSaveWithoutImage":
                                        Toast.makeText(RegisterComplain.this,"Failed",Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(RegisterComplain.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterComplain.this,"Something went wrong"+error,Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();
                        param.put("regNo",sharedPreferencesData.GetRegistrationNo());
                        param.put("category",category);
                        param.put("description",description.getText().toString());
                        param.put("image",image);
                        param.put("code",code);
                        param.put("room",sharedPreferencesData.GetRoom());
                        param.put("gender",sharedPreferencesData.GetGender());
                        return param;
                    }
                };
                MySingleton.getInstance(RegisterComplain.this).addToRequestQueue(stringRequest);
            }
            else{
                Toast.makeText(RegisterComplain.this,"No network",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterComplain.this,ServiceConnect.class));
        finish();
    }
}