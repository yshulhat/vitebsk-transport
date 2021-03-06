package com.spax.vitebsktransport.service;

import com.spax.vitebsktransport.domain.Route;
import com.spax.vitebsktransport.repository.RouteRepository;

import android.content.Context;

import java.util.List;

public class RouteService {
    private RouteRepository routeRepository;

    public RouteService(Context ctx) {
        this.routeRepository = new RouteRepository(ctx);
    }

    public Route create(Route route) {
        return routeRepository.create(route);
    }

    public Route create(String number, String name) {
        return routeRepository.create(new Route(number, name));
    }

    public void delete(long id) {
        routeRepository.delete(id);
    }

    public void delete(Route route) {
        routeRepository.delete(route.getId());
    }

    public List<Route> findAll() {
        return routeRepository.getAll();
    }

    public List<Route> findAll(String transportType) {
        return routeRepository.getAll(transportType);
    }

    public void open() {
        routeRepository.open();
    }

    public void close() {
        routeRepository.close();
    }
}
