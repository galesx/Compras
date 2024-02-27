package com.example.compras.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.compras.R
import com.example.compras.R.color
import com.example.compras.model.Producto

class ProductAdapter(var lista: ArrayList<Producto>,
                     var contexto : Context):RecyclerView.Adapter<ProductAdapter.MyHolder>() {

    private lateinit var listener: OnProductoListener
    init {
        listener = contexto as OnProductoListener
    }
    class MyHolder(item: View): ViewHolder(item){
        var imagen : ImageView
        var nombre : TextView
        var precio : TextView
        var botonCarrito : Button

        init {
            imagen = item.findViewById(R.id.imagen_prodcuto)
            nombre = item.findViewById(R.id.nombre_producto)
            precio = item.findViewById(R.id.precio_producto)
            botonCarrito = item.findViewById(R.id.agregar_carrito)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista:View = LayoutInflater.from(contexto).inflate(R.layout.filas_recycler,parent,false)
        return MyHolder(vista)
    }

    override fun getItemCount(): Int {
       return lista.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val objeto = lista[position]
        holder.nombre.text = objeto.nombre
        holder.precio.text = objeto.precio.toString()+" €"
        Glide.with(contexto).load(objeto.imagen).into(holder.imagen)
        holder.botonCarrito.setOnClickListener {
            listener.onProductSelected(objeto)
            holder.botonCarrito.setText("Articulo agregado")
            holder.botonCarrito.setBackgroundColor(contexto.resources.getColor(R.color.articulo_selected))
        }
    }
    fun agregarProducto(x:Producto){
        lista.add(x)
        notifyItemInserted(lista.size-1)
    }
    interface OnProductoListener{
        fun onProductSelected(objeto: Producto)
    }
}