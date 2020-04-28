package com.example.shinebookstore;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyBooksFragment extends Fragment {
    private List<Book> mybookList;

    public MyBooksFragment() {
        mybookList=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_my_books_fragment,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mobileRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            FetchBookData fetchBookData = new FetchBookData("http://192.168.1.2:8080/sunshinebookapi/myBooks.php?api_key=abc");
            mybookList=fetchBookData.execute().get();


        }catch (Exception ex){
            ex.printStackTrace();
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(),mybookList);

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.ic_divider_line));
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }
}
