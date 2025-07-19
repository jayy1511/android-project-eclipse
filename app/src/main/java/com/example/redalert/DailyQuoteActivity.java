package com.example.redalert;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DailyQuoteActivity extends AppCompatActivity {

    TextView quoteText, authorText;
    String url = "https://zenquotes.io/api/today";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quote);

        quoteText = findViewById(R.id.quoteText);
        authorText = findViewById(R.id.authorText);

        // Fetch the daily quote
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject quoteObj = response.getJSONObject(0);
                            String quote = quoteObj.getString("q");
                            String author = quoteObj.getString("a");

                            quoteText.setText("\"" + quote + "\"");
                            authorText.setText("- " + author);

                        } catch (JSONException e) {
                            Toast.makeText(DailyQuoteActivity.this, "Error parsing quote", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DailyQuoteActivity.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }
}
