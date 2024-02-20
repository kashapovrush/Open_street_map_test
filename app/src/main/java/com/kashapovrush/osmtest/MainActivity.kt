package com.kashapovrush.osmtest

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration.getInstance
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    private lateinit var map: MapView
    private lateinit var center: Button
    private lateinit var focus: Button
    private lateinit var menu: Button
    private var listForMarker = mutableListOf<GeoPoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_main)

        map = findViewById(R.id.map)
        center = findViewById(R.id.center_btn)
        focus = findViewById(R.id.focus_btn)
        menu = findViewById(R.id.menu_btn)
        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(15.5)
        val startPoint = GeoPoint(55.7856, 37.5830)
        mapController.setCenter(startPoint)

        center.setOnClickListener {
            if (listForMarker.size == 0) {
                Toast.makeText(this@MainActivity, "Выберите начальную точку", Toast.LENGTH_LONG)
                    .show()
            } else {
                mapController.setCenter(listForMarker[0])
            }
        }
        val line = Polyline()
        line.isGeodesic = true
        line.outlinePaint.strokeWidth = 2f

        focus.setOnClickListener {
            when (listForMarker.size) {
                0 -> {
                    Toast.makeText(this@MainActivity, "Выберите начальную точку", Toast.LENGTH_LONG)
                        .show()
                }

                1 -> {
                    Toast.makeText(this@MainActivity, "Выберите конечную точку", Toast.LENGTH_LONG)
                        .show()
                }

                else -> {
                    line.addPoint(listForMarker[0])
                    line.addPoint(listForMarker[1])
                    map.overlays.add(line)
                    map.invalidate()
                }
            }
        }

        menu.setOnClickListener {
            startActivity(MenuActivity.newIntent(this))
            finish()
        }

        val startMarker = Marker(map)
        val secondMarker = Marker(map)
        map.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                if (listForMarker.size == 0) {
                    startMarker.position = GeoPoint(p?.latitude ?: 0.0, p?.longitude ?: 0.0)
                    listForMarker.add(p ?: GeoPoint(0.0, 0.0))
                    startMarker.icon =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_marker)

                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                    map.overlays.add(startMarker)

                    map.invalidate()
                } else {
                    secondMarker.position = GeoPoint(p?.latitude ?: 0.0, p?.longitude ?: 0.0)
                    if (listForMarker.size < 2) {
                        listForMarker.add(p ?: GeoPoint(0.0, 0.0))
                    } else {
                        listForMarker.removeAt(1)
                        listForMarker.add(p ?: GeoPoint(0.0, 0.0))
                        map.overlays.remove(line)
                        line.actualPoints.clear()
                    }
                    secondMarker.icon =
                        ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_marker)

                    secondMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                    map.overlays.add(secondMarker)

                    map.invalidate()
                }


                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                return false
            }

        }))
    }


    override fun onResume() {
        super.onResume()
        map.onResume()
    }


    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}