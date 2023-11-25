package fragment

import adapater.Dashboardrecyle
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.shubham.bookstall.R
import model.Book
import org.json.JSONException
import util.Connectionmanager


class Dashboard : Fragment() {


    lateinit var recycler: RecyclerView
    lateinit var  recylead : Dashboardrecyle

    lateinit var  progressBarlayout: RelativeLayout
    lateinit var progres : ProgressBar
  //  var bookInfoList = arrayListOf<Informatrion>()

    val bookInfoList = arrayListOf<Book>(
       /* Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)

        */
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)

       recycler= view.findViewById<RecyclerView>(R.id.recylcer)
        recycler.layoutManager=LinearLayoutManager(activity)

        progressBarlayout=view.findViewById(R.id.progress)
        progres=view.findViewById(R.id.forprogress)

        progressBarlayout.visibility=View.VISIBLE
      /*  Button.setOnClickListener {
            if(Connectionmanager().chekconnection(activity as Context)){

                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Succes")
                dialog.setMessage("Internet found succesfully")
                dialog.setPositiveButton("ok"){ text, listener ->


                }
                dialog.create()
                dialog.show()
            }else{

                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Unsucces")
                dialog.setMessage("Internet not found ")
                dialog.setPositiveButton("Cancle"){ text, listener ->


                }
                dialog.create()
                dialog.show()
            }
        }

       */




      /*  recylead = Dashboardrecyle(activity as Context,bookInfoList)
        recycler.adapter=recylead
        recycler.layoutManager=recycler.layoutManager

        recycler.addItemDecoration(DividerItemDecoration(recycler.context,(recycler.layoutManager as LinearLayoutManager).orientation))

       */


        if(Connectionmanager().chekconnection(activity as Context)) {

            val que = Volley.newRequestQueue(activity as Context)   // this if block code is totally responicible for sending request and fetch tha data and parse the data
            val url = "http://13.235.250.119/v1/book/fetch_books/"


            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                try {

                    progressBarlayout.visibility=View.GONE

                    val succes = it.getBoolean("success")
                    if (succes) {

                        val data = it.getJSONArray("data")

                        for (i in 0 until data.length()) {

                            val bookjsonobject = data.getJSONObject(i)
                            val Bookobject = Book(
                                bookjsonobject.getString("book_id"),
                                bookjsonobject.getString("name"),
                                bookjsonobject.getString("author"),
                                bookjsonobject.getString("price"),
                                bookjsonobject.getString("rating"),
                                bookjsonobject.getString("image"),

                                )

                            bookInfoList.add(Bookobject)
                            recylead = Dashboardrecyle(
                                activity as Context,
                                bookInfoList
                            )  // this line for connecting the adapater
                            recycler.adapter = recylead
                            recycler.layoutManager = recycler.layoutManager

                           /* recycler.addItemDecoration(   // this code for adding line between items in recylerview
                                DividerItemDecoration(
                                    recycler.context,
                                    (recycler.layoutManager as LinearLayoutManager).orientation
                                )
                            )

                            */

                        }

                    } else {
                        //  Toast.makeText(this,"this is toast message",Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException){
                    Toast.makeText(context, "Some error will be ocuured!!!", Toast.LENGTH_SHORT).show()
                }

                }, Response.ErrorListener {

                   // println("error is $it")
                Toast.makeText(context, "Volley Error Ocuured!!!", Toast.LENGTH_SHORT).show()
                }) {

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"  // this code for pass the token in hedaer section
                        headers["token"] = "ee70ef4656012e"
                        //return super.getHeaders()
                        return headers
                    }
                }


            que.add(jsonObjectRequest)  // this line for add the resultdata in requestque
        }
        else{
            val dialog = AlertDialog.Builder(activity as Context)  // this is for dialog box show
            dialog.setTitle("Unsucces")
            dialog.setMessage("Internet not found ")
            dialog.setPositiveButton("Opent Setting"){ text, listener ->

                val settingoption =Intent(Settings.ACTION_WIRELESS_SETTINGS) // this line for open setting and connect to the internet thats caleed implicit intent
                startActivity(settingoption)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(activity as Activity)  // this line for exit the app

            }
            dialog.create()
            dialog.show()
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater?.inflate(R.menu.sort,menu)
    }
}


