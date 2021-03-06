package com.example.myinjections.view.ui

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.myinjections.R
import com.example.myinjections.network.model.Place
import com.example.myinjections.viewmodel.PlacesViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.internal.VisibilityAwareImageButton
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var locationPermissionGranted = false

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val DEFAULT_ZOOM = 12
    private val TAG = MapsActivity::class.java.simpleName
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private lateinit var placesViewModel: PlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //Setting toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.maps_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // ViewModel
        placesViewModel = getViewModel()
        subscribeToObservers()

        // Floating buttons
        unwrap_button.setOnClickListener {
            changeUnwrapButtonState()
        }

        only_clinics_button.setOnClickListener {
            placesViewModel.getNearestClinics()
            changeUnwrapButtonState()
        }

        only_pharmacies_button.setOnClickListener {
            placesViewModel.getNearestPharmacies()
            changeUnwrapButtonState()
        }
    }

    private fun changeUnwrapButtonState() {
        placesViewModel.isUnwrapButtonClicked = !placesViewModel.isUnwrapButtonClicked

        if(placesViewModel.isUnwrapButtonClicked){
            only_clinics_button.visibility = VisibilityAwareImageButton.VISIBLE
            only_pharmacies_button.visibility = VisibilityAwareImageButton.VISIBLE
        } else {
            only_clinics_button.visibility = VisibilityAwareImageButton.INVISIBLE
            only_pharmacies_button.visibility = VisibilityAwareImageButton.INVISIBLE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //disable map toolbar
        mMap!!.uiSettings.isMapToolbarEnabled = false

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocationUI()

        if(locationPermissionGranted) {
            ensureGPSIsTurnedOn()
        }
        getDeviceLocation()

        mMap!!.setOnMyLocationButtonClickListener {
            Log.d(TAG, "Find my location button clicked.")
            getDeviceLocation()
            true
        }
    }


    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        placesViewModel.lastKnownLocation = task.result
                        moveCamera(CameraMovementModes.MyLocation)
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun moveCamera(mode : CameraMovementModes, coordinates: LatLng? = null) {
        // Firstly make sure, that myLocation is available (if chosen myLocation type).
        // If not available change mode to default and throw error.
        val confirmedMode: CameraMovementModes =
            if (mode == CameraMovementModes.MyLocation && placesViewModel.lastKnownLocation == null) {
                Log.e(TAG, "Last known location was empty, so mode switched to default.")
                CameraMovementModes.Default
            }
            else mode

        // Assign new coordinates depending on chosen mode.
        val newCoordinates = when(confirmedMode){
            CameraMovementModes.Default -> PlacesViewModel.DEFAULT_LOCATION

            CameraMovementModes.MyLocation ->
                LatLng(
                    placesViewModel.lastKnownLocation!!.latitude,
                    placesViewModel.lastKnownLocation!!.longitude)

            CameraMovementModes.Other -> coordinates
        }

        // Then move camera to newCooradinates.
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                newCoordinates,
                DEFAULT_ZOOM.toFloat())
        )
    }


    private fun ensureGPSIsTurnedOn() {
        // Creation of type of location request (high accuracy)
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        // Asynchronous task to check the GPS status
        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())

        // If GPS is off, show user the prompt to allow to turn it on.
        result.addOnCompleteListener(this) { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            val resolvable: ResolvableApiException = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult() and check the result in onActivityResult().
                            resolvable.startResolutionForResult(this, LocationRequest.PRIORITY_HIGH_ACCURACY)
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }


    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            updateLocationUI()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false

        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    updateLocationUI()
                }
            }
        }
    }


    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
                placesViewModel.lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun subscribeToObservers() {
        placesViewModel.places.observe(this, Observer {
            addMarkers(it, mMap)
        })
    }


    private fun addMarkers(placesList: List<Place>, googleMap: GoogleMap?)= runBlocking {
        // remove old markers
        googleMap?.clear()

        // add new markers
        placesList.forEach { place ->
            googleMap?.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .icon(BitmapDescriptorFactory.fromResource(placesViewModel.currentPlaceTypeIcon))
                    .position(LatLng(place.latitude, place.longitude))
            )
        }
    }
}


enum class CameraMovementModes {
    Default, MyLocation, Other
}