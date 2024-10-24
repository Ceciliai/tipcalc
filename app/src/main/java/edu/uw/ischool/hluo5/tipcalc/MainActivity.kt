package edu.uw.ischool.hluo5.tipcalc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.DecimalFormat
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var selectedTipPercentage = 0.10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val buttonTip = findViewById<Button>(R.id.buttonTip)
        val spinnerTipPercentage = findViewById<Spinner>(R.id.spinnerTipPercentage)


        val tipOptions = arrayOf("10%", "15%", "18%", "20%")
        val tipValues = arrayOf(0.10, 0.15, 0.18, 0.20)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // 设置下拉列表的布局
        spinnerTipPercentage.adapter = adapter

        spinnerTipPercentage.setSelection(0)

        spinnerTipPercentage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                selectedTipPercentage = tipValues[position]
            }

           override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing here
            }
        }

        buttonTip.isEnabled = false


        editTextAmount.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {

                buttonTip.isEnabled = !s.isNullOrEmpty() && s.toString().toDoubleOrNull() != null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        buttonTip.setOnClickListener {
            val amountText = editTextAmount.text.toString()
            val amount = amountText.toDoubleOrNull()

            if (amount != null) {

                val tip = amount * selectedTipPercentage
                val formattedTip = DecimalFormat("$#.00").format(tip)

                Toast.makeText(this, "Tip: $formattedTip", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
