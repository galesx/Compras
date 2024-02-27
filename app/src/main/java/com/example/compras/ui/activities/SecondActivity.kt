package com.example.compras.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.compras.R
import com.example.compras.adapter.CarritoAdapter
import com.example.compras.adapter.ProductAdapter
import com.example.compras.databinding.ActivityMainBinding
import com.example.compras.databinding.ActivitySecondBinding
import com.example.compras.model.Producto
import java.io.Serializable

class SecondActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySecondBinding
    lateinit  var carritoAdapter: CarritoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySecondBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val lista: ArrayList<Producto> = intent.getParcelableArrayListExtra<Parcelable>("productos") as ArrayList<Producto>
        carritoAdapter = CarritoAdapter(this,lista)
        binding.recyclerCarrito.adapter = carritoAdapter
        binding.recyclerCarrito.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        setSupportActionBar(binding.toolbarCarrito)
        supportActionBar!!.title= "Tu carrito de compras"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carrito,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.voler->{
                finish()
            }
        }
        return true
    }

}