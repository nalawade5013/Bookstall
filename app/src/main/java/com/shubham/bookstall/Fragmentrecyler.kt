package com.shubham.bookstall

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.bookstall.database.Bookenitites
import com.squareup.picasso.Picasso
import model.Book

class Fragmentrecyler(val context: Context,val Booklist : List<Bookenitites>) : RecyclerView.Adapter<Fragmentrecyler.Frameholder>() {


    class Frameholder(view : View): RecyclerView.ViewHolder(view){

        val imagebookname =view.findViewById<ImageView>(R.id.book1)
        val texthello=view.findViewById<TextView>(R.id.heloworld)
        val textandroid=view.findViewById<TextView>(R.id.android)
        val textprice=view.findViewById<TextView>(R.id.androidrs)
        val textrating=view.findViewById<TextView>(R.id.rating)
        val card=view.findViewById<CardView>(R.id.card)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Frameholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.single_row_xml, parent, false)

        return Frameholder(itemView)
    }

    override fun onBindViewHolder(holder: Frameholder, position: Int) {

        val book=Booklist[position]
        holder.texthello.text=book.Bookname
        holder.textandroid.text=book.Bookauthorname
        holder.textprice.text=book.Bookprice
        holder.textrating.text=book.Bookrating
        Picasso.get().load(book.image).error(R.drawable.default_book_cover).into(holder.imagebookname)

        holder.card.setOnClickListener {
            val intent = Intent(context, Descriptionfavourite::class.java)
           // intent.putExtra("book_id", book.bookid)
            intent.putExtra("book_id",book.book_id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return Booklist.size
    }
}