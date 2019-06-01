package com.example.user1.localstorage1

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
//import sun.text.normalizer.UTF16.append
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine



class MainActivity : AppCompatActivity() {

    lateinit var mEditText: EditText
    lateinit var mEditText1: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById(R.id.edit_text)
        mEditText1 = findViewById(R.id.edit_text1)
    }

    fun save(v: View) {
        val text = mEditText.text.toString()+" "+mEditText1.text.toString()
        var fos: FileOutputStream? = null

        try {
            val fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)

            fos.write(text.toByteArray());

            mEditText.text.clear()
            Toast.makeText(this, "Saved to $filesDir/$FILE_NAME",
                    Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    fun load(v: View) {
        var fis: FileInputStream? = null

        try {
            fis = openFileInput(FILE_NAME)
            val isr = InputStreamReader(fis!!)
            val br = BufferedReader(isr)
            val sb = StringBuilder()

//            var text:String?=""
//            while (text!= null) {
//                sb.append(text).append("\n")
//            }
            var text: String?


            do{
                text=br.readLine()
                if(text==null)
                    break
                sb.append(text).append("\n")
             //   Toast.makeText(this, "Saved to $text",Toast.LENGTH_LONG).show()

            }while(true)

            mEditText.setText(sb.toString())

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {
        private val FILE_NAME = "example.txt"
    }
}