package adapater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shubham.bookstall.Descriptionactivity
import com.shubham.bookstall.R
import com.squareup.picasso.Picasso
import model.Book

//@SuppressLint("NotConstructor")
class Dashboardrecyle(val context: Context, val listitem : ArrayList<Book>) : RecyclerView.Adapter<Dashboardrecyle.Viewholder>() {


    class Viewholder (view: View) : RecyclerView.ViewHolder(view) {

        val textbookname =view.findViewById<TextView>(R.id.text1)
        val textbookauhor=view.findViewById<TextView>(R.id.text2)   // find id in kotlin recylerview
        val textbookrating=view.findViewById<TextView>(R.id.text3)
        val textbookcost=view.findViewById<TextView>(R.id.text4)
        val imgaebook=view.findViewById<ImageView>(R.id.img1)
        //val startimages=view.findViewById<ImageView>(R.id.img2)
        val itemsonbook=view.findViewById<RelativeLayout>(R.id.parent)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {


        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.dashboarditem, parent, false)

        return Viewholder(itemView)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val Book=listitem[position]
      /*  holder.textbookname.setText(listitem.get(position).Bookname)
        holder.textbookauhor.setText(listitem.get(position).Bookauthorname)
        holder.textbookcost.setText(listitem.get(position).Bookprice)
        holder.textbookrating.setText(listitem.get(position).Bookrating)
       // holder.imgaebook.setImageResource(listitem.get(position).image)

        Picasso.get().load(listitem[position].image).into(holder.imgaebook);

       */
        holder.textbookname.text=Book.Bookname
        holder.textbookauhor.text=Book.Bookauthorname
        holder.textbookrating.text=Book.Bookrating
        holder.textbookcost.text=Book.Bookprice
        Picasso.get().load(Book.image).error(R.drawable.default_book_cover).into(holder.imgaebook);  // in this line the images will be lodaed using picasso liabary
        holder.itemsonbook.setOnClickListener {

            val intent= Intent(context, Descriptionactivity::class.java)
             intent.putExtra("book_id",Book.bookid)
            context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return listitem.size
    }


}