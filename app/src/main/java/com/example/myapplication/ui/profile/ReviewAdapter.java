package com.example.myapplication.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> reviews = new ArrayList<>();

    public ReviewAdapter() {}

    public void setData(List<Review> data) {
        reviews = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.author.setText(review.getAuthor());
        holder.text.setText(review.getText() != null ? review.getText() : "");
        holder.rating.setRating(review.getRating());

        // Форматируем дату — берём только первые 10 символов если это ISO-строка
        String date = review.getDate();
        if (date != null && date.length() >= 10) {
            holder.date.setText(date.substring(0, 10));
        } else {
            holder.date.setText(date != null ? date : "");
        }

        // "Читать полностью" — разворачиваем текст по клику
        holder.more.setOnClickListener(v -> {
            if (holder.text.getMaxLines() == 3) {
                holder.text.setMaxLines(Integer.MAX_VALUE);
                holder.more.setText("свернуть");
            } else {
                holder.text.setMaxLines(3);
                holder.more.setText("читать полностью");
            }
        });
    }

    @Override
    public int getItemCount() { return reviews.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView author, text, date, more;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.reviewAuthor);
            text   = itemView.findViewById(R.id.reviewText);
            date   = itemView.findViewById(R.id.reviewDate);
            rating = itemView.findViewById(R.id.reviewRating);
            more   = itemView.findViewById(R.id.reviewMore);
        }
    }
}
