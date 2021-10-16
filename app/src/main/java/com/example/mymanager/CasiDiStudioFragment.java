package com.example.mymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CasiDiStudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CasiDiStudioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CasiDiStudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CasiDiStudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CasiDiStudioFragment newInstance(String param1, String param2) {
        CasiDiStudioFragment fragment = new CasiDiStudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_casi_di_studio, container, false);

        // TODO Auto-generated method stub
        ImageButton btn = (ImageButton) view.findViewById(R.id.buttonCreaCaso);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreaCasoDiStudio.class));

                /*
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CreaCasoDiStudioFragment();
                Fragment fragment1 = new CasiDiStudioFragment();
                Fragment fragmentOld = fragmentManager.findFragmentById(R.id.nav_host_fragment);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                /*fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fragmentTransaction.remove(fragment1);
                fragmentTransaction.commit();*/

                /*fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmentOld);
                fragmentTransaction.commit();*/

                /*CreaCasoDiStudioFragment nextFrag= new CreaCasoDiStudioFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();*/

                //toastMessage("Click");
            }
        });

        return view;
    }
}