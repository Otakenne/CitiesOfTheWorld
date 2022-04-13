package com.otakenne.citiesoftheworld.presentation.view_cities.map_utility

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.otakenne.citiesoftheworld.domain.model.City

class CityRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<City>
): DefaultClusterRenderer<City>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: City?, markerOptions: MarkerOptions?) {
        markerOptions
            ?.title(item?.name)
            ?.position(LatLng(item?.latitude ?: 0.0, item?.longitude ?: 0.0))

    }

    override fun onClusterItemRendered(clusterItem: City?, marker: Marker?) {
        marker?.tag = clusterItem
    }
}