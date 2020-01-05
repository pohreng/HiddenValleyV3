package com.example.hiddenvalleyv3

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.after_login.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.user_registration.*
import kotlinx.android.synthetic.main.user_registration.pass
import kotlinx.android.synthetic.main.user_registration.username

class  MainActivity : AppCompatActivity() {

    lateinit var handler: AccDatabase

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

        back_button.setOnClickListener{
            showHome()
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
                    }
            }
            else {
                Toast.makeText(this, "Username Or Password Incorrect", Toast.LENGTH_SHORT).show()
                showHome()
            }
        }
    }

    private fun showUserReg(){
        registration_layout.visibility=View.VISIBLE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showLogin(){
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.VISIBLE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showHome(){
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.VISIBLE
        afterLogin_layout.visibility=View.GONE
    }
    private fun afterLoginPages(){
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.VISIBLE
    }
    private fun showNumGame(){
        number_game.visibility=View.VISIBLE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showLetGame(){
        letter_game.visibility=View.VISIBLE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showMatchGame(){
        match_game.visibility=View.VISIBLE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
    private fun showCalGame(){
        cal_game.visibility=View.VISIBLE
        registration_layout.visibility=View.GONE
        login_layout.visibility=View.GONE
        main123.visibility=View.GONE
        afterLogin_layout.visibility=View.GONE
    }
}
