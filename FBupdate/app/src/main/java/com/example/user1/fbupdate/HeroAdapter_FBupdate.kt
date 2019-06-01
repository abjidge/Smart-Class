package com.example.user1.fbupdate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HeroAdapter_FBupdate(val mCtx: Context, val layoutResId: Int, val heroList: List<Hero_FBupdate>)
    : ArrayAdapter<Hero_FBupdate>(mCtx,layoutResId,heroList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInfliter : LayoutInflater = LayoutInflater.from(mCtx);
        var view: View = layoutInfliter.inflate(layoutResId,null)
        val textViewName = view.findViewById<TextView>(R.id.textViewName)

     //   val textViewName = view.findViewById<TextView>(R.id.textViewName);

        val hero = heroList[position]

        textViewName.text = hero.subject

        return view;

    }
}