package fragment

import com.shubham.bookstall.Fragmentrecyler
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.shubham.bookstall.R
import com.shubham.bookstall.database.Bookdatabase
import com.shubham.bookstall.database.Bookenitites


class Favouritefragment : Fragment() {


    lateinit var recyler : RecyclerView
    lateinit var progressbarlaouyt : RelativeLayout
    lateinit var  progress : ProgressBar
    lateinit var  layoutmanager : RecyclerView.LayoutManager
    lateinit var adapater : Fragmentrecyler

    var dBooklist= listOf<Bookenitites>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v=inflater.inflate(R.layout.fragment_favouritefragment, container, false)

        recyler=v.findViewById(R.id.recylers)
        progressbarlaouyt=v.findViewById(R.id.progressbarlayout)
        progress=v.findViewById(R.id.progreesl)

        layoutmanager= GridLayoutManager(activity as Context,2)
        dBooklist =Retrivefavroutie(activity as Context).execute().get()

        if(activity!=null){

            progressbarlaouyt.visibility=View.GONE
            recyler.adapter = Fragmentrecyler(activity as Context,dBooklist)
            recyler.layoutManager=layoutmanager

        }
        return v
    }


    class Retrivefavroutie(val context: Context): AsyncTask<Void,Void,List<Bookenitites>>(){
        override fun doInBackground(vararg params: Void?): List<Bookenitites> {

            val db=Room.databaseBuilder(context,Bookdatabase::class.java,"book-db").build() /// database intilize
            return db.bookdao().getallbooks()
        }

    }



}