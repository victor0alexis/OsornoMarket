package com.tuempresa.osornomarket.core.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tuempresa.osornomarket.core.model.Product
import com.tuempresa.osornomarket.core.repository.ProductRepository
import kotlinx.coroutines.tasks.await

/**
 * Implementación concreta del repositorio usando Firebase Firestore.
 * Cumple con el Principio de Responsabilidad Única (SRP) al manejar solo datos.
 */
class FirebaseProductRepository : ProductRepository {
    
    private val db = Firebase.firestore

    override suspend fun getAllProducts(): List<Product> {
        return try {
            val result = db.collection("products").get().await()
            result.documents.mapNotNull { document ->
                document.toObject(Product::class.java)?.copy(id = document.id)
            }
        } catch (e: Exception) {
            // Aquí podrías relanzar el error o registrarlo
            emptyList()
        }
    }
}
