package com.example.user1.fbupdate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*

class MainActivity_FBupdate : AppCompatActivity() {


    lateinit var listView: ListView

    lateinit var heroList: MutableList<Hero_FBupdate>
    lateinit var ref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fbupdate)

        heroList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("update")


        listView = findViewById(R.id.listview)



        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if(p0!!.exists()){
                    heroList.clear()

                    for(h in p0.children){

                        val hero = h.getValue(Hero_FBupdate::class.java)
                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapter_FBupdate(this@MainActivity_FBupdate,R.layout.heroes_fbupdate,heroList)
                    listView.adapter = adapter

                }

            }


        });
    }
}
