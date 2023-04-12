package wolf.north.kalkulatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {

        //DEKLARACJA ELEMENTÓW UI
        private var textInput: TextView? = null

        //
        //FLAGA - SPRAWDZA CZY W POLU TEKSTOWYM ZNAJDUJE SIĘ NUMER NA KOŃCU
        var lastIsNumber : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INICJALIZACJA ZADEKLAROWANYCH ELEMENTÓW UI
        textInput = findViewById(R.id.textInput)
        //

    }
    fun wcisniecieLiczby (view: View){
        //BUTTON NALEŻY DO GRUPY VIEW, DEKLARUJEMY GO JAKO WARUNEK (JEŻELI VIEW TO PRZYCISK TO POBIERAMY JEGO TEKST)
        //FLAGA lastIsNumber ZMIENIA NA WARTOŚĆ TRUE PONIEWAŻ WCISKAMY LICZBE- WIĘC TAKA ZNAJDUJE SIĘ NA KOŃCU TEKSTU
        textInput?.append((view as Button).text)
        lastIsNumber = true
    }

    fun wcisniecieCLR (view: View){
        //FUNKCJONALNOŚĆ PRZYCISKU CLR - WCIŚNIĘCIE = USUNIĘCIE CAŁEGO TEKSTU
        textInput?.text=""
    }

    fun dodajCzescDziesietna(view: View){
        //FUNKCJONALNOŚĆ PRZYCISKU "." - WCIŚNIĘCIE DODAJE DO LICZBY CZĘŚĆ DZIESIĘTNĄ
        //lastIsNumber - FLAGA ZMNIENIA WARTOŚĆ NA FAŁSZ, PONIEWAŻ NA KOŃCU MAMY KROPKĘ, ALBO PUSTKI

        if(textInput?.text.toString().endsWith('.')){
            lastIsNumber = false
            return
        }else if(textInput?.text.toString().contains('.')){
            return
        }else if(textInput?.text.toString().isBlank()){
            lastIsNumber = false
            return
        }else{
            textInput?.append(".")
            lastIsNumber = false
        }
    }

    private fun czyWybranyOperator(poleTekstowe : String) : Boolean {
        //FUNKCJA KTÓRA WERYFIKUJE CZY TEKST ZAWIERA JUŻ ZNAK OPERATORA (DODAWANIE,ODEJMOWANIE,DZIELENIE,MNOŻENIE)
        //PIERWSZY WARUNEK SPRAWDZA CZY NIE ZACZYNA SIĘ OD MINUSA (LICZBY UJEMNE) = WTEDY IGNORUJE TEN WARUNEK BO CHCEMY OPEROWAĆ RÓWNIEŻ NA WARTOŚCIACH UJEMNYCH
        return if(poleTekstowe.startsWith('-')){
            false
        }else{
            poleTekstowe.contains('/')
                    || poleTekstowe.contains('*')
                    || poleTekstowe.contains('+')
                    || poleTekstowe.contains('-')
        }
    }

    fun wcisniecieOperatora(view: View){

        //FUNCKJONALNOŚĆ KTÓRA DODAJE DO TEKSTU OPERATOR MATEMATYCZNY - Z WARUNKAMI KTÓRE SPRAWIAJĄ
        //ŻE TYLKO JEDNA OPERACJA MOŻE BYĆ WYKONANA W JEDNYM CZASIE
        if (lastIsNumber && !czyWybranyOperator(textInput?.text.toString())){
            textInput?.append((view as Button).text)
            lastIsNumber = false
        }
    }
}