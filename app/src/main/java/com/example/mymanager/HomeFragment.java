package com.example.mymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private ArrayList<List> list = new ArrayList<List>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buildListData();
        initRecyclerView(view);

        //RICARICO LA VARIABILE GLOBALE
        Cursor cursor = MainActivity.DB.login(MainActivity.utenteLoggato.matricola, MainActivity.utenteLoggato.password);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                MainActivity.utenteLoggato.matricola = cursor.getString(0);
                MainActivity.utenteLoggato.nome = cursor.getString(1);
                MainActivity.utenteLoggato.cognome = cursor.getString(2);
                MainActivity.utenteLoggato.email = cursor.getString(3);
                MainActivity.utenteLoggato.password = cursor.getString(4);
                MainActivity.utenteLoggato.dataNascita = cursor.getString(5);
            }
        }

        return view;
    }

    private void initRecyclerView(View view) {

         recyclerView = view.findViewById(R.id.recyclerview);
         layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        //LISTA CASI DI STUDIO DELL'UTENTE LOGGATO
        if(MainActivity.utenteLoggato.matricola.charAt(0)!='0') {
            Cursor cursor = MainActivity.DB.listaCasiDiStudio(MainActivity.utenteLoggato.matricola);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    List<String> arrlist = new ArrayList<String>();
                    arrlist.add(cursor.getString(0));
                    arrlist.add(cursor.getString(1));
                    arrlist.add(cursor.getString(2));
                    arrlist.add(cursor.getString(3));
                    arrlist.add(cursor.getString(4));
                    arrlist.add(cursor.getString(5));
                    list.add(arrlist);
                }
            }
        }else
        {
            Cursor cursor = MainActivity.DB.listaCasiProf(MainActivity.utenteLoggato.matricola);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    List<String> arrlist = new ArrayList<String>();
                    arrlist.add(cursor.getString(0));
                    arrlist.add(cursor.getString(1));
                    arrlist.add(cursor.getString(2));
                    arrlist.add(cursor.getString(3));
                    arrlist.add(cursor.getString(4));
                    list.add(arrlist);
                }
            }
        }
    }

    @Override
    public void onItemClick(List casi, int position) {
        /*Fragment fragment = DettaglioFragment.newInstance(list.get(position).get(1).toString(), list.get(position).get(3).toString());
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, "DettaglioFragment");
        transaction.addToBackStack(null);
        transaction.commit();*/
        if(MainActivity.utenteLoggato.matricola.charAt(0)!='0') {
            Intent i = new Intent(getActivity(), InfoCasoDiStudio.class);
            i.putExtra("key", Integer.toString((position)));
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(getActivity(), InfoProfCasoDiStudio.class);
            i.putExtra("key", Integer.toString((position)));
            startActivity(i);
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}