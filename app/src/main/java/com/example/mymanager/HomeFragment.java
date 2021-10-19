package com.example.mymanager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private void buildListData(){
        //LISTA CASI DI STUDIO DELL'UTENTE LOGGATO
        Cursor cursor = MainActivity.DB.listaCasiDiStudio(MainActivity.utenteLoggato.matricola);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()) {
                list.add(new Casi(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            }
        }
        /*for(int i = 1; i<=cursor.getCount(); i++)
        {
            cursor.move(i);
            list.add(new Casi(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)));
        }*/
    }

    @Override
    public void onItemClick(Casi casi) {

        Fragment fragment = DettaglioFragment.newInstance(casi.getId(), casi.getNome());
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment, "DettaglioFragment");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}