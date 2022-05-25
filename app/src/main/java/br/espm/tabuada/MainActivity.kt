package br.espm.tabuada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var rightText: TextView;
    lateinit var leftText: TextView;
    lateinit var ratingBar: RatingBar;
    lateinit var input: EditText;
    lateinit var button: Button;

    var expectedResult: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rightText = findViewById(br.espm.tabuada.R.id.rightNumber);
        rightText.textSize = 32F;
        leftText = findViewById(br.espm.tabuada.R.id.leftNumber);
        leftText.textSize = 32F;
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setIsIndicator(true);
        input = findViewById(R.id.inputNumber);
        input.gravity = Gravity.CENTER_HORIZONTAL;
        button = findViewById(R.id.resultButton);

        generateNumbers();
        button.setOnClickListener() {
            checkResponse();
        }
    }

    private fun generateNumbers() {
        val num1 = Random.nextInt(1,9);
        val num2 = Random.nextInt(1,9);
        expectedResult = num1 * num2;
        rightText.text = num1.toString();
        leftText.text = num2.toString();
    }

    private fun checkResponse(){
        if(input.text.isEmpty()) {
            return;
        }
        // checar resultado
        val builder = AlertDialog.Builder(this);
        builder.setTitle("Resultado");
        if(input.text.toString().toInt() == expectedResult){
            builder.setMessage("Acertou!!! Uhuul");
            //add estrela
            changeRating(1);
        } else {
            builder.setMessage("Errrrrrrrrrouuu")
            //remove estrela
            changeRating(-1);
        }
        builder.setNeutralButton("Legal"){dialog, which -> dialog.cancel()};

        // dar resultado se alcancou 5 pontos
        if(ratingBar.rating >= 5) {
            builder.setTitle("PARABÉNS!!!");
            builder.setMessage("Você venceu o jogo.");
            builder.setNeutralButton("UHUL, I'M A WINNER"){dialog, which -> dialog.cancel(); resetGame()};
            builder.show();
            return;
        }
        builder.show();

        // gerar numeros novamente
        generateNumbers();
        input.text.clear();
    }

    private fun changeRating(points: Int) {
        if(points < 0 && ratingBar.rating == 0F) {
            return;
        }
        ratingBar.rating += points;
    }

    private fun resetGame() {
        input.text.clear();
        ratingBar.rating = 0F;
        generateNumbers();
    }
}