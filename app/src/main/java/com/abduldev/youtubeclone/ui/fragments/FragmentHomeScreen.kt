package com.abduldev.youtubeclone.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abduldev.youtubeclone.R
import com.abduldev.youtubeclone.databinding.FragmentHomeScreenBinding
import com.abduldev.youtubeclone.ui.adapter.PopularVideosAdapter
import com.abduldev.youtubeclone.ui.home.PopularVideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHomeScreen : Fragment(R.layout.fragment_home_screen) {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var popularVideosAdapter: PopularVideosAdapter
    private val popularVideoViewModel: PopularVideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        hideActionBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeDataFromApi()
    }

    private fun observeDataFromApi() {
        popularVideoViewModel.popularVideos.observe(
            viewLifecycleOwner
        ) { result ->
            popularVideosAdapter.differ.submitList(result.date?.items)
        }
    }

    private fun setUpRecyclerView() {
        popularVideosAdapter = PopularVideosAdapter()
        binding.videoRecyclerView.apply {
            adapter = popularVideosAdapter
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )

        }
    }

    private fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}