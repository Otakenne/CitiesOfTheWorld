package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.FragmentMapBinding
import com.otakenne.citiesoftheworld.presentation.view_cities.adapters.CityAdapter
import com.otakenne.citiesoftheworld.presentation.view_cities.view_model.CityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentMapBinding
    private val binding get() = _binding

    private val viewModel: CityViewModel by activityViewModels()
    private lateinit var googleMaps: GoogleMap

    @Inject
    lateinit var adapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).supportActionBar?.title = "Cities of the World"

        val mapFragment = childFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMaps = p0

        lifecycleScope.launch {
            viewModel.pagedCityFlow.collect (adapter::submitData)
        }

        for (city in adapter.snapshot().items) {
            if (city.latitude != null && city.longitude != null) {
                val latLng = LatLng(city.latitude, city.longitude)
                googleMaps.addMarker(
                    MarkerOptions().position(latLng).title(city.name)
                )
                googleMaps.animateCamera(CameraUpdateFactory.zoomTo(2.0f));
                googleMaps.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        menu.findItem(R.id.navigateToMapFragment).isVisible = false
//        super.onPrepareOptionsMenu(menu)
//    }
}