package com.example.diplommap2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var mapObjects: MapObjectCollection

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("a35d079d-834c-4a14-98a8-26069e2775c1")
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        mapObjects = mapView.map.mapObjects

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Инициализация карты

        mapView.map.move(CameraPosition(Point(56.138386, 40.399160), 11.0f, 0.0f, 0.0f))
        Animation(Animation.Type.SMOOTH, 300f)
     /*   val lkbutton = findViewById<Button>(R.id.btnlk)
        // Кнопка перехода в личный кабинет
        lkbutton.setOnClickListener {
            val Intent = Intent(this, Account::class.java)
            startActivity(Intent)
        }*/

        val point = Point(56.138386, 40.399160)
        val placemark = mapObjects.addPlacemark(point)
        val iconStyle = IconStyle().setAnchor(PointF(0.5F, 0.5F)).setScale(1.0f).setFlat(true)
        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.marker_icon), iconStyle)
      /*  val markersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("markers")
        markersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                runOnUiThread {
                    mapObjects.clear() // Очистка старых меток
                    for (markerSnapshot in dataSnapshot.children) {
                        val latitude =
                            markerSnapshot.child("latitude").getValue(Double::class.java)
                                ?: continue
                        val longitude =
                            markerSnapshot.child("longitude").getValue(Double::class.java)
                                ?: continue
                        val title = markerSnapshot.child("title").getValue(String::class.java) ?: ""
                        val descriptioh =
                            markerSnapshot.child("descriptioh").getValue(String::class.java) ?: ""

                        Log.d("FirebaseData", "Marker loaded: $latitude, $longitude, $title, $descriptioh")
                        val point = Point(latitude, longitude)
                        val imageProvider = ImageProvider.fromResource(this@MainActivity, R.drawable.marker_icon)
                        val marker = mapObjects.addPlacemark(point, imageProvider)

                        marker.userData = MarkerData(title, descriptioh) // Дополнительные данные, если нужны

                        // Настройка маркера

                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибки
            }

        }) */
    }
        override fun onStart() {
           mapView.onStart()
           MapKitFactory.getInstance().onStart()
           super.onStart()

        }
        override fun onStop() {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
            super.onStop()

        }


    data class MarkerData(val title: String, val descriptioh: String)
}






