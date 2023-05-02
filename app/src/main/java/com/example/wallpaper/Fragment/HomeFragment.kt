package com.example.wallpaper.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaper.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), (WallpapersModel) -> Unit {
    private val firebaseRepository = FirebaseRepository()
    private var navController: NavController? = null
    private var isLoading: Boolean = true
    private val wallpapersViewModel: WallpapersViewModel by viewModels()

    private var wallpapersList: List<WallpapersModel> = ArrayList()
    private val wallpapersListAdapter: WallpapersListAdapter =
        WallpapersListAdapter(wallpapersList, this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as AppCompatActivity).setSupportActionBar(main_toolbar)
//
//        val actionBar = (activity as AppCompatActivity).supportActionBar
//        actionBar!!.title = "Wallpapers"

        navController = Navigation.findNavController(view)

        if (firebaseRepository.getUser() == null) {

            navController!!.navigate(R.id.action_homeFragment_to_registerFragment)

        }
        rv_list_item.setHasFixedSize(true)
        rv_list_item.layoutManager = GridLayoutManager(context, 3)
        rv_list_item.adapter = wallpapersListAdapter

        rv_list_item.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isLoading) {
                        wallpapersViewModel.loadWallpapersData()
                        isLoading = true
                    }
                }

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wallpapersViewModel.getWallpapersList().observe(viewLifecycleOwner, Observer {
            wallpapersList = it
            wallpapersListAdapter.wallpapersList = wallpapersList
            wallpapersListAdapter.notifyDataSetChanged()
            isLoading = false

        })
    }

    override fun invoke(wallpapers: WallpapersModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(wallpapers.image)
        navController!!.navigate(action)
    }
}