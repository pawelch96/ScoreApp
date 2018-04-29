package com.example.user.sforum

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentPagerAdapter


class FragmentsAdapter (fragmentManager : FragmentManager):FragmentPagerAdapter(fragmentManager) {

    var mFragmentManager = fragmentManager
    var mFragmentItems : ArrayList<Fragment> = ArrayList()
    var mFragmentTitles : ArrayList<String> = ArrayList()

    fun addFragments(fragmentItem : Fragment, fragmentTitle : String) {
        mFragmentItems.add(fragmentItem)
        mFragmentTitles.add(fragmentTitle)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentItems[position]
    }

    override fun getCount(): Int {
        return mFragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitles[position]
    }
}