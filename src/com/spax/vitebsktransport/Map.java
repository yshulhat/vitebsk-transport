package com.spax.vitebsktransport;

import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cyrilmottier.polaris.Annotation;
import com.cyrilmottier.polaris.MapCalloutView;
import com.cyrilmottier.polaris.PolarisMapView;
import com.cyrilmottier.polaris.PolarisMapView.OnAnnotationSelectionChangedListener;
import com.cyrilmottier.polaris.PolarisMapView.OnRegionChangedListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.spax.vitebsktransport.R;

import java.util.Arrays;
import java.util.List;

public class Map extends MapActivity implements OnRegionChangedListener, OnAnnotationSelectionChangedListener {
    private static final Annotation VITEBSK =
            new Annotation(new GeoPoint(55194744, 30202209), "Vitebsk", "Some info about Vitebsk");

    private PolarisMapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = new PolarisMapView(this, "AIzaSyDMT5-O5mi8b4QPMUtz9agnawc5F9jXidQ");
//        mapView = new PolarisMapView(this, "AIzaSyDR3n6J0RYw0_OLdR-ZDWpSQVGrZz69sWs");
        mapView.setUserTrackingButtonEnabled(true);
        mapView.setOnRegionChangedListenerListener(this);
        mapView.setOnAnnotationSelectionChangedListener(this);

        final List<Annotation> annotations = Arrays.asList(VITEBSK);

        mapView.setAnnotations(annotations, R.drawable.map_pin_holed_blue_normal);
        mapView.setBuiltInZoomControls(true);

        final FrameLayout mapViewContainer = (FrameLayout) findViewById(R.id.map_view_container);
        mapViewContainer.addView(mapView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onRegionChanged(PolarisMapView mapView) {
        Log.i(null, "onRegionChanged");
    }

    @Override
    public void onRegionChangeConfirmed(PolarisMapView mapView) {
        Log.i(null, "onRegionChangeConfirmed");
    }

    @Override
    public void onAnnotationSelected(PolarisMapView mapView, MapCalloutView calloutView, int position,
            Annotation annotation) {
        Log.i(null, "onAnnotationSelected");
        calloutView.setDisclosureEnabled(true);
        calloutView.setClickable(true);
        if (!TextUtils.isEmpty(annotation.getSnippet())) {
            calloutView.setLeftAccessoryView(getLayoutInflater().inflate(R.layout.accessory, calloutView, false));
        } else {
            calloutView.setLeftAccessoryView(null);
        }
    }

    @Override
    public void onAnnotationDeselected(PolarisMapView mapView, MapCalloutView calloutView, int position,
            Annotation annotation) {
        Log.i(null, "onAnnotationDeselected");
    }

    @Override
    public void onAnnotationClicked(PolarisMapView mapView, MapCalloutView calloutView, int position,
            Annotation annotation) {
        Log.i(null, "onAnnotationClicked");
        Toast.makeText(this, getString(R.string.annotation_clicked, annotation.getTitle()), Toast.LENGTH_SHORT).show();
    }


}
