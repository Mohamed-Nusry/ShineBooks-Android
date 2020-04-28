package com.example.shinebookstore;

import android.content.Context;
import android.content.Intent;
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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {
    private static final String TAG="recyclerDetect";
    private Context context;
    private List<Book> bookList;


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvPrice, tvISBN,tvAuthor;
        ImageView tvImg;
        Button btndet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView) itemView.findViewById(R.id.bName);
            tvPrice=(TextView) itemView.findViewById(R.id.bPrice);
            tvISBN=(TextView) itemView.findViewById(R.id.bISBN);
            tvAuthor=(TextView) itemView.findViewById(R.id.bAuthor);
            tvImg=(ImageView) itemView.findViewById(R.id.bImage);
            btndet=(Button) itemView.findViewById(R.id.btndet);


        }
    }

    public RecyclerAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.book_view,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Book book = bookList.get(i);
        Log.i(TAG, "book"+book);
        myViewHolder.tvName.setText(book.getName());
        myViewHolder.tvPrice.setText("LKR "+book.getPrice());
        myViewHolder.tvISBN.setText(book.getISBN());
        myViewHolder.tvAuthor.setText(book.getAuthor());


        Log.i(TAG, "img: "+book.getImageURL());
        if(!book.getImageURL().isEmpty() && !book.getImageURL().equals("null")){
            Glide.with(context)
                    .load(book.getImageURL())
                    .placeholder(R.drawable.ic_book_black_24dp)
                   // .circleCrop()
                    .into(myViewHolder.tvImg);
        }else{
            myViewHolder.tvImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_book_black_24dp));
        }


        myViewHolder.btndet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,ViewBook.class);
                intent.putExtra("bookname",book.getName());
                intent.putExtra("bookisbn",book.getISBN());
                intent.putExtra("bookprice",book.getPrice());
                intent.putExtra("bookurl",book.getImageURL());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }





}
