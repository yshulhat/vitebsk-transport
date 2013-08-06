package com.spax.vitebsktransport.service;

import com.spax.vitebsktransport.domain.Stop;
import com.spax.vitebsktransport.repository.StopRepository;

import android.content.Context;

import java.util.List;

public class StopService {
    private StopRepository stopRepository;

    public StopService(Context ctx) {
        this.stopRepository = new StopRepository(ctx);
    }

    public Stop create(Stop route) {
        return stopRepository.create(route);
    }

    public void delete(long id) {
        stopRepository.delete(id);
    }

    public void delete(Stop route) {
        stopRepository.delete(route.getId());
    }

    public List<Stop> findAll() {
        return stopRepository.getAll();
    }

    public List<Stop> findByDirection(long directionId) {
        return stopRepository.getForDirection(directionId);
    }

    public Stop find(long stopId) {
        return stopRepository.getById(stopId);
    }

    public void open() {
        stopRepository.open();
    }

    public void close() {
        stopRepository.close();
    }
}
