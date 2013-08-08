package com.spax.vitebsktransport;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cyrilmottier.polaris.Annotation;
import com.cyrilmottier.polaris.MapCalloutView;
import com.cyrilmottier.polaris.PolarisMapView;
import com.cyrilmottier.polaris.PolarisMapView.OnAnnotationSelectionChangedListener;
import com.cyrilmottier.polaris.PolarisMapView.OnRegionChangedListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.service.StopService;

import java.util.ArrayList;
import java.util.List;

public class Map extends MapActivity implements OnRegionChangedListener, OnAnnotationSelectionChangedListener {
    private StopService stopService;
    private static final GeoPoint VITEBSK = new GeoPoint(55194744, 30202209);

    private PolarisMapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        stopService = new StopService(this);

        // mapView = new PolarisMapView(this, "AIzaSyDR3n6J0RYw0_OLdR-ZDWpSQVGrZz69sWs"); // release
        // key
        mapView = new PolarisMapView(this, "AIzaSyDMT5-O5mi8b4QPMUtz9agnawc5F9jXidQ"); // debug key
        mapView.setUserTrackingButtonEnabled(true);
        mapView.setOnRegionChangedListenerListener(this);
        mapView.setOnAnnotationSelectionChangedListener(this);

        List<Stop> stops = stopService.findAll();
        final List<Annotation> annotations = stopsToAnnotations(stops);

        mapView.setAnnotations(annotations, R.drawable.pin_bus);
        mapView.setBuiltInZoomControls(true);

        final FrameLayout mapViewContainer = (FrameLayout) findViewById(R.id.map_view_container);
        mapViewContainer.addView(mapView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        MapController mc = mapView.getController();
        mc.setZoom(14);
        mc.animateTo(VITEBSK);
    }

    private List<Annotation> stopsToAnnotations(List<Stop> stops) {
        List<Annotation> result = new ArrayList<Annotation>();
        for (Stop s : stops) {
            result.add(new Annotation(new GeoPoint(s.getLatitudeE6(), s.getLongitudeE6()), s.getName(), "id: "
                    + s.getId()));
        }
        return result;
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
        // Log.i(null, "onRegionChanged");
        // Log.i(null, "Zoom: " + mapView.getZoomLevel());
    }

    @Override
    public void onRegionChangeConfirmed(PolarisMapView mapView) {
        // Log.i(null, "onRegionChangeConfirmed");
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // MenuItem searchItem = menu.findItem(R.id.action_search);
        // SearchView searchView = (SearchView) searchItem.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

}
