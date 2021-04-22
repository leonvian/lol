package lvc.com.lolinterpreter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isOver16()
    }

    private fun isOver16() {

        val age = 35

        val medianAge = age - 10

        if (age > 16) {
            Toast.makeText(this, "Maior de 16!", Toast.LENGTH_LONG).show()
            if (age > 30) {
                Toast.makeText(this, "Nossa muito velho!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Menor de 16!", Toast.LENGTH_LONG).show()
        }

    }

}
