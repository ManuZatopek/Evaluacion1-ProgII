package com.example.evaluacion1.Modelo

class CuentaMesa {

   private var listaTotalSinPropina = mutableListOf<Int>()

    fun TotalSinPropina(totalMenu1:Int, totalMenu2:Int):Int {
        listaTotalSinPropina.add(totalMenu1)
        listaTotalSinPropina.add(totalMenu2)
        val total= listaTotalSinPropina.sum()
        return total
    }

    fun Propina(SinPropina:Int): Int {
        val DiezPorciento = SinPropina * (10 / 100.0)
        // val TotalConPropina = SinPropina + DiezPorciento
        return  DiezPorciento.toInt()
    }
}
