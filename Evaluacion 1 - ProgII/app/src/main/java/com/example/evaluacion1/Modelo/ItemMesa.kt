
package com.example.evaluacion1.Modelo

class ItemMesa(nombre: String, precio: Int, val cantidad: Int) : ItemMenu(nombre, precio) {

    fun calcularSubTotal(): Int {
        return precio * cantidad
    }
}