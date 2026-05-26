package com.tuempresa.osornomarket.core.repository

import com.tuempresa.osornomarket.core.model.Product

/**
 * Interfaz que define las operaciones permitidas para los productos.
 * Cumple con el Principio de Inversión de Dependencias (DIP).
 */
interface ProductRepository {
    suspend fun getAllProducts(): List<Product>
}
