package com.example.user1.fbdatastorage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class MainActivity_FBdatastorage : AppCompatActivity() {


    lateinit var listView: ListView

    lateinit var heroList: MutableList<Hero_FBdatastorage>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fbdatastorage)

        heroList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("heroes")


        listView = findViewById(R.id.listview)



        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if(p0!!.exists()){
                    heroList.clear()

                    for(h in p0.children){

                        val hero = h.getValue(Hero_FBdatastorage::class.java)
                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapter_FBdatastorage(this@MainActivity_FBdatastorage,R.layout.heroes_fbdatastorage,heroList)
                    listView.adapter = adapter

                }

            }


        });
    }


}
