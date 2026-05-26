package com.tuempresa.osornomarket.core.model

/**
 * Representa un producto en el marketplace.
 * Se agregan valores por defecto para que Firestore pueda instanciar la clase.
 */
data class Product(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Int = 0,
    val sellerId: String = "",
    val imageUrl: String = ""
)
