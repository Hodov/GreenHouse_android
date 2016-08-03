package hodov.greenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Send request");
        Storage storage = new Storage(this);
        storage.requestStorage(this);
        System.out.println("Finish request");
        System.out.println(storage.storage);
        if (Settings.getSetting("token") != null) {
            //Intent intent = new Intent(this, ScrollingActivity.class);
            //startActivity(intent);
        }
    }
}
