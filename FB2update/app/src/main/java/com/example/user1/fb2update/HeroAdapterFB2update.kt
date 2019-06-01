package com.example.user1.fb2update

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HeroAdapterFB2update(val mCtx: Context, val layoutResId: Int, val heroList: List<HeroFB2update>)
    : ArrayAdapter<HeroFB2update>(mCtx,layoutResId,heroList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInfliter : LayoutInflater = LayoutInflater.from(mCtx);
        var view: View = layoutInfliter.inflate(layoutResId,null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName);

        val hero = heroList[position]

        textViewName.setText(hero.subject)


        return view;


    }

}