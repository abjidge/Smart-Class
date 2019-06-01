package com.example.user1.fb2datastorage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class MainActivity_FB2datastorage : AppCompatActivity() {

    lateinit var sub: EditText
    lateinit var tit: EditText
    lateinit var desc: EditText
    lateinit var but: Button
    lateinit var listView: ListView

    lateinit var heroList: MutableList<Hero_FB2datastorage>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fb2datastorage)

        heroList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("heroes")

        sub = findViewById(R.id.subject)
        desc = findViewById(R.id.desc)
        tit = findViewById(R.id.title)
        but = findViewById(R.id.submit)
        listView = findViewById(R.id.listview)

        but.setOnClickListener {

            saveHero()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0!!.exists()) {
                    heroList.clear()

                    for (h in p0.children) {

                        val hero = h.getValue(Hero_FB2datastorage::class.java)

                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapter_FB2datastorage(this@MainActivity_FB2datastorage, R.layout.heroes_fb2datastorage, heroList)
                    listView.adapter = adapter

                }

            }


        });
    }

    private fun saveHero() {
        var subf = sub.text.toString().trim()
        var titf = tit.text.toString().trim()
        var descf = desc.text.toString().trim()
        if (subf.isEmpty()) {
            sub.error = "Please enter Subject Name"
            // tit.error="Please enter Title"
            return

        }


        val heroid = ref.push().key

        val hero = Hero_FB2datastorage(heroid, subf,titf,descf)

        ref.child(heroid).setValue(hero).addOnCompleteListener {

            Toast.makeText(applicationContext, "Subject save success", Toast.LENGTH_LONG).show()
        }
    }
}