package com.example.starwarsretrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarsretrofit.R;
import com.example.starwarsretrofit.model.Personaje;

import java.util.List;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeAdapterHolder>  {
    private List<Personaje> personajes;
    private OnItemClickListener itemClickListener;

    public PersonajeAdapter(List<Personaje> personajes, OnItemClickListener itemClickListener) {
        this.personajes = personajes;
        this.itemClickListener = itemClickListener;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonajeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new PersonajeAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeAdapterHolder holder, int position) {
        holder.textViewName.setText(personajes.get(position).getName());
        holder.textViewHeight.setText(personajes.get(position).getHeight());
        holder.bind(this.personajes.get(position),this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    public class PersonajeAdapterHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewHeight;

        public PersonajeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewHeight = itemView.findViewById(R.id.textViewHeight);
        }
        public void bind(final Personaje p, final OnItemClickListener itemClickListener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(p, getAdapterPosition());
                }
            });
        }



    }

}
