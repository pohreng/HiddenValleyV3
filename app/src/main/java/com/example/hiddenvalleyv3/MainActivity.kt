package com.example.hiddenvalleyv3

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.after_login.*
import kotlinx.android.synthetic.main.cal_game.*
import kotlinx.android.synthetic.main.game.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.user_registration.*
import kotlinx.android.synthetic.main.user_registration.pass
import kotlinx.android.synthetic.main.user_registration.username
import java.util.*

class  MainActivity : AppCompatActivity() {

    lateinit var handler: AccDatabase
    lateinit var diceImage1 : ImageView
    lateinit var diceImage2 : ImageView

    var manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = AccDatabase(this)

        showHome()

        registration_button.setOnClickListener{
            showUserReg()
        }

        login_button.setOnClickListener{
            showLogin()
        }

        sign_button.setOnClickListener {
            if(username.text.toString().isNotEmpty()){
                if (handler.verifyUsername(username.text.toString())) {
                    if (pass.text.toString().isNotEmpty() && pass.text.toString() == password.text.toString()) {
                        handler.insertUserData(username.text.toString(), pass.text.toString())
                        showHome()
                    } else {
                        Toast.makeText(this, "Incorrect Second Password", Toast.LENGTH_SHORT).show()
                        showUserReg()
                    }
                } else
                    Toast.makeText(this, "Username Been Used", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show()
        }

        login.setOnClickListener{
            login()
        }
    }

    private fun login(){
        if(handler.userPresent(login_username.text.toString(),login_pass.text.toString())) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

            var data = handler.retrieveData(login_username.text.toString())

            afterLoginPages()

            afterUsername.text = ""
            for(i in 0..(data.size-1)){
                afterUsername.append(data.get(i).username)
            }

            val clickerLetter = findViewById<ImageView>(R.id.letterIMG)
            val clickerNumber = findViewById<ImageView>(R.id.numberIMG)
            val clickerMatch = findViewById<ImageView>(R.id.matchIMG)
            val clickerCal = findViewById<ImageView>(R.id.calculateIMG)

            clickerLetter.setOnClickListener{
                showLetGame()
            }
            clickerNumber.setOnClickListener{
                showNumGame()
            }
            clickerMatch.setOnClickListener{
                showMatchGame()
            }
            clickerCal.setOnClickListener{
                showCalGame()
                diceImage1 = findViewById(R.id.dice_img1)
                diceImage2 = findViewById(R.id.dice_img2)

                diceImage1.setImageResource(R.drawable.dice_1)
                diceImage2.setImageResource(R.drawable.dice_1)

                roll_button.setOnClickListener{rollDice()}
            }
        }else {
            Toast.makeText(this, "Username Or Password Incorrect", Toast.LENGTH_SHORT).show()
            showLogin()
        }
    }


    private fun rollDice(){

        val randomInt1 = Random().nextInt(6)+1
        val randomInt2 = Random().nextInt(6)+1

        Toast.makeText(this,"button clicked",Toast.LENGTH_SHORT).show()

        val dice1 = when (randomInt1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage1.setImageResource(dice1)

        val dice2 = when (randomInt2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage2.setImageResource(dice2)
        answer_calculation.setOnClickListener{
            countTotal(randomInt1,randomInt2)
        }
    }
    private fun countTotal(num1 : Int,num2 : Int){
        val ans = cal_answer.text.toString().toInt()
        val total = num1 + num2
        if(ans == total) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Answer", Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun loopLogin(){
    var data = handler.retrieveData(login_username.text.toString())

    setContentView(R.layout.after_login)

    afterUsername.text = ""
    for(i in 0..(data.size-1)){
        afterUsername.append(data.get(i).username)
    }

    val clickerLetter = findViewById<ImageView>(R.id.letterIMG)
    val clickerNumber = findViewById<ImageView>(R.id.numberIMG)
    val clickerMatch = findViewById<ImageView>(R.id.matchIMG)
    val clickerCal = findViewById<ImageView>(R.id.calculateIMG)

    clickerLetter.setOnClickListener{
        setContentView(R.layout.game)
        showLetterGame()
        back_home.setOnClickListener {
            login()
        }
    }
    clickerNumber.setOnClickListener{
        setContentView(R.layout.game)
        showNumberGame()
        back_home.setOnClickListener {
            login()
        }
    }
    clickerMatch.setOnClickListener{
        setContentView(R.layout.game)
        showMatchGame()
        back_home.setOnClickListener {
            login()
        }
    }
    clickerCal.setOnClickListener{
        setContentView(R.layout.game)
        showCalGame()
        back_home.setOnClickListener {
            login()
        }
    }
}*/
    /*private fun showCalGame(){
        val transaction = manager.beginTransaction()
        val fragment = Cal_game_fragment()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/
    /*private fun showLetterGame(){
        val transaction = manager.beginTransaction()
        val fragment = Letter_game_fragment()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/
    /*private fun showMatchGame(){
        val transaction = manager.beginTransaction()
        val fragment = Match_game_fragment()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/
    /* private fun showNumberGame(){
        val transaction = manager.beginTransaction()
        val fragment = Number_game_fragment()
        transaction.replace(R.id.fragment_holder,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/

    private fun showUserReg(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.VISIBLE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showLogin(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.VISIBLE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showHome(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.VISIBLE
        afterLogin_layout.visibility=View.GONE
    }
    private fun afterLoginPages(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.VISIBLE
    }

    private fun showNumGame(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.VISIBLE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showLetGame(){
        let_game.visibility=View.VISIBLE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showMatchGame(){
        let_game.visibility=View.GONE
        match_game.visibility=View.VISIBLE
        num_game.visibility=View.GONE
        cal_game.visibility=View.GONE
        match_game.visibility=View.GONE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showCalGame(){
        let_game.visibility=View.GONE
        match_game.visibility=View.GONE
        num_game.visibility=View.GONE
        cal_game.visibility=View.VISIBLE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
}
