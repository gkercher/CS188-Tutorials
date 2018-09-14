package com.beautifulbulldog.grace.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BulldogListFragment extends Fragment {

    private RecyclerView bulldogList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter bulldogAdapter;

    public BulldogListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bulldog_list, container, false);

        final ArrayList<Bulldog> bulldogs = new ArrayList<Bulldog>();
        bulldogList = (RecyclerView)view.findViewById((R.id.bulldog_list));

        Bulldog bulldog1 = new Bulldog();
        bulldog1.setAge("9");
        bulldog1.setName("Porterhouse");

        Bulldog bulldog2 = new Bulldog();
        bulldog2.setAge("2");
        bulldog2.setName("Drake");

        bulldogs.add(bulldog1);
        bulldogs.add(bulldog2);

        layoutManager = new LinearLayoutManager(getContext());
        bulldogList.setLayoutManager(layoutManager);

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bulldog bulldog = (Bulldog)bulldogs.get(position);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("bulldog", (Serializable)bulldog);
                startActivity(intent);
            }
        };

        bulldogAdapter = new BulldogAdapter(getContext(), bulldogs, listener);
        bulldogList.setAdapter(bulldogAdapter);


        return view;
    }

}
