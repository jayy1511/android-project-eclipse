package com.example.redalert;
import com.example.redalert.models.NewsArticle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redalert.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnArticleClickListener {
        void onArticleClick(NewsArticle article);
    }

    private List<NewsArticle> articles;
    private OnArticleClickListener listener;

    public ArticleAdapter(List<NewsArticle> articles, OnArticleClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.title.setText(article.title);
        holder.itemView.setOnClickListener(v -> listener.onArticleClick(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
        }
    }
}
