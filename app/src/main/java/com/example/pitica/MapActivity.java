package com.example.pitica;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapActivity extends AppCompatActivity {
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(8.0);
        map.getController().setCenter(new GeoPoint(44.2000, 18.5000));

        // 1. Setup Blue Location Dot
        setupMyLocation();

        // 2. Setup Markers and Nav
        setupNavigation();
        addChefMarkers();
    }

    private void setupMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
            myLocationOverlay.enableMyLocation();
            map.getOverlays().add(myLocationOverlay);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void addChefMarkers() {
        Drawable icon = getResources().getDrawable(android.R.drawable.ic_menu_compass);

        // Update these strings to match the data in MainActivity exactly
        addChefMarker(new GeoPoint(43.8563, 18.4131), "Lejla");
        addChefMarker(new GeoPoint(44.5384, 18.6671), "Amira");
        addChefMarker(new GeoPoint(44.5500, 18.6500), "Tarik");
    }

    private void addChefMarker(GeoPoint point, String chefName) {
        // Turn the icon Red
        Drawable icon = getResources().getDrawable(android.R.drawable.ic_menu_compass);
        icon.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        Marker marker = new Marker(map);
        marker.setPosition(point);
        marker.setTitle(chefName);
        marker.setIcon(icon);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // Marker Click Listener
        marker.setOnMarkerClickListener((m, mapView) -> {
            Intent intent = new Intent(MapActivity.this, ChefProfileActivity.class);
            intent.putExtra("chef_name", m.getTitle());
            startActivity(intent);
            return true;
        });

        map.getOverlays().add(marker);
        map.invalidate();
    }

    private void setupNavigation() {
        findViewById(R.id.btnNavHome).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.btnNavCart).setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        findViewById(R.id.btnNavProfile).setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}