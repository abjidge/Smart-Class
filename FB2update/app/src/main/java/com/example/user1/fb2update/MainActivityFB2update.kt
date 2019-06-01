package com.example.user1.fb2update

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*

class MainActivityFB2update : AppCompatActivity() {

    lateinit var sub: EditText
    lateinit var but: Button
    lateinit var listView: ListView

    lateinit var heroList: MutableList<HeroFB2update>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fb2update)

        heroList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("update")

        sub = findViewById(R.id.update)
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

                        val hero = h.getValue(HeroFB2update::class.java)

                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapterFB2update(this@MainActivityFB2update, R.layout.heroes_fb2update, heroList)
                    listView.adapter = adapter

                }

            }


        });

    }


    private fun saveHero() {
        var subf = sub.text.toString().trim()

        if (subf.isEmpty()) {
            sub.error = "Please enter Subject Name"
            return

        }

        val heroid = ref.push().key
        val hero = HeroFB2update(heroid, subf)

        ref.child(heroid).setValue(hero).addOnCompleteListener {

            Toast.makeText(applicationContext, "Subject save success", Toast.LENGTH_LONG).show()
        }
    }
}
