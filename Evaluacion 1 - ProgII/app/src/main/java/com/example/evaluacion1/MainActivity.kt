package com.example.evaluacion1
import android.icu.text.NumberFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.evaluacion1.Modelo.ItemMesa
import com.example.evaluacion1.Modelo.CuentaMesa
import java.util.*
class MainActivity : AppCompatActivity() {
    private var CantidadPastel: EditText? = null
    private var TotalPastel: TextView? = null
    private var CantidadCazuela: EditText? = null
    private var TotalCazuela: TextView? = null
    private var TotalComida: TextView? = null
    private var Propina: TextView? = null
    private var TotalMesa: TextView? = null
    private var Switch: Switch? = null
    private var CalcularPropina = false
    private var totalMenu1 = 0
    private var totalMenu2 = 0
    private var SinPropina = 0
    private var TotalPropina = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CantidadPastel = findViewById(R.id.CantidadPastel)
        TotalPastel = findViewById(R.id.TotalPastel)
        CantidadCazuela = findViewById(R.id.CantidadCazuela)
        TotalCazuela = findViewById(R.id.TotalCazuela)
        TotalComida = findViewById(R.id.TotalComida)
        Propina = findViewById(R.id.txPropina)
        TotalMesa = findViewById(R.id.TotalMesa)
        Switch = findViewById(R.id.switch1)

        val textWatcherPastel: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cantidadpastel = CantidadPastel?.text.toString().toIntOrNull() ?: 0
                val cantidadcazuela = CantidadCazuela?.text.toString().toIntOrNull() ?: 0
                // Crear instancia
                val totalpastel =
                    ItemMesa("Pastel de Choclo", 12000, cantidadpastel).calcularSubTotal()
                totalMenu1 = totalpastel
                sumarTotalSinPropina()
                SumarPropina()
                SumaTotal()
                // Formatear valores Monetarios
                val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                val totalPastelFormateado = formatoMoneda.format(totalpastel)
                // Subtotal formateado en Textview
                TotalPastel?.setText("${totalPastelFormateado}")
            }
        }
        CantidadPastel?.addTextChangedListener(textWatcherPastel)

////////////////////////////////////////////////////////////////////////////////////////////////////
        val textWatcherCazuela: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cantidadpastel = CantidadPastel?.text.toString().toIntOrNull() ?: 0
                val cantidadcazuela = CantidadCazuela?.text.toString().toIntOrNull() ?: 0
                // Crear instancia
                val totalcazuela = ItemMesa("Cazuela", 10000, cantidadcazuela).calcularSubTotal()
                totalMenu2 = totalcazuela
                sumarTotalSinPropina()
                SumarPropina()
                SumaTotal()
                // Formatear valores Monetarios
                val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
                val totalCazuelaFormateado = formatoMoneda.format(totalcazuela)
                // Subtotal formateado en Textview
                TotalCazuela?.setText("${totalCazuelaFormateado}")
            }
        }
        CantidadCazuela?.addTextChangedListener(textWatcherCazuela)
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        Switch?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                CalcularPropina = true
                SumaTotal()
                Toast.makeText(this, "SI incluye propina", Toast.LENGTH_SHORT).show()
            } else {
                CalcularPropina = false
                SumaTotal()
                Toast.makeText(this, "NO incluye propina", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun sumarTotalSinPropina() {
        val sumaTotalSinPropina = CuentaMesa().TotalSinPropina(totalMenu1, totalMenu2)
        SinPropina = sumaTotalSinPropina
        // Formatear valores Monetarios
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        val TotalSinPropina = formatoMoneda.format(sumaTotalSinPropina)
        TotalComida?.setText("${TotalSinPropina}")
    }

    fun SumarPropina() {
        val SumaSinPropina = CuentaMesa().Propina(SinPropina)
        TotalPropina = SumaSinPropina
        // Formatear valores Monetarios
        val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        val propina = formatoMoneda.format(SumaSinPropina)
        Propina?.setText("${propina}")
    }

    fun SumaTotal() {
        if (CalcularPropina) {
            val total = SinPropina + TotalPropina
            val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            val totalmesa = formatoMoneda.format(total)
            TotalMesa?.setText("${totalmesa}")
        } else {
            val total = SinPropina
            val formatoMoneda = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            val totalmesa = formatoMoneda.format(total)
            TotalMesa?.setText("${totalmesa}")
        }
    }
}
