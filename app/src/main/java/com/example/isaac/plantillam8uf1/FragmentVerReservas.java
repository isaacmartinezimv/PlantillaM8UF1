package com.example.isaac.plantillam8uf1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentVerReservas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentVerReservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVerReservas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //variables de elementos en pantalla
    ProgressBar spinner;
    RecyclerView recyclerView;

    //Variables de datos obtenidos de firebase
    ArrayList<HacerReservaModel> listaReservas;

    //variables de firebase
    FirebaseDatabase firebaseDatabase;

    //Variables para la recyclerView
    ReservasAdapter adapter;


    //Elemento raiz de la base de datos
    String databasePath = "reservas";

    private OnFragmentInteractionListener mListener;

    public FragmentVerReservas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentVerReservas newInstance(String param1, String param2) {
        FragmentVerReservas fragment = new FragmentVerReservas();
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
        View view = inflater.inflate(R.layout.fragment_ver_reservas, container, false);

        //Creamos un array vacio asociado a listaReservas
        listaReservas = new ArrayList<>();

        //Asociamos los elementos de la vista con sus IDs
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerID);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar1);

        //Nada mas iniciar la vista nos interesa que el spinner se vea
        spinner.setVisibility(View.VISIBLE);

        //Configuramos la recyclerView añadiendole el layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Creamos un adapter del tipo que hemos creado anteriormente y le pasamos la lista de reservas.
        //Este adapter que creamos se lo asignamos a nuestra recycler view
        adapter = new ReservasAdapter(listaReservas);
        recyclerView.setAdapter(adapter);

        //Obtener los datos del realTime DB, creamos una instancia de la database y obtenemos
        //la referencia de los hijos del elemento root, que en nuestro caso es reservas
        firebaseDatabase = FirebaseDatabase.getInstance();

        //Añadimos un event listener que contemple si se han añadido nuevos elementos
        firebaseDatabase.getReference().child(databasePath).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Declaramos un objeto de tipo HacerReservasModel con el que vamos a interpretar los datos
                HacerReservaModel reserva;

                //Parseamos los datos con el modelo que hemos creado
                reserva = dataSnapshot.getValue(HacerReservaModel.class);

                //Una vez tenemos los datos interpretados en un objeto tipo HacerReservaModel, lo añadimos a la listaReservas
                listaReservas.add(reserva);

                //Notificamos al adapter el cambio y seteamos el spiner en invisible
                adapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE);
            }


            //Metodos restantes de la recycler, no interesan de momento.
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
