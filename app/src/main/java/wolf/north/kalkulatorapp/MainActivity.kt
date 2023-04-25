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


        //FLAGA - SPRAWDZA CZY W POLU TEKSTOWYM ZNAJDUJE SIĘ NUMER NA KOŃCU
        var lastIsNumber : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //INICJALIZACJA ZADEKLAROWANYCH ELEMENTÓW UI
        textInput = findViewById(R.id.textInput)
        //

    }

    //FUNKCJE PROGRAMU
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
        //PIERWSZY WARUNEK SPRAWDZA CZY W TEKŚCIE/NA KOŃCU NIE MA JUŻ TEGO OPERATORA

        textInput?.text.let {
            if (lastIsNumber && !czyWybranyOperator(it.toString())) {
                textInput?.append((view as Button).text)
                lastIsNumber = false
            }
        }
    }

    fun wcisniecieRownaSie(view: View){
        //FUNKCJONALNOŚĆ PRZYCISKU RÓWNA SIĘ
        //PIERWSZY WARUNEK SPRAWDZA CZY W POLU ZNAJDUJĘ SIĘ LICZBA LUB CAŁE WYRAŻENIE NP(5-2)
        //A NASTĘPNIE ROBI Z LICZBY STRINGA KTÓREGO RODZIELA NA DWA WYRAŻENIA (1 -+/* 2)
        //A NASTĘPNIE WYKONUJE OPERACJĘ ARYTMETYCZNĄ PRZEKSZTAŁCAJĄC JE NA DOUBLE A POTEM DO STRINGA
        //KTÓREGO DODAJE DO POLA JAKO WYNIK KOŃCOWY OPERACJI

        if(lastIsNumber){

            var textWartosc = textInput?.text.toString()
            var prefixLiczby = ""

            try{
                if(textWartosc.startsWith("-")){
                    prefixLiczby = "-"
                    textWartosc = textWartosc.substring(1)
                }
                //
                if(textWartosc.contains("-")) {

                    //SPLIT ROZDZIELA NA DWA WYRAŻENIA, PIERWSZE JEST DOPÓKI NIE SPOTKA LIMITERA(operator)
                    var rozdzielWartosc = textWartosc.split("-")

                    var pierwszeWyrazenie = rozdzielWartosc[0] // (x - y) - wartość x
                    var drugieWyrazenie = rozdzielWartosc[1] // (x - y) - wartość y

                    if(prefixLiczby.isNotEmpty()){
                        //JEZELI PIERWSZA LICZBA BYŁA UJEMNA TO ZAMIENIAMY JĄ NA STRINGA I DODAJEMY PREFIX NA POCZATKU
                        pierwszeWyrazenie = prefixLiczby + pierwszeWyrazenie
                    }

                    //KALKULATOR WYKONUJE SWOJE ZADANIE Z OPERACJĄ ODEJMOWANIA
                    textInput?.text =
                        usuniecieCzesciDziesietnej((pierwszeWyrazenie.toDouble() - drugieWyrazenie.toDouble()).toString())

                }
                else if (textWartosc.contains("+")){

                    //SPLIT ROZDZIELA NA DWA WYRAŻENIA, PIERWSZE JEST DOPÓKI NIE SPOTKA LIMITERA(operator)
                    var rozdzielWartosc = textWartosc.split("+")

                    var pierwszeWyrazenie = rozdzielWartosc[0] // (x + y) - wartość x
                    var drugieWyrazenie = rozdzielWartosc[1] // (x + y) - wartość y

                    if(prefixLiczby.isNotEmpty()){
                        //JEZELI PIERWSZA LICZBA BYŁA UJEMNA TO ZAMIENIAMY JĄ NA STRINGA I DODAJEMY PREFIX NA POCZATKU
                        pierwszeWyrazenie = prefixLiczby + pierwszeWyrazenie
                    }

                    //KALKULATOR WYKONUJE SWOJE ZADANIE Z OPERACJĄ DODAWANIA
                    textInput?.text =
                        usuniecieCzesciDziesietnej((pierwszeWyrazenie.toDouble() + drugieWyrazenie.toDouble()).toString())

                }
                else if (textWartosc.contains("*")){

                    //SPLIT ROZDZIELA NA DWA WYRAŻENIA, PIERWSZE JEST DOPÓKI NIE SPOTKA LIMITERA(operator)
                    var rozdzielWartosc = textWartosc.split("*")

                    var pierwszeWyrazenie = rozdzielWartosc[0] // (x * y) - wartość x
                    var drugieWyrazenie = rozdzielWartosc[1] // (x * y) - wartość y

                    if(prefixLiczby.isNotEmpty()){
                        //JEZELI PIERWSZA LICZBA BYŁA UJEMNA TO ZAMIENIAMY JĄ NA STRINGA I DODAJEMY PREFIX NA POCZATKU
                        pierwszeWyrazenie = prefixLiczby + pierwszeWyrazenie
                    }

                    //KALKULATOR WYKONUJE SWOJE ZADANIE Z OPERACJĄ MNOŻENIA
                    textInput?.text =
                        usuniecieCzesciDziesietnej((pierwszeWyrazenie.toDouble() * drugieWyrazenie.toDouble()).toString())

                }
                else if (textWartosc.contains("/")){

                    //SPLIT ROZDZIELA NA DWA WYRAŻENIA, PIERWSZE JEST DOPÓKI NIE SPOTKA LIMITERA(operator)
                    var rozdzielWartosc = textWartosc.split("/")

                    var pierwszeWyrazenie = rozdzielWartosc[0] // (x / y) - wartość x
                    var drugieWyrazenie = rozdzielWartosc[1] // (x / y) - wartość y

                    if(prefixLiczby.isNotEmpty()){
                        //JEZELI PIERWSZA LICZBA BYŁA UJEMNA TO ZAMIENIAMY JĄ NA STRINGA I DODAJEMY PREFIX NA POCZATKU
                        pierwszeWyrazenie = prefixLiczby + pierwszeWyrazenie
                    }

                    //KALKULATOR WYKONUJE SWOJE ZADANIE Z OPERACJĄ DZIELENIA
                    textInput?.text =
                        usuniecieCzesciDziesietnej((pierwszeWyrazenie.toDouble() / drugieWyrazenie.toDouble()).toString())
                }
            }catch(e: java.lang.ArithmeticException){
                e.printStackTrace()
            }
        }

    }

    private fun usuniecieCzesciDziesietnej(wynik: String) : String {
        var wartosc = wynik

        if(wartosc.contains(".0")) {
            //USUNIECIE CZESCI DZIESIETNEJ, STRING WARTOŚĆ TO LICZBA BEZ .0
            wartosc = wynik.substring(0, wynik.length - 2)
        }
        return wartosc
    }

}