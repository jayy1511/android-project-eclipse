package com.example.redalert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HealthArticleAdapter extends RecyclerView.Adapter<HealthArticleAdapter.ArticleViewHolder> {

    private List<HealthArticle> articleList;

    public HealthArticleAdapter(List<HealthArticle> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        HealthArticle article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            description = itemView.findViewById(R.id.articleDescription);
        }
    }
}
