package com.example.myinjections.view.ui

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myinjections.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var locationPermissionGranted = false

    //permission:
    //https://stackoverflow.com/questions/43518520/how-to-ask-user-to-enable-gps-at-the-launch-of-application

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastKnownLocation: Location? = null
    private val DEFAULT_ZOOM = 15
    private val TAG = MapsActivity::class.java.simpleName
    private val defaultLocation = LatLng(-34.0, 151.0)
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //val intent = Intent(Settings.)
        ///startActivity(intent)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
       // mMap!!.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Sydney"))
       // mMap!!.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation))

        // Construct a PlacesClient
        //Places.initialize(applicationContext, getString(R.string.maps_api_key))
        //var placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //Log.d(TAG, )

        updateLocationUI()
        if(locationPermissionGranted) {
            ensureGPSIsTurnedOn()
        }
        getDeviceLocation()
        addMarkers(mMap!!)

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
                        lastKnownLocation = task.result
                        moveCamera(CameraMovementModes.MyLocation)
//                        if (lastKnownLocation != null) {
//                            //moveCameraToMyLocation()
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.")
//                            mMap?.moveCamera(
//                                CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
//                            )
//                            //mMap?.uiSettings?.isMyLocationButtonEnabled = false
//                        }
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
            if (mode == CameraMovementModes.MyLocation && lastKnownLocation == null) {
                Log.e(TAG, "Last known location was empty, so mode switched to default.")
                CameraMovementModes.Default
            }
            else mode

        // Assign new coordinates depending on chosen mode.
        val newCoordinates = when(confirmedMode){
            CameraMovementModes.Default -> defaultLocation

            CameraMovementModes.MyLocation ->
                LatLng(
                    lastKnownLocation!!.latitude,
                    lastKnownLocation!!.longitude)

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
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }


    private fun addMarkers(googleMap: GoogleMap) {
        val places = arrayListOf("a")

        places.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place)
                    .position(LatLng(-34.0, 151.0))
            )
        }
    }
}

enum class CameraMovementModes {
    Default, MyLocation, Other
}