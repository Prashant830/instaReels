package com.prashant.musicvideosample.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.prashant.musicvideosample.R
import com.prashant.musicvideosample.base.BaseActivity
import com.prashant.musicvideosample.databinding.ActivityHomeBinding
import com.prashant.musicvideosample.ui.fragment.HomeFragment
import com.prashant.musicvideosample.utils.addFragment
import com.prashant.musicvideosample.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


class  HomeActivity : BaseActivity<ActivityHomeBinding>(), BottomNavigationView.OnNavigationItemSelectedListener,KodeinAware {

    override val kodein: Kodein by kodein()
    override val layoutRes: Int get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar?  = binding.toolbar
    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        enableBack()
        setToolbarTitleDisable()
        addFragment(R.id.homeContainer, HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            R.id.home -> {
                toast("Shop Clicked")
                val j = Intent(this@HomeActivity, secondHome ::class.java)
                startActivity(j)
            }
            R.id.video -> toast("Home Clicked")
            R.id.like ->  {
                toast("Category Clicked")
                val j = Intent(this@HomeActivity, catagrey ::class.java)
                startActivity(j)
            }
            R.id.profile -> {
                toast("Profile Clicked")
                val j = Intent(this@HomeActivity, profile ::class.java)
                startActivity(j)
            }
        }
        return true
    }
}