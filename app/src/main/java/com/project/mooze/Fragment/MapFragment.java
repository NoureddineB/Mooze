package com.project.mooze.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.project.mooze.Activity.OrderActivity;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.project.mooze.Fragment.MainFragment.restoID;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private LocationManager locationManager;
    private static final int PERMISSION_REQUEST_CODE = 201;
    private static final int REQUEST_CODE = 200;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Marker user_marker;



    private List<Restaurent> restaurentsList = new ArrayList<>();
    private Disposable disposable;
    private FusedLocationProviderClient fusedLocationClient;

    onLocationPass locationPasser;



    public interface onLocationPass{
        void onLocationPass(Location location);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        checkUserLocationPermissions();
        getAllRestaurent();
        configureMap();


        return v;

    }


    private void openOrderActivity(int restaurantid) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra(restoID, restaurantid);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }


    private void configureMap() {

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LocationManager mgr = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
                if ( !mgr.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.8534, 2.3488), 15)); // If the user dont give his location camera focus on Paris
                    buildAlertMessageNoGps();
                }else {
                    if (getLastKnownLocation() != null) {
                        setCurrentPosition(googleMap, getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude());
                    }
                }

            }
        });

    }


    private void setCurrentPosition(GoogleMap googleMap, double latitude, double longitude) {
        user_marker = googleMap.addMarker(new MarkerOptions().title("Vous êtes ici").position(new LatLng(0, 0)));
        user_marker.setPosition(new LatLng(getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));


    }

    private Location getLastKnownLocation() {
        Location l = null;
        LocationManager mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        passLocation(bestLocation);
        return bestLocation;
    }


    /*private Location getLastKnownLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            bestLocation = location;
                        }
                    }
                });

        return bestLocation;
    }*/

    private boolean checkUserLocationPermissions() {
        if (checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAllRestaurent();
                    configureMap();
                } else {
                    Log.e("TAGREFUSE", "Refusé");
                }
                return;
            }

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Activez votre GPS afin de retrouver vos établissements favoris")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, RESULT_OK);

                    }
                })
                .setNegativeButton("Plus tard", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void getAllRestaurent() {
        this.disposable = MoozeStreams.getAllRestaurent().subscribeWith(create());
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private DisposableObserver<List<Restaurent>> create() {
        return new DisposableObserver<List<Restaurent>>() {
            @Override
            public void onNext(List<Restaurent> restaurents) {
                configureRestaurentMarker(restaurents);




            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "Error" + e);


            }

            @Override
            public void onComplete() {


            }
        };
    }

    private List<Restaurent> getRestaurent(List<Restaurent> restaurent) {
        return restaurent;
    }

    private void configureRestaurentMarker(final List<Restaurent> restaurents) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                for (int i = 0; i < getRestaurent(restaurents).size(); i++) {
                    createMarker(googleMap, getRestaurent(restaurents).get(i).getLatitude(), getRestaurent(restaurents).get(i).getLongitude(), getRestaurent(restaurents).get(i).getName(), getRestaurent(restaurents).get(i).getAddress(),restaurents.get(i).getId());

                }
            }
        });


    }

    private Marker createMarker(GoogleMap googleMap, double latitude, double longitude, String title, String snippet,int restaurantid) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.untitled_1);
       configureOnClick(googleMap);
        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .zIndex(restaurantid)
                .snippet(snippet)
                .icon(icon));
    }

    private void configureOnClick(GoogleMap googleMap){
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                openOrderActivity((int) marker.getZIndex());
            }
        });

    }

    public void passLocation(Location location) {
        locationPasser.onLocationPass(location);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationPasser = (onLocationPass) context;
    }
}









