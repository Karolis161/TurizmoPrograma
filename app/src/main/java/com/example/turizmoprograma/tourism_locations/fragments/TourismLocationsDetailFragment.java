package com.example.turizmoprograma.tourism_locations.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turizmoprograma.R;
import com.example.turizmoprograma.tourism_locations.dto.TourismLocationsData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TourismLocationsDetailFragment extends Fragment {
    private String name;
    private double latDest, lngDest;
    private View view;

    public TourismLocationsDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tourism_locations_detail, container, false);
        TextView locationName = view.findViewById(R.id.locationName);
        TextView locationDescription = view.findViewById(R.id.locationDescription);
        ImageView locationImg = view.findViewById(R.id.locationImg);
        Button btnMap = view.findViewById(R.id.btnNav);
        Button btnShare = view.findViewById(R.id.btnShare);

        Bundle bundle = getArguments();
        assert bundle != null;
        TourismLocationsData data = (TourismLocationsData) bundle.getSerializable("Location");
        name = data.getName();
        locationName.setText(data.getName());
        locationDescription.setText(data.getDesc());
        latDest = data.getLat();
        lngDest = data.getLng();
        locationImg.setImageResource(data.getImg());

        MapView mapView = view.findViewById(R.id.locationMap);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(requireActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(googleMap -> {
            MarkerOptions marker = new MarkerOptions().position(
                    new LatLng(latDest, lngDest)).title(name);
            LatLng position = new LatLng(latDest, lngDest);
            googleMap.addMarker(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(15.0f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
        });

        btnMap.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + name);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            startActivity(mapIntent);
        });

        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_fragment_locations_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.option_home:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_HomeFragment);
                return true;
            case R.id.option_route_planner:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_RoutePlannerFragment);
                return true;
            case R.id.option_tourist_locations:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_TourismLocationsFragment);
                return true;
            case R.id.option_interactive_map:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_InteractiveMapFragment);
                return true;
            case R.id.option_tourism_helper:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_TourismHelperFragment);
                return true;
            case R.id.option_help:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_HelpFragment);
                return true;
            case R.id.option_about:
                Navigation.findNavController(view).navigate(R.id.action_LocationsDetailFragment_to_AboutFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}