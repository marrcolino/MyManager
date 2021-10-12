package com.example.mymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements RecyclerAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Casi> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buildListData();
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        list.add(new Casi("234","MyManager","Android","Zorro"));
        list.add(new Casi("1234","AppOne","Modelli","Belen"));
        list.add(new Casi("5646","RealMe","PIU","Onorevole"));
    }

    @Override
    public void onItemClick(Casi casi) {

        Fragment fragment = DettaglioFragment.newInstance(casi.getId(), casi.getCasiDiStudio());
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, "DettaglioFragment");
        transaction.addToBackStack(null);
        transaction.commit();

    }

}