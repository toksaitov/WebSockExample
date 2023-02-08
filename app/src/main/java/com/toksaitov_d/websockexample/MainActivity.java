package com.toksaitov_d.websockexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket sock;
    {
        try {
            sock = IO.socket("http://auca.space:8080");
        } catch (URISyntaxException ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sock.on("update", onUpdate);
        sock.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sock.disconnect();
    }

    public void onSpawnButtonClicked(View view) {
        sock.emit("spawn");
    }

    public void onEmitButtonClicked(View view) {
        sock.emit("command", "w");
    }

    private final Emitter.Listener onUpdate = args -> runOnUiThread(() -> {
        JSONArray data = (JSONArray) args[0];
        try {
            Log.d("Got server data", data.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    });

}
