package by.sands.vitebsktransport.service;

import android.content.Context;
import by.sands.vitebsktransport.domain.Stop;
import by.sands.vitebsktransport.repository.StopRepository;

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

    public void open() {
        stopRepository.open();
    }

    public void close() {
        stopRepository.close();
    }
}
