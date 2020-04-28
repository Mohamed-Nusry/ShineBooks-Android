package com.example.shinebookstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyBooksRecAdapter extends RecyclerView.Adapter<MyBooksRecAdapter.MyViewHolder> {
    private Context context;
    private List<Book> mybookList;


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mybookName, mybookPrice, mybookISBN,mybookAuthor;
        ImageView mybookImg;
        Button mybookbtndet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mybookName=(TextView) itemView.findViewById(R.id.bName);
            mybookPrice=(TextView) itemView.findViewById(R.id.bPrice);
            mybookISBN=(TextView) itemView.findViewById(R.id.bISBN);
            mybookAuthor=(TextView) itemView.findViewById(R.id.bAuthor);
            mybookImg=(ImageView) itemView.findViewById(R.id.bImage);
            mybookbtndet=(Button) itemView.findViewById(R.id.btndet);
        }
    }

    public MyBooksRecAdapter(Context context, List<Book> mybookList) {
        this.context = context;
        this.mybookList = mybookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.book_view,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Book book = mybookList.get(i);

        myViewHolder.mybookName.setText(book.getName());
        myViewHolder.mybookPrice.setText(book.getPrice());
        myViewHolder.mybookISBN.setText(book.getISBN());
        myViewHolder.mybookAuthor.setText(book.getAuthor());


        if(!book.getImageURL().isEmpty() && !book.getImageURL().equals("null")){
            Glide.with(context)
                    .load(book.getImageURL())
                    .placeholder(R.drawable.ic_book_black_24dp)
                    // .circleCrop()
                    .into(myViewHolder.mybookImg);
        }else{
            myViewHolder.mybookImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_book_black_24dp));
        }

    }

    @Override
    public int getItemCount() {
        return mybookList.size();
    }

}
