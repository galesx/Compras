package com.example.compras.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.compras.R
import com.example.compras.model.Producto

class CarritoAdapter(var context: Context, var lista: ArrayList<Producto>) : RecyclerView.Adapter<CarritoAdapter.Mycarritoholder>(){

    class Mycarritoholder (item : View): ViewHolder(item){
        var imagen : ImageView
        var nombre : TextView
        var precio : TextView
        init {
            imagen = item.findViewById(R.id.imagen_prodcuto_carrito)
            nombre = item.findViewById(R.id.nombre_producto_carrito)
            precio = item.findViewById(R.id.precio_producto_carrito)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mycarritoholder {
        var vista : View = LayoutInflater.from(context).inflate(R.layout.filas_carrito,parent,false)
        return Mycarritoholder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: Mycarritoholder, position: Int) {
        var producto = lista[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = producto.precio.toString()+" €"
        Glide.with(context).load(producto.imagen).into(holder.imagen)
    }

}