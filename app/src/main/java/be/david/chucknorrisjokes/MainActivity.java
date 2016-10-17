package be.david.chucknorrisjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import be.david.chucknorrisjokes.app.AppPreferences;

public class MainActivity extends AppCompatActivity {

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreferences = new AppPreferences(this);

        if (appPreferences.isFirstTime()) {


        }
    }

    public void saveUsername(View v) {

        EditText username = (EditText) findViewById(R.id.entryText);

        appPreferences.setUsername(username.getText().toString().trim());



    }
}
