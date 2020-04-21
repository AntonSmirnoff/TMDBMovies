package com.nordsgn.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nordsgn.mymovies.R;
import com.nordsgn.mymovies.data.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews;

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //переопределяем онКриэйтВьюХолдер
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewContent.setText(review.getContent());
        holder.textViewAuthor.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        //данный метод возвращает количество отзывов
        return reviews.size();
    }

    //добавм сеттер на массив ревье ALT+INSERT
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        //кога мы установили список нам нужно вызвать метод
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewContent;

        //переопределяем конструктор ctrl+o
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            //после этого можно наследовать наш класс ReviewAdapter от адаптера
            // и нажимаем ctrl+i для переопределния методов и onCreateViewHolder,
            // onBindViewHolder, getItemCount

        }
    }
}
