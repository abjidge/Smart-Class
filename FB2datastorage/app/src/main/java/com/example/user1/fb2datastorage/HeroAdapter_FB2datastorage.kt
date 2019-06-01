package com.example.user1.fb2datastorage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class HeroAdapter_FB2datastorage(val mCtx: Context, val layoutResId: Int, val heroList: List<Hero_FB2datastorage>)
    : ArrayAdapter<Hero_FB2datastorage>(mCtx,layoutResId,heroList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInfliter : LayoutInflater = LayoutInflater.from(mCtx);
        var view: View = layoutInfliter.inflate(layoutResId,null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName);
    //    val textViewtitle = view.findViewById<TextView>(R.id.textViewtitle);
        val textViewUpdate = view.findViewById<TextView>(R.id.textViewUpdate)

        val hero = heroList[position]

        textViewName.setText(hero.subject)
       // textViewtitle.setText(hero.tit)
       // Toast.makeText(mCtx, "Got", Toast.LENGTH_LONG).show()

        textViewUpdate.setOnClickListener {

            showupdateDialog(hero)
        }
        return view;


    }

    fun showupdateDialog(hero: Hero_FB2datastorage) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")


        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.layout_update_hero_fb2datastorage,null)
        val edittexts = view.findViewById<EditText>(R.id.subject)
        val edittextt = view.findViewById<EditText>(R.id.title)
        val edittextd = view.findViewById<EditText>(R.id.desc)

        edittexts.setText(hero.subject)
        edittextt.setText(hero.tit)
        edittextd.setText(hero.desc)

        builder.setView(view)
        builder.setPositiveButton("Update",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val dbHero = FirebaseDatabase.getInstance().getReference("heroes")
                val subject = edittexts.text.toString().trim();
                val title = edittextt.text.toString().trim();
                val desc = edittextd.text.toString().trim();

                if(subject.isEmpty()){
                    edittexts.error = "Please Enter  Subject Name"
                    edittexts.requestFocus()
                    return
                }

                if(title.isEmpty()){
                    edittextt.error = "Please Enter  title "
                    edittextt.requestFocus()
                    return
                }

                if(desc.isEmpty()){
                    edittextd.error = "Please Enter  Desc"
                    edittextd.requestFocus()
                    return
                }

                val hero = Hero_FB2datastorage(hero.id,subject,title,desc)

                dbHero.child(hero.id).setValue(hero)
                Toast.makeText(mCtx, "Hero_FB2datastorage Updated", Toast.LENGTH_LONG).show()
            }

        })

        builder.setNegativeButton("Back",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })
        val alert = builder.create()
        alert.show()

    }
}