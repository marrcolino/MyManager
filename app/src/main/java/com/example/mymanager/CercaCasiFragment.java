package com.example.mymanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CercaCasiFragment extends Fragment implements RecyclerAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private ArrayList<List> list = new ArrayList<List>();
    private EditText editTextCercaCaso;
    private Button buttonCercaCaso;
    public static String barraDiRicerca;
    private TextView textViewListaVuotaCercaCasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cerca_casi, container, false);

        editTextCercaCaso = (EditText) view.findViewById(R.id.editTextCercaCaso);
        buttonCercaCaso = (Button) view.findViewById(R.id.buttonCercaCaso);
        textViewListaVuotaCercaCasi = (TextView)view.findViewById(R.id.textViewListaVuotaCercaCasi);

        buttonCercaCaso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                buildListData();
                initRecyclerView(view);

                if(!list.isEmpty())
                {
                    textViewListaVuotaCercaCasi.setVisibility(View.GONE);
                }
                else
                {
                    textViewListaVuotaCercaCasi.setVisibility(View.VISIBLE);
                }

                //CODICE PER CHIUDERE LA TASTIERA
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

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
        if(!editTextCercaCaso.getText().toString().equals(""))
        {
            barraDiRicerca = editTextCercaCaso.getText().toString();
            Cursor cursor = MainActivity.DB.listaCasiDiStudioCerca(barraDiRicerca);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    List<String> arrlist = new ArrayList<String>();
                    arrlist.add(cursor.getString(0));
                    arrlist.add(cursor.getString(1));
                    arrlist.add(cursor.getString(2));
                    arrlist.add(cursor.getString(3));
                    arrlist.add(cursor.getString(4));
                    arrlist.add(cursor.getString(5));
                    arrlist.add(cursor.getString(6));
                    list.add(arrlist);
                }
            }
        }
        else
        {
            editTextCercaCaso.setError("Riempire il campo!");
            toastMessage("Riempire il campo di ricerca!");
        }
    }

    @Override
    public void onItemClick(List casi, int position) {
        Intent i = new Intent(getActivity(), InfoCasoDiStudio.class);
        i.putExtra("key", "cerca$"+Integer.toString((position)));
        startActivity(i);
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }
}