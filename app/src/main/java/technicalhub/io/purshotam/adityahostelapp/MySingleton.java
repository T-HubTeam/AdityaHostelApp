package technicalhub.io.purshotam.adityahostelapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Purshotam on 12-Feb-18.
 */

public class MySingleton {
    private static  MySingleton mInstance;
    private RequestQueue requestQueue;
    private  Context context;

    private MySingleton(Context context){
        this.context=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new MySingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
