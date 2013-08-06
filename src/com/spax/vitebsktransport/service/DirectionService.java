package com.spax.vitebsktransport.service;

import com.spax.vitebsktransport.domain.Direction;
import com.spax.vitebsktransport.repository.DirectionRepository;

import android.content.Context;

import java.util.List;

public class DirectionService {
    private DirectionRepository directionRepository;

    public DirectionService(Context ctx) {
        this.directionRepository = new DirectionRepository(ctx);
    }

    public Direction create(Direction route) {
        return directionRepository.create(route);
    }

    public Direction create(String name, long routeId) {
        return directionRepository.create(new Direction(name, routeId));
    }

    public void delete(long id) {
        directionRepository.delete(id);
    }

    public void delete(Direction route) {
        directionRepository.delete(route.getId());
    }

    public List<Direction> findAll() {
        return directionRepository.getAll();
    }

    public List<Direction> findAll(long routeId) {
        return directionRepository.getAll(routeId);
    }

    public void open() {
        directionRepository.open();
    }

    public void close() {
        directionRepository.close();
    }
}
