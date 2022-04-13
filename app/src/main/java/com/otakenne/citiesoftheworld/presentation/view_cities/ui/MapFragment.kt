package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.FragmentMapBinding
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.presentation.view_cities.adapters.CityAdapter
import com.otakenne.citiesoftheworld.presentation.view_cities.map_utility.CityRenderer
import com.otakenne.citiesoftheworld.presentation.view_cities.view_model.CityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var _binding: FragmentMapBinding
    private val binding get() = _binding

    private val viewModel: CityViewModel by activityViewModels()
    private lateinit var googleMaps: GoogleMap
    private var cities = mutableListOf<City>()

    @Inject
    lateinit var adapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar?.title = "Cities of the World"
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        getData()

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync{
            addClusteredMarkers(it)
        }
    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.pagedCityFlow.collect (adapter::submitData)
        }

        for (city in adapter.snapshot().items) {
            cities.add(city)
        }
    }

    private fun addClusteredMarkers(googleMap: GoogleMap) {
        val clusterManager = ClusterManager<City>(requireContext(), googleMap)
        clusterManager.renderer = CityRenderer(requireContext(), googleMap, clusterManager)

//        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        clusterManager.addItems(cities)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }
}