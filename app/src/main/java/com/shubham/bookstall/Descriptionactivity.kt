package com.shubham.bookstall

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shubham.bookstall.R
import com.shubham.bookstall.database.Bookdatabase
import com.shubham.bookstall.database.Bookenitites
import com.squareup.picasso.Picasso
import org.json.JSONObject
import util.Connectionmanager

class Descriptionactivity : AppCompatActivity() {


    lateinit var  tol : Toolbar
    lateinit var scrol : ScrollView
    lateinit var linear : LinearLayout
    lateinit var img : ImageView
    lateinit var parent : RelativeLayout
    lateinit var bookname: TextView
    lateinit var authorname: TextView
    lateinit var price : TextView
    lateinit var rating : TextView
    lateinit var aboutbook : TextView
    lateinit var descriptionbook : TextView
    lateinit var button : Button
    lateinit var progresbarlaout: RelativeLayout
   lateinit var progress : ProgressBar

    var Bookid : String? ="100"
   // var bookid : Int?=100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptionactivity)
       tol=findViewById(R.id.destoolbar)
        scrol=findViewById(R.id.desscroll)
        linear=findViewById(R.id.deslinearlayout)
        img=findViewById(R.id.desimage)
        parent=findViewById(R.id.deslinearrelative)
        bookname=findViewById(R.id.desnamebook)
        authorname=findViewById(R.id.desauthorname)
        price=findViewById(R.id.desprice)
        rating=findViewById(R.id.desrating)
        aboutbook=findViewById(R.id.desaboutbook)
        descriptionbook=findViewById(R.id.despriction)
        button=findViewById(R.id.desBuuton)
        progresbarlaout=findViewById(R.id.progressbarlayout)
        progress=findViewById(R.id.pro)
        progress.visibility=View.VISIBLE
        progresbarlaout.visibility=View.VISIBLE

        setSupportActionBar(tol)
        supportActionBar?.title="Book deatils"

       if(intent!=null){

           Bookid= intent.getStringExtra("book_id")
         //  bookid=intent.getIntExtra("book_id",0)

       }else{
           finish()
           Toast.makeText(this,"some error occured",Toast. LENGTH_SHORT).show()
       }
        if(Bookid=="100"){
            finish()
            Toast.makeText(this,"some error occured",Toast. LENGTH_SHORT).show()
        }


        val que= Volley.newRequestQueue(this)
        val url = "http://13.235.250.119/v1/book/get_book/"

        val jsonparameter= JSONObject()
        jsonparameter.put("book_id",Bookid)



        if(Connectionmanager().chekconnection(this)){
        val jsonobjectrequest =object : JsonObjectRequest(Request.Method.POST,url,jsonparameter,Response.Listener {

                   try{

                       val succes =it.getBoolean("success")

                       if(succes){
                           val bookjsonobject=it.getJSONObject("book_data")
                           progresbarlaout.visibility=View.GONE

                           val bookimgaeurl=bookjsonobject.getString("image")
                          // Picasso.get().load(bookjsonobject.getString("image")).error(R.drawable.default_book_cover).into(img)
                           bookname.text = bookjsonobject.getString("name")
                           authorname.text = bookjsonobject.getString("author")
                           price.text = bookjsonobject.getString("price")
                           rating.text = bookjsonobject.getString("rating")
                           descriptionbook.text = bookjsonobject.getString("description")

                           val bookenitites =Bookenitites(  // set all information in table
                               Bookid?.toInt() as Int,
                               bookname.text.toString(),
                               authorname.text.toString(),
                               price.text.toString(),
                               rating.text.toString(),
                               descriptionbook.text.toString(),
                               bookimgaeurl
                           )

                           val checkfav= DBasync(applicationContext,bookenitites,1).execute()
                           val isfav=checkfav.get()
                           if(isfav){
                               button.setText("Remove from favourite")
                               val favcolor=ContextCompat.getColor(applicationContext,R.color.colorfav)
                               button.setBackgroundColor(favcolor)
                           }else{
                               button.text="Add to favourites"
                               val nonfavcolor=ContextCompat.getColor(applicationContext,R.color.teal_700)
                               button.setBackgroundColor(nonfavcolor)
                           }

                           button.setOnClickListener {
                               if(!DBasync(applicationContext,bookenitites,1).execute().get()){

                                   val async=DBasync(applicationContext,bookenitites,2).execute()
                                   val result=async.get()
                                   if(result){
                                       Toast.makeText(this, "Book added to favourite", Toast.LENGTH_SHORT).show()
                                       button.text="Remove from favourites"
                                       val favcolor = ContextCompat.getColor(applicationContext,R.color.colorfav)
                                       button.setBackgroundColor(favcolor)
                                   }
                                   else{
                                       Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()

                                   }
                               }else{

                                   val async=DBasync(applicationContext,bookenitites,3).execute()
                                   val result=async.get()
                                   if(result){
                                       Toast.makeText(this, "Book removeed from favourtie", Toast.LENGTH_SHORT).show()
                                       button.text="Add to favourite"
                                       val nonfavcolor = ContextCompat.getColor(applicationContext,R.color.teal_700)
                                       button.setBackgroundColor(nonfavcolor)
                                   }else{
                                       Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
                                   }
                               }

                           }

                       }else{
                           Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                       }
                   }catch (e : Exception){

                       Toast.makeText(this,"some error will be occured",Toast.LENGTH_SHORT).show()
                   }



        },Response.ErrorListener {
            Toast.makeText(this,"Volley error will be occured",Toast.LENGTH_SHORT).show()
        }){

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"  // this code for pass the token in hedaer section
                headers["token"] = "ee70ef4656012e"
                //return super.getHeaders()
                return headers

            }
        }
        que.add(jsonobjectrequest)

        }else{

            val dialog = AlertDialog.Builder(this)  // this is for dialog box show
            dialog.setTitle("Unsucces")
            dialog.setMessage("Internet not found ")
            dialog.setPositiveButton("Opent Setting"){ text, listener ->

                val settingoption = Intent(Settings.ACTION_WIRELESS_SETTINGS) // this line for open setting and connect to the internet thats caleed implicit intent
                startActivity(settingoption)
                finish()

            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(this)  // this line for exit the app

            }
            dialog.create()
            dialog.show()
        }
        // THEIS IS THE LINE WHEN CODE REMOVER TIME





        // this is end
    }

    class DBasync(val context: Context,val bookenitites: Bookenitites,val mode : Int) : AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg params: Void?): Boolean {

            val db= Room.databaseBuilder(context,Bookdatabase::class.java,"book-db").build()
            when(mode){

                1 ->{
                   // check the database if the book is faourite or not
                    val book : Bookenitites? = db.bookdao().getbookid(bookenitites.book_id.toString())
                    db.close()
                    return book!=null
                }
                2->{

                    // add for fav book
                    db.bookdao().insertbook(bookenitites)
                    db.close()
                    return true
                }
                3->{

                    // remove from fav book
                    db.bookdao().deletbook(bookenitites)
                    db.close()
                    return true
                }

            }
          return false
        }

    }
}