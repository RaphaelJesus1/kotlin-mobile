package br.espm.tabuada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var rightText: TextView = findViewById(R.id.rightNumber);
    var leftText: TextView = findViewById(R.id.leftNumber);
    var ratingBar: RatingBar = findViewById(R.id.ratingBar);
    var input: EditText = findViewById(R.id.inputNumber);
    var button: Button = findViewById(R.id.resultButton);

    var expectedResult: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button.setOnClickListener() {
            checkResponse();
        }
    }

    fun generateNumbers() {
        val num1 = Random.nextInt(1,9);
        val num2 = Random.nextInt(1,9);
        expectedResult = num1 * num2;
        rightText.text = num1.toString();
        leftText.text = num2.toString();
    }

    fun checkResponse(){
        // checar resultado
        val builder = AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        if(input.text.equals(expectedResult.toString())){
            builder.setMessage("Acertou!!! Uhuul");
            //add estrela
            changeRating(1);
        } else {
            builder.setMessage("Errrrrrrrrrouuu")
            //remove estrela
            changeRating(-1);
        }
        builder.setNeutralButton("Legal"){dialog, which -> dialog.cancel()};
        builder.show();

        // dar resultado se alcancou 5 pontos
        if(ratingBar.rating >= 5) {
            val builder = AlertDialog.Builder(this);
            builder.setTitle("PARABÉNS!!!");
            builder.setMessage("Você venceu o jogo.");
            builder.setNeutralButton("UHUL, I'M A WINNER"){dialog, which -> dialog.cancel(); resetGame()};
            builder.show();
            return;
        }

        // gerar numeros novamente
        generateNumbers();
        input.text.clear();
    }

    fun changeRating(points: Int) {
        if(points < 0 && ratingBar.rating == 0F) {
            return;
        }
        ratingBar.rating += points;
    }

    fun resetGame() {
        input.text.clear();
        ratingBar.rating = 0F;
        generateNumbers();
    }
}