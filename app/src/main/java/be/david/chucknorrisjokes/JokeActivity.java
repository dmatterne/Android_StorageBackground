package be.david.chucknorrisjokes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import be.david.chucknorrisjokes.app.AppController;
import be.david.chucknorrisjokes.app.AppPreferences;
import be.david.chucknorrisjokes.lab.ConnectionDetector;

import static com.android.volley.Request.Method.GET;

public class JokeActivity extends AppCompatActivity {

    private TextView jokeField;
    private AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        jokeField = (TextView) findViewById(R.id.jokeField);

        preferences = new AppPreferences(this);

        if (preferences.isFirstTime()) {

            Toast.makeText(this, "Hi " + preferences.getUsername(),Toast.LENGTH_LONG).show();

            preferences.setFirstTime(true);

        } else {

            Toast.makeText(this, "Welcome back, " + preferences.getUsername(), Toast.LENGTH_LONG).show();

        }

        nextJoke();





    }

    public void getNextJoke(View v) {

        nextJoke();

    }

    private void nextJoke() {
        ConnectionDetector cd = new ConnectionDetector(this);

        if (cd.isConnectingToInternet()) {

            getOnlineJoke();
        } else {
            readJokeFromFile();
        }
    }


    private void getOnlineJoke() {

        jokeField.setText("Loading ...");
        JsonObjectRequest json = new JsonObjectRequest(GET,
                "http://api.icndb.com/jokes/random",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d("Response",response.toString());

                        try {

                            String text = response.getJSONObject("value").getString("joke");
                            jokeField.setText(text);
                            writeToFile(text);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Response", "ERROR:" + e.getMessage());
                            Toast.makeText(JokeActivity.this,"Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
                            jokeField.setText("ERROR ERROR ERROR!!!!'");

                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Response", "ERROR:" + error.getMessage());
                    }
                }


        );


        AppController.getInstance().addToRequestQueue(json);

    }


    private void writeToFile(String joke){

        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("joke.json", Context.MODE_PRIVATE));
            out.write(joke);
            out.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            Log.e("FileWrite","ERROR: " + e.getMessage());
        }
    }

    private void readJokeFromFile(){

        String joke = "";
        try {
            InputStream inputstream = openFileInput("joke.json");

            if (inputstream != null) {

                InputStreamReader reader = new InputStreamReader(inputstream);

                BufferedReader bufferedReader = new BufferedReader(reader);

                String receiveString = "";

                StringBuilder sbuilder = new StringBuilder();

                Log.v("Message","Reading...");

                while ((receiveString = bufferedReader.readLine()) != null) {

                    sbuilder.append(receiveString);

                }

                inputstream.close();

                joke = sbuilder.toString();

                jokeField.setText(joke);

            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}
