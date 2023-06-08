package ru.mirea.sidorov_m.mireaproject;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import ru.mirea.sidorov_m.mireaproject.databinding.FragmentBethesdaBinding;

public class BethesdaFragment extends Fragment {


    private FragmentBethesdaBinding binding;
    private View root;
    private MapView mapView = null;
    private MyLocationNewOverlay locationNewOverlay;
    private static final int REQUEST_CODE_PERMISSION = 200;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBethesdaBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Configuration.getInstance().load(root.getContext().getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(root.getContext().getApplicationContext()));

        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(root.getContext().getApplicationContext(), new InternalCompassOrientationProvider(root.getContext().getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);


        int cOARSE_LOCATION = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fINE_LOCATION = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (cOARSE_LOCATION == PackageManager.PERMISSION_GRANTED || fINE_LOCATION == PackageManager.PERMISSION_GRANTED) {
            setMyLocation();
        }


        Marker bethesda_1 = new Marker(mapView);
        bethesda_1.setPosition(new GeoPoint(39.403464520041894, -77.15804920445048));
        bethesda_1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Офис Bethesda" + "\n1370 Piccard Dr # 120, Rockville, MD 20850", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(bethesda_1);
        bethesda_1.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        bethesda_1.setTitle("Офис Bethesda №1");


        Marker new_vegas = new Marker(mapView);
        new_vegas.setPosition(new GeoPoint(36.29962229424441, -115.21803559815179));
        new_vegas.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"Нью Вегас" + "\nБывший Лас Вегас", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(new_vegas);
        new_vegas.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        new_vegas.setTitle("New Vegas");


        Marker pentagon = new Marker(mapView);
        pentagon.setPosition(new GeoPoint(38.87231940733044, -77.05383572094338));
        pentagon.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(root.getContext().getApplicationContext(),"База Братсва Стали" + "\nПентагон, Вашингтон, США", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(pentagon);
        pentagon.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        pentagon.setTitle("База братства стали");



        return root;
    }

    protected void setMyLocation()
    {
        locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(root.getContext().getApplicationContext()), mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);
        locationNewOverlay.runOnFirstFix(new Runnable() {
            public void run() {

                try {
                    double latitude = locationNewOverlay.getMyLocation().getLatitude();
                    double longitude = locationNewOverlay.getMyLocation().getLongitude();
                    Log.d("coord", String.valueOf(latitude));
                    Log.d("coord", String.valueOf(longitude));

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IMapController mapController = mapView.getController();
                            mapController.setZoom(15.0);
                            GeoPoint startPoint = new GeoPoint(latitude, longitude);
                            mapController.setCenter(startPoint);
                        }
                    });
                }
                catch (Exception e) {}
            }
        });
    }
}