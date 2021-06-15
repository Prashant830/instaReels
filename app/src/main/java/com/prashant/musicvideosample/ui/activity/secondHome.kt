package com.prashant.musicvideosample.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.prashant.musicvideosample.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home2.*

class secondHome : AppCompatActivity() {
   // private var cardView : CardView? = null
   var nav: NavigationView? = null
    var toggle: ActionBarDrawerToggle? = null
    var drawerLayout: DrawerLayout? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        nav = findViewById<View>(R.id.navmenu) as NavigationView
        drawerLayout = findViewById<View>(R.id.drawer) as DrawerLayout

        ActionBarDrawerToggle(
            this,
            drawerLayout,
            this.toolbar,
            R.string.open,
            R.string.close
        ).also { toggle = it }
        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        nav!!.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
            }
            true
        }
        val constraintLayout = findViewById<ConstraintLayout>(R.id.cons)
        constraintLayout.setOnClickListener {
            startActivity(Intent(this@secondHome, shopp::class.java))
        }
        val constraintLayout1 = findViewById<ConstraintLayout>(R.id.cons1)
        constraintLayout1.setOnClickListener {
            startActivity(Intent(this@secondHome, shopp::class.java))
        }
        val constraintLayout2 = findViewById<ConstraintLayout>(R.id.cons2)
        constraintLayout2.setOnClickListener {
            startActivity(Intent(this@secondHome, shopp::class.java))
        }
        val constraintLayout3 = findViewById<ConstraintLayout>(R.id.cons3)
        constraintLayout3.setOnClickListener {
            startActivity(Intent(this@secondHome, shopp::class.java))
        }



    }
}