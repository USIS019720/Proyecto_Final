package com.example.appvoto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class adaptadorMensaje extends RecyclerView.Adapter<HoderMensaje> {

    private List<Mensaje> listMensaje = new ArrayList<>();
    private Context c;

    public adaptadorMensaje( Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m) {
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HoderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_mensaje,parent,false);
        return new HoderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HoderMensaje holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());
        holder.getHora().setText(listMensaje.get(position).getHora());

        if (listMensaje.get(position).getType_mensaje().equals("2")){
            holder.getFotoEnviada().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.GONE);
            Glide.with(c).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoEnviada());
        }else if(listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getFotoEnviada().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
