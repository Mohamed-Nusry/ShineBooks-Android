package com.example.shinebookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem=menu.add("Go back");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    private static final String TAG="bDetect";
    private List<Book> bookList;
    private Button btde;


    public StoreFragment() {
        bookList=new ArrayList<>();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_store_fragment,container,false);

        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.bookRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try{
            FetchBookData fetchBookData = new FetchBookData("http://192.168.1.2:8080/sunshinebookapi/?api_key=abc");
            bookList=fetchBookData.execute().get();
            Book book=bookList.get(0);

            Log.i(TAG,"list "+bookList.get(0));
            Log.i(TAG,"name "+ book.getName());

        }catch (Exception ex){
            ex.printStackTrace();
            Log.i(TAG, "Booo "+ex.getMessage());
        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(),bookList);

        recyclerView.setAdapter(recyclerAdapter);





        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.ic_divider_line));
        recyclerView.addItemDecoration(dividerItemDecoration);



       // MainActivity mainActivity = new MainActivity();
      //  mainActivity.abc(bookList);



        return view;



    }


}
