package com.example.isaac.plantillam8uf1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PlatosAdapter extends RecyclerView.Adapter<PlatosAdapter.ViewHolderPlatos> {

    //Declaramos un ArrayList de objetos de tipo PlatosModel con la que inicializaremos el adapter
    ArrayList<PlatosModel> listaPlatos;

    public PlatosAdapter(ArrayList<PlatosModel> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    //Atencion en el siguiente metodo hemos cambiado el parametro int i por int posicion para que sea mas claro
    @NonNull
    @Override
    public PlatosAdapter.ViewHolderPlatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plato_item, null, false);
        return new ViewHolderPlatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatosAdapter.ViewHolderPlatos viewHolderPlatos, int position) {
        viewHolderPlatos.nombrePlato.setText("Nombre: "+listaPlatos.get(position).getNombre());
        viewHolderPlatos.ingredientesPlato.setText("Ingredientes: "+listaPlatos.get(position).getIngredientes());
        viewHolderPlatos.precioPlato.setText("Precio: "+listaPlatos.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public class ViewHolderPlatos extends RecyclerView.ViewHolder {
        TextView nombrePlato;
        TextView ingredientesPlato;
        TextView precioPlato;

        public ViewHolderPlatos(@NonNull View itemView) {
            super(itemView);
            nombrePlato = itemView.findViewById(R.id.nombrePlatoID);
            ingredientesPlato = itemView.findViewById(R.id.ingredientesPlatoID);
            precioPlato = itemView.findViewById(R.id.precioPlatoID);
        }
    }
}
