package com.example.user.sforum

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.example.user.sforum.fragments.FixturesFragment
import com.example.user.sforum.fragments.LeagueTableFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar_main.*
import android.support.v4.view.GravityCompat


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentsAdapter : FragmentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        val id = intent.getStringExtra("id")
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setNavigationViewListener()

        val leagueTableFragment = LeagueTableFragment.newInstance(id)
        val fixturesFragment = FixturesFragment.newInstance(id)

        fragmentsAdapter = FragmentsAdapter(supportFragmentManager)
        fragmentsAdapter!!.addFragments(leagueTableFragment, "League Table")
        fragmentsAdapter!!.addFragments(fixturesFragment, "Fixtures")

        fragmentViewPager.adapter = fragmentsAdapter
        fragmentTabLayout.setupWithViewPager(fragmentViewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        if (mToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        when (item.itemId) {

            R.id.nav_laliga -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", "455")
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                startActivity(intent)
            }

            R.id.nav_premierleague -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", "445")
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                startActivity(intent)
            }

            R.id.nav_ligue1 -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", "450")
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                startActivity(intent)
            }

            R.id.nav_seriea -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", "456")
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                finish()
                startActivity(intent)
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    fun setNavigationViewListener() {

        val navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener(this)


    }
}
