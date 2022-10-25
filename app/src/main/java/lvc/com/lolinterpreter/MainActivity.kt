package lvc.com.lolinterpreter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import lvc.com.languageofleo.Interpreter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testCode =
            "  variable integer x = 10;\n" +
                    "  variable integer y = 5;\n" +
                    "  variable integer z = x + y;\n" +
                    "   startProgram {\n" +
                    "     print(z + 1);\n" +
                    "  }"

        val interpreter = Interpreter()
        val logs = interpreter.execute(testCode)
    }

}
