package com.augie.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.augie.githubuser.fragment.FollowerFragment
import com.augie.githubuser.fragment.FollowingFragment
import com.augie.githubuser.fragment.RepositoryFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String? = null
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
            2 -> fragment = RepositoryFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }

}