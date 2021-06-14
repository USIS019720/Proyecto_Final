package com.example.appvoto;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HoderMensaje extends RecyclerView.ViewHolder {

    private TextView nombre,mensaje,hora;
    private CircleImageView fotoMensaje;
    private ImageView fotoEnviada;

    public HoderMensaje(@NonNull View itemView) {
        super(itemView);
        fotoEnviada = (ImageView) itemView.findViewById(R.id.fotoEnviada);
        nombre = (TextView) itemView.findViewById(R.id.nombreMensaje);
        mensaje = (TextView) itemView.findViewById(R.id.mensajeMensaje);
        hora = (TextView) itemView.findViewById(R.id.horaMensaje);
        fotoMensaje  = (CircleImageView) itemView.findViewById(R.id.fotoPeril);
    }

    public ImageView getFotoEnviada() {
        return fotoEnviada;
    }

    public void setFotoEnviada(ImageView fotoEnviada) {
        this.fotoEnviada = fotoEnviada;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(CircleImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}
