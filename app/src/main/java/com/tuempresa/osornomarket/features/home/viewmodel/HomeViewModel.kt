package com.tuempresa.osornomarket.features.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tuempresa.osornomarket.core.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val db = Firebase.firestore

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Obtenemos los documentos de la colección "products"
                val result = db.collection("products").get().await()
                
                // Mapeamos los documentos a objetos Product
                val productList = result.documents.mapNotNull { document ->
                    // convertimos el documento a Product y asignamos el ID manual de Firestore
                    document.toObject(Product::class.java)?.copy(id = document.id)
                }
                
                _products.value = productList
                Log.d("HomeViewModel", "Productos cargados: ${productList.size}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error al cargar productos", e)
                // Aquí podrías manejar un estado de error si lo deseas
            } finally {
                _isLoading.value = false
            }
        }
    }
}
