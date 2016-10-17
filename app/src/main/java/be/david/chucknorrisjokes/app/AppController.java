package be.david.chucknorrisjokes.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by David on 17/10/2016.
 */

public class AppController extends Application {

    private static AppController instance ;

    private RequestQueue requestQueue;

    public static synchronized AppController getInstance() {

        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static AppController getinstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null)
            {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {

        request.setTag("AppController");

        getRequestQueue().add(request);



    }

    public void cancelPendingRequests() {

        if (requestQueue != null) {


            requestQueue.cancelAll("AppController");

        }

    }
}
