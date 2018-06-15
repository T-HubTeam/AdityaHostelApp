package technicalhub.io.purshotam.adityahostelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ServiceConnect extends AppCompatActivity {

    SharedPreferencesData sharedPreferencesData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_connect);
        sharedPreferencesData = new SharedPreferencesData(ServiceConnect.this);
    }

    public void RegisterComplain(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            startActivity(new Intent(ServiceConnect.this,RegisterComplain.class));
        }
        else{
            Toast.makeText(ServiceConnect.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void ComplainStatus(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            startActivity(new Intent(ServiceConnect.this,ComplainStatus.class));
        }
        else{
            Toast.makeText(ServiceConnect.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void ComplainHistory(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            startActivity(new Intent(ServiceConnect.this,ComplainHistory.class));
        }
        else{
            Toast.makeText(ServiceConnect.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void HostelImportantContact(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            Intent intent = new Intent(ServiceConnect.this,ImportantContact.class);
            intent.putExtra("rank","warden");
            startActivity(intent);
        }
        else{
            Toast.makeText(ServiceConnect.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ServiceConnect.this,HomePage.class));
        finish();
    }
}
