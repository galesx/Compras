package com.example.compras.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.compras.R
import com.example.compras.adapter.CarritoAdapter
import com.example.compras.adapter.ProductAdapter
import com.example.compras.databinding.ActivityMainBinding
import com.example.compras.model.Categoria
import com.example.compras.model.Producto
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ProductAdapter.OnProductoListener  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var listaObjetos : ArrayList<Producto>
    private lateinit var listaCategorias: ArrayList<Categoria>
    private lateinit var intent: Intent
    private lateinit var listaCarrito : ArrayList<Producto>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listaCarrito = ArrayList()
        listaObjetos = ArrayList()
        listaCategorias = ArrayList()
        productAdapter = ProductAdapter(listaObjetos,this)
        rellenarSpiner()
        rellenarLista()

        binding.spinnerProductos.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.recycler.adapter = productAdapter
        binding.recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        setSupportActionBar(binding.toolbarMenu)
        supportActionBar?.title = "Bienvenido a tienda Dummy"

    }

    fun rellenarSpiner(){

        val peticionCategorias : JsonArrayRequest = JsonArrayRequest("https://dummyjson.com/products/categories",
            {
                val categorias : JSONArray = it
                listaCategorias.add(Categoria("Selecciona una categoría"))
                for (i in 0 until it.length()){
                    val categoria = categorias.getString(i)
                    listaCategorias.add(Categoria(categoria))
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaCategorias.map { it.nombre })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerProductos.adapter = adapter
            },
            {
                Snackbar.make(binding.root,"Error con el json categorias",Snackbar.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(applicationContext).add(peticionCategorias)
    }
    fun rellenarLista(){
        val peticion : JsonObjectRequest = JsonObjectRequest("https://dummyjson.com/products",
            {
                val objetos : JSONArray = it.getJSONArray("products")
                for (i in 0 until objetos.length()){
                    val objeto: JSONObject = objetos.getJSONObject(i)
                    val producto : Producto =
                        Producto(objeto.getString("category"),
                        objeto.getString("title"),
                        objeto.getInt("price"),
                        objeto.getJSONArray("images").getString(0))
                    productAdapter.agregarProducto(producto)
                }
            },
            {
                Snackbar.make(binding.root,"Error en la conexion",Snackbar.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(applicationContext).add(peticion)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.carrito_compras->{
                intent = Intent(this,SecondActivity::class.java)
                intent.putExtra("productos",listaCarrito)
                startActivity(intent)
            }
        }
        return true
    }
    override fun onProductSelected(objeto: Producto) {
        listaCarrito.add(objeto)
    }

}