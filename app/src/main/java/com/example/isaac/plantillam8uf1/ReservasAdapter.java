package com.example.isaac.plantillam8uf1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ReservasViewHolder> {

    //El adapter recibira una lista de objetos de tipo HacerReservaModel
    ArrayList<HacerReservaModel> listaReservas;

    public ReservasAdapter(ArrayList<HacerReservaModel> listaReservas) {
        this.listaReservas = listaReservas;
    }

    @NonNull
    @Override
    public ReservasAdapter.ReservasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Inflamos la vista de cada elemento de la recycler view con la vista reserva_item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reserva_item, null, false);
        return new ReservasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasAdapter.ReservasViewHolder reservasViewHolder, int position) {
        reservasViewHolder.verFecha.setText("Fecha: "+listaReservas.get(position).getFecha());
        reservasViewHolder.verComensales.setText("Personas"+listaReservas.get(position).getComensales());
    }

    @Override
    public int getItemCount() {
        //La cantidad de elemento que contendra la recycler sera equivalte al num de la lista
        return listaReservas.size();
    }

    public class ReservasViewHolder extends RecyclerView.ViewHolder {
        //Declaramos las variables de la vista
        TextView verFecha;
        TextView verComensales;

        public ReservasViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asignamos las variables de la vista a los elementos de la reserva_item.xml
            verFecha = itemView.findViewById(R.id.verFechaID);
            verComensales = itemView.findViewById(R.id.verComensalesID);

        }
    }
}
