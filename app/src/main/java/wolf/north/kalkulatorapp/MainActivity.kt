package wolf.north.kalkulatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

        //DEKLARACJA ELEMENTÓW UI
        private var textInput: TextView? = null
        //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INICJALIZACJA ZADEKLAROWANYCH ELEMENTÓW UI
        textInput = findViewById(R.id.textInput)
        //

    }
    fun wcisniecieLiczby (view: View){
        //BUTTON NALEŻY DO GRUPY VIEW, DEKLARUJEMY GO JAKO WARUNEK (JEŻELI VIEW TO PRZYCISK TO POBIERAMY JEGO TEKST)
        textInput?.append((view as Button).text)
    }

    fun wcisniecieCLR (view: View){
        //FUNKCJONALNOŚĆ PRZYCISKU CLR - WCIŚNIĘCIE = USUNIĘCIE CAŁEGO TEKSTU
        textInput?.text=""
    }

}