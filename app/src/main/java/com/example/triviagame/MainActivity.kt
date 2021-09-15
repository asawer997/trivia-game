package com.example.triviagame

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.triviagame.data.Trivia
import com.example.triviagame.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private val client= OkHttpClient()
    val options= ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
       request()
        getColorOfButton()
      binding.done.setOnClickListener {
         request()
          setColorOfButton()



      }
    }
    private fun request() {

            options.clear()
        val request= Request.Builder().url("https://opentdb.com/api.php?amount=1&difficulty=medium&type=multiple").build()

        client.newCall(request).enqueue(object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                e.message?.let { Log.i("MAIN_ACTIVITY",e.message.toString()) }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString->

                    val result= Gson().fromJson(jsonString,Trivia::class.java)


                    runOnUiThread{
                        binding.apply {
                            question.text = result.results.joinToString { it.question }
                            for (i in 0..2) {
                                options.add(result.results.joinToString { it.incorrect_answers[i] })
                            }

                                option1.text=result.results.joinToString { it.correct_answer }
                                option2.text=options[0]
                                option3.text=options[1]
                                option4.text=options[2]
                            }




                        }}
                }


        })
    }


    private  fun getColorOfButton(){
        binding.apply{
            option1.setOnClickListener {changeColorOfButton()}
            option2.setOnClickListener {changeColorOfButton()}
            option3.setOnClickListener {changeColorOfButton()}
            option4.setOnClickListener {changeColorOfButton()}


        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeColorOfButton(){
        binding.apply {

                option1.background = resources.getDrawable(R.drawable.button_correct)
                option2.background = resources.getDrawable(R.drawable.button_incorrect)
                option3.background = resources.getDrawable(R.drawable.button_incorrect)
                option4.background = resources.getDrawable(R.drawable.button_incorrect)


        }
    }
    private fun setColorOfButton(){
        binding.apply {

            option1.background = resources.getDrawable(R.drawable.button_shape)
            option2.background = resources.getDrawable(R.drawable.button_shape)
            option3.background = resources.getDrawable(R.drawable.button_shape)
            option4.background = resources.getDrawable(R.drawable.button_shape)


        }
    }


}
