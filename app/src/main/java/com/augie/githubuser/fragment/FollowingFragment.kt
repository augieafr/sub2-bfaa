package com.augie.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.githubuser.DetailActivity
import com.augie.githubuser.adapter.UserAdapter
import com.augie.githubuser.model.UserModel
import com.augie.githubuser.databinding.FragmentFollowingBinding
import com.augie.githubuser.adapter.UserAdapter.OnItemClickCallback
import com.augie.githubuser.viewmodel.UserViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var followingViewModel: UserViewModel

    companion object {
        private const val EXTRA_USERNAME = "extra_username"
        @JvmStatic
        fun newInstance(username: String?) =
                FollowingFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_USERNAME, username)
                    }
                }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get data from sectionpager
        val username = arguments?.getString(EXTRA_USERNAME)
        //set recycler view
        adapter = UserAdapter()
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.adapter = adapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        showLoading(true)
        followingViewModel.setFollow(username, "following")
        followingViewModel.getUser().observe(viewLifecycleOwner, { listUser ->
            showLoading(false)
            adapter.setData(listUser)
        })

        // recyclerview on click callback
        adapter.setOnItemClickCallback(object : OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                val mIntent = Intent(view.context, DetailActivity::class.java)
                mIntent.putExtra(DetailActivity.EXTRA_USERNAME, data.name)
                startActivity(mIntent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }
}