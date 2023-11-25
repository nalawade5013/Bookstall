package com.shubham.bookstall

import android.annotation.SuppressLint
import fragment.About
import fragment.Dashboard
import fragment.Favouritefragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.shubham.bookstall.R
import fragment.Profilefragement

class MainActivity : AppCompatActivity() {

    lateinit var  DrawerLayout : DrawerLayout
    lateinit var coor : CoordinatorLayout
    lateinit var  frame : FrameLayout
    lateinit var tol : androidx.appcompat.widget.Toolbar
    lateinit var  navgavtion : NavigationView
      var prewitem : MenuItem? =null
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DrawerLayout=findViewById(R.id.drwaerlayiut)
        coor=findViewById(R.id.coordinator)
        frame=findViewById(R.id.frame)
        tol=findViewById(R.id.toolbar)
        navgavtion=findViewById(R.id.navagation)


        setuptool()  // this is for adding toolbar in our app custmoize toolbar
        val actionBarDrawerToggle=ActionBarDrawerToggle(this,DrawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        DrawerLayout.addDrawerListener(actionBarDrawerToggle)  // for replace homeenablebutton to hamburger
        actionBarDrawerToggle.syncState()

        lodaed()  // default fragmet will be lodaed

        navgavtion.setNavigationItemSelectedListener {
 // this code for fragemnt item will be clickeble
            if(prewitem!=null){
                prewitem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            prewitem=it

            if(it.itemId== R.id.dashboard){
                //Toast. makeText(applicationContext,"this is toast message",Toast. LENGTH_SHORT).show()

              /*  supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, Dashboard())

                    .commit()
                    supportActionBar?.title="Dashboard"
                     DrawerLayout.closeDrawers()

               */
                lodaed()


            }
            else if(it.itemId== R.id.fav){
               // Toast. makeText(applicationContext,"this is toast for fav",Toast. LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, Favouritefragment())

                    .commit()
                supportActionBar?.title="Favourite"
                    DrawerLayout.closeDrawers()
            }
          else  if(it.itemId== R.id.person){
               // Toast. makeText(applicationContext,"this is toast for profile",Toast. LENGTH_SHORT).show()
              supportFragmentManager.beginTransaction()
                  .replace(R.id.frame, Profilefragement())

                  .commit()
                supportActionBar?.title="Profile"
                  DrawerLayout.closeDrawers()
            }
            else{
               // Toast. makeText(applicationContext,"this is toast for about",Toast. LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, About())

                    .commit()
                supportActionBar?.title="About"

                    DrawerLayout.closeDrawers()
            }

            return@setNavigationItemSelectedListener true
        }





    }

    fun setuptool(){   // this function for tooblar
        setSupportActionBar(tol)
        supportActionBar?.title="Book Toolbar"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

 fun lodaed(){    // this is the default fragemnt
     supportFragmentManager.beginTransaction()
         .replace(R.id.frame, Dashboard())
        // .addToBackStack("Dashboard")
         .commit()
        supportActionBar?.title="Dashboard"
     navgavtion.setCheckedItem(R.id.dashboard)
 }

    override fun onBackPressed() {    // activity will be direct back to the default fragment and the  close
        val frag=supportFragmentManager.findFragmentById(R.id.frame)
     /*   if(frag!=Dashboard()){
            lodaed()
        }else{
            super. getOnBackPressedDispatcher().onBackPressed()
        }

      */
        when(frag){
            !is Dashboard ->lodaed()
            else -> getOnBackPressedDispatcher().onBackPressed()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {   // this is for open fragment

        val id=item.itemId
        if(id==android.R.id.home){
            DrawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}