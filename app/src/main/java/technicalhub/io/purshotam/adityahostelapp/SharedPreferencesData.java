package technicalhub.io.purshotam.adityahostelapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Purshotam on 08-Feb-18.
 */

/*
    this class is used to store different types of data in the mobile for future reference
 */
public class SharedPreferencesData {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesData(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("My spp",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    //It saves the block status of invalid user
    public void SaveBlocked(boolean block){
        editor.putBoolean("Blocked",block);
        editor.apply();
    }

    //It returns true if the user clicks on the checkbox to stay login
    public boolean IsBlocked(){
        return sharedPreferences.getBoolean("Blocked",false);
    }

    //It saves the user login
    public void SaveLoggedIn(boolean loggedin){
        editor.putBoolean("LoginMode",loggedin);
        editor.apply();
    }

    //It returns true if the user clicks on the checkbox to stay login
    public boolean IsLoggedIn(){
        return sharedPreferences.getBoolean("LoginMode",false);
    }

    //It saves the application installation
    public void SaveInstallation(boolean installed){
        editor.putBoolean("Installed",installed);
        editor.apply();
    }

    //It returns true if the application is already installed in the mobile
    public boolean IsInstalled(){
        return sharedPreferences.getBoolean("Installed",false);
    }

    //It saves the name of the user for future reference
    public void SaveName(String name){
        editor.putString("Name",name);
        editor.apply();
    }

    //It returns the name of the user for displaying on the navigation drawer
    public String GetName(){
        return sharedPreferences.getString("Name","none");
    }

    //It is used to save the block of the user which he/she belongs to for future reference
    public void SaveBlock(String block){
        editor.putString("block",block);
        editor.apply();
    }

    //It returns the registration number of the user when needed
    public String GetBlock(){
        return sharedPreferences.getString("block","none");
    }

    //It is used to save the registration number of the user for future reference
    public void SaveRegistrationNo(String reg){
        editor.putString("RegistrationNo",reg);
        editor.apply();
    }

    //It returns the registration number of the user when needed
    public String GetRegistrationNo(){
        return sharedPreferences.getString("RegistrationNo","none");
    }

    //It is used to save the email id of the user for future reference
    public void SaveEmailId(String email){
        editor.putString("Email",email);
        editor.apply();
    }

    //It returns the registration number of the user when needed
    public String GetEmailId(){
        return sharedPreferences.getString("Email","none");
    }

    //It saves profile image for the for displaying on navigation drawer
    public void SaveProfileImage(String image){
        editor.putString("ProfileImage",image);
        editor.apply();
    }

    //It returns the profile image of the user
    public String GetProfileImage(){
        return sharedPreferences.getString("ProfileImage","none");
    }

    //It saves the mess of the student to which a student belong
    public void SaveMess(String mess){
        editor.putString("mess",mess);
        editor.apply();
    }

    //It returns the mess of the student to which a student belong
    public String GetMess(){
        return sharedPreferences.getString("mess","none");
    }

    //It saves the room number
    public void SaveContactNo(String contact){
        editor.putString("Contact",contact);
        editor.apply();
    }

    //It returns the gender
    public String GetGender(){
        return sharedPreferences.getString("Gender","none");
    }

    //It saves the gender
    public void SaveGender(String gender){
        editor.putString("Gender",gender);
        editor.apply();
    }

    //It returns the contact number
    public String GetContactNo(){
        return sharedPreferences.getString("Contact","none");
    }

    public void SaveRoom(String room){
        editor.putString("Room",room);
        editor.apply();
    }

    //It returns the room number
    public String GetRoom(){
        return sharedPreferences.getString("Room","none");
    }

    //Convert Image Bitmap to String
    public String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    //Convert String to Image Bitmap
    public Bitmap StringToBitmap(String image){
        byte[] decodedString=Base64.decode(image,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
    }

    //Checks either the network is available or not
    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }
}











