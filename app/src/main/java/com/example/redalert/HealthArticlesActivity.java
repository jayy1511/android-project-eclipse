package com.example.redalert;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HealthArticlesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<HealthArticle> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        articleList = new ArrayList<>();
        articleList.add(new HealthArticle("Stay hydrated during PMS", "Drink 8+ glasses of water to reduce bloating and fatigue."));
        articleList.add(new HealthArticle("Sleep stabilizes hormones", "A consistent 7–9 hours of sleep supports emotional balance."));
        articleList.add(new HealthArticle("Cycle tracking is self-care", "Knowing your cycle improves physical and mental wellness."));
        articleList.add(new HealthArticle("Exercise during luteal phase", "Light workouts like yoga ease mood swings and cramps."));
        articleList.add(new HealthArticle("Consult a doctor if cramps are intense", "Severe pain could signal underlying conditions like endometriosis."));

        HealthArticleAdapter adapter = new HealthArticleAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }
}
