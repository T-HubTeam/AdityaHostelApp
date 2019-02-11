package technicalhub.io.purshotam.adityahostelapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView profile_photo;
    private SharedPreferencesData sharedPreferencesData;
    AlertDialog.Builder alertDialogLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v=navigationView.getHeaderView(0);
        TextView profile_name=v.findViewById(R.id.profile_name);
        TextView profile_regNo=v.findViewById(R.id.profile_regNo);
        sharedPreferencesData=new SharedPreferencesData(this);
        profile_name.setText(sharedPreferencesData.GetName());
        profile_regNo.setText(sharedPreferencesData.GetRegistrationNo());
        profile_photo=v.findViewById(R.id.profile_photo);
        if(sharedPreferencesData.GetProfileImage().equals("none")){
            profile_photo.setImageResource(R.drawable.profile);
        }
        else{
            profile_photo.setImageBitmap(sharedPreferencesData.StringToBitmap(sharedPreferencesData.GetProfileImage()));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder alertDialog=new AlertDialog.Builder(HomePage.this);
            alertDialog.setMessage("Are you sure you want to exit ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomePage.super.onBackPressed();
                            Intent exitt=new Intent(Intent.ACTION_MAIN);
                            exitt.addCategory(Intent.CATEGORY_HOME);
                            exitt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(exitt);
                            System.exit(0);
                            finish();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.change_password) {
            if(sharedPreferencesData.isNetworkAvailable()){
                startActivity(new Intent(HomePage.this,ChangePassword.class));
            }
            else{
                Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.view_developed_by){
            if(sharedPreferencesData.isNetworkAvailable()){
                startActivity(new Intent(HomePage.this,DevelopedBy.class));
            }
            else{
                Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.change_contactNo) {
            if(sharedPreferencesData.isNetworkAvailable()){
                startActivity(new Intent(HomePage.this,ChangeContactNumber.class));
            }
            else{
                Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.logout) {
            if(sharedPreferencesData.isNetworkAvailable()){
                alertDialogLogout = new AlertDialog.Builder(HomePage.this);
                alertDialogLogout.setMessage("Are you sure. You want to logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                sharedPreferencesData.SaveLoggedIn(false);
                                sharedPreferencesData.SaveProfileImage("none");
                                sharedPreferencesData.SaveEmailId("none");
                                sharedPreferencesData.SaveMess("none");
                                sharedPreferencesData.SaveContactNo("none");
                                sharedPreferencesData.SaveGender("none");
                                sharedPreferencesData.SaveName("none");
                                sharedPreferencesData.SaveRegistrationNo("none");
                                sharedPreferencesData.SaveRoom("none");
                                sharedPreferencesData.SaveBlock("none");
                                startActivity(new Intent(HomePage.this,LoginPage.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = alertDialogLogout.create();
                dialog.show();
            }
            else{
                Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.view_account) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePage.this);
            alertDialog.setMessage("My Details...")
                    .setCancelable(false)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            View view = getLayoutInflater().inflate(R.layout.my_info,null);

            ImageView myinfo_image = view.findViewById(R.id.myinfo_image);
            TextView myinfo_name = view.findViewById(R.id.myinfo_name);
            TextView myinfo_regNo = view.findViewById(R.id.myinfo_regNo);
            TextView myinfo_email = view.findViewById(R.id.myinfo_email);
            TextView myinfo_contact = view.findViewById(R.id.myinfo_contact);
            TextView myinfo_mess = view.findViewById(R.id.myinfo_mess);
            TextView myinfo_room = view.findViewById(R.id.myinfo_room);
            TextView myinfo_block = view.findViewById(R.id.myinfo_block);

            if(sharedPreferencesData.GetProfileImage().equals("none")){
                myinfo_image.setImageResource(R.drawable.profile);
            }
            else {
                myinfo_image.setImageBitmap(sharedPreferencesData.StringToBitmap(sharedPreferencesData.GetProfileImage()));
            }
            myinfo_name.setText(sharedPreferencesData.GetName());
            myinfo_regNo.setText(sharedPreferencesData.GetRegistrationNo());
            myinfo_email.setText(sharedPreferencesData.GetEmailId());
            myinfo_contact.setText(sharedPreferencesData.GetContactNo());
            myinfo_mess.setText(sharedPreferencesData.GetMess());
            myinfo_room.setText(sharedPreferencesData.GetRoom());
            myinfo_block.setText(sharedPreferencesData.GetBlock());
            alertDialog.setView(view);
            AlertDialog dialog = alertDialog.create();
            dialog.show();

        } else if (id == R.id.nav_share) {
            Intent shareIntent=new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My APP");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=technicalhub.io.purshotam.adityahostelapp");
            startActivity(Intent.createChooser(shareIntent,"send via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //This method changes the profile photo
    public void ChangeProfilePhoto(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                if(imageUri!=null){
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    int height = selectedImage.getHeight();
                    int width = selectedImage.getWidth();
                    if(height > 2000 && width > 2000) {
                        profile_photo.setImageBitmap(Bitmap.createScaledBitmap(selectedImage, 300, 200, false));
                        sharedPreferencesData.SaveProfileImage(sharedPreferencesData.BitmapToString(Bitmap.createScaledBitmap(selectedImage, 300, 200, false)));
                    }
                    else{
                        profile_photo.setImageBitmap(selectedImage);
                        sharedPreferencesData.SaveProfileImage(sharedPreferencesData.BitmapToString(selectedImage));
                    }
                }
                else{
                    Toast.makeText(HomePage.this,"Error in loading file",Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(HomePage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(HomePage.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public void ServiceConnectMethod(View view) {
        startActivity(new Intent(HomePage.this,ServiceConnect.class));
    }

    public void CanteenFeedBackMethod(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            startActivity(new Intent(HomePage.this,CanteenFeedback.class));
        }
        else{
            Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void RulesRegulationMethod(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            startActivity(new Intent(HomePage.this,RulesRegulationsType.class));
        }
        else{
            Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
        }
    }

    public void ImportantContactMethod(View view) {
        if(sharedPreferencesData.isNetworkAvailable()){
            Intent intent = new Intent(HomePage.this,ImportantContact.class);
            intent.putExtra("rank","others");
            startActivity(intent);
        }
        else{
            Toast.makeText(HomePage.this,"No network",Toast.LENGTH_LONG).show();
        }
    }
}
