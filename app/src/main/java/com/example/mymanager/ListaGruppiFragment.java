package com.example.mymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListaGruppiFragment extends Fragment implements RecyclerAdapterGruppi.ItemClickListenerGruppi{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterGruppi adapter;
    private ArrayList<List> list = new ArrayList<List>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_gruppi, container, false);

        buildListData();
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {

        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterGruppi(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        //LISTA GRUPPI DELL'UTENTE LOGGATO
        Cursor cursor = MainActivity.DB.listaGruppi(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                List<String> arrlist = new ArrayList<String>();
                arrlist.add(cursor.getString(0));
                arrlist.add(cursor.getString(1));
                arrlist.add(cursor.getString(2));
                arrlist.add(cursor.getString(3));
                arrlist.add(cursor.getString(4));
                arrlist.add(cursor.getString(5));
                //arrlist.add(cursor.getString(6));
                list.add(arrlist);
            }
        }
    }

    @Override
    public void onItemClick(List gruppi, int position) {
        Intent e = new Intent(getActivity(), InfoGruppo.class);
        e.putExtra("key2", Integer.toString((position)));
        startActivity(e);
    }


}