package com.example.homework2.presentation.screen.places

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.homework2.R
import com.example.homework2.databinding.FragmentPlacesBinding
import com.example.homework2.presentation.base.BaseFragment
import com.example.homework2.presentation.state.PlacesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlacesFragment : BaseFragment<FragmentPlacesBinding>(FragmentPlacesBinding::inflate) {

    private val imageAdapter = ImageRecyclerViewAdapter()
    private val locationAdapter = LocationTypesListAdapter()
    private val viewModel: PlacesViewModel by viewModels()

    override fun setUp() {
        setUpPager()
        setupRecycler()
    }

    override fun setUpListeners() {
        locationAdapter.onItemClick = {
            viewModel.filterPlaces(type = it.type)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.placesStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(placesState: PlacesState) {
        placesState.chosenPlaces?.let {
            imageAdapter.submitList(it)
        }
        placesState.locationTypes?.let {
            locationAdapter.submitList(it.toList())
        }
    }

    private fun setupRecycler() {
        with(binding.locationTypeRecycler) {
            adapter = locationAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun setUpPager() {
        with(binding.viewPager) {
            adapter = imageAdapter
            offscreenPageLimit = 1

            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            }
            setPageTransformer(pageTransformer)

            val itemDecoration = HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin
            )
            addItemDecoration(itemDecoration)
        }
    }
}