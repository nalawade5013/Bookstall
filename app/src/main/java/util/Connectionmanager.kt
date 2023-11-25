package util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Connectionmanager() {
// checking network is connected or not
    fun chekconnection(context: Context) : Boolean {

        val connectvitymanager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activenetwork : NetworkInfo? =connectvitymanager.activeNetworkInfo

        if(activenetwork?.isConnected!=null){
            return activenetwork.isConnected
        }else{
            return false
        }
    }
}