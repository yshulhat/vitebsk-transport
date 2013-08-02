package by.sands.vitebsktransport.service;

import android.content.Context;
import by.sands.vitebsktransport.domain.Departure;
import by.sands.vitebsktransport.domain.MoveTime;
import by.sands.vitebsktransport.domain.Time;
import by.sands.vitebsktransport.domain.TimeTableRecord;
import by.sands.vitebsktransport.repository.DepartureRepository;
import by.sands.vitebsktransport.repository.MoveTimeRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartureService {
    private DepartureRepository departureRepository;
    private MoveTimeRepository moveTimeRepository;

    public DepartureService(Context ctx) {
        departureRepository = new DepartureRepository(ctx);
        moveTimeRepository = new MoveTimeRepository(ctx);
    }

    public Departure create(Departure route) {
        return departureRepository.create(route);
    }

    public void delete(long id) {
        departureRepository.delete(id);
    }

    public void delete(Departure departure) {
        departureRepository.delete(departure.getId());
    }

    public List<Departure> findAll() {
        return departureRepository.getAll();
    }

    public List<Departure> findAll(long directionId, long stopId, String day) {
        return departureRepository.getAll(directionId, day);
    }

    public List<Time> getTimes(long directionId, long stopId, String day) {
        List<Time> result = new ArrayList<Time>();
        List<Departure> deps = departureRepository.getAll(directionId, day);
        for (Departure d : deps) {
            String t = d.getTime();
            Time time = parseTime(t);
            int deltaTime = countDeltaTime(d, stopId);
            time.addMins(deltaTime);
            result.add(time);
        }
        return result;
    }

    public List<TimeTableRecord> getTimeTable(long directionId, long stopId, String day) {
        List<TimeTableRecord> result = new ArrayList<TimeTableRecord>();
        List<Time> times = getTimes(directionId, stopId, day);
        if (!times.isEmpty()) {
            int hour = times.get(0).getHours();
            TimeTableRecord rec = new TimeTableRecord();
            for (Time t : times) {
                if (t.getHours() == hour) {
                    rec.addTime(t);
                } else {
                    result.add(rec);
                    rec = new TimeTableRecord();
                    hour = t.getHours();
                    rec.addTime(t);
                }
            }
        }
        return result;
    }

    private int countDeltaTime(Departure dep, long stopId) {
        List<MoveTime> times = moveTimeRepository.getAll(dep.getDirectionId());
        int result = 0;
        boolean foundFirstStop = false;
        for (MoveTime t : times) {
            if (!foundFirstStop && t.getFromStopId() == dep.getFromStopId()) {
                foundFirstStop = true;
            }
            if (t.getFromStopId() == stopId) {
                break;
            }
            if (foundFirstStop) {
                result += t.getTime();
            }
        }
        return result;
    }

    private Time parseTime(String t) {
        String[] arr = t.split(":");
        return new Time(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
    }

    public List<String> findDaysForDirection(long directionId) {
        return departureRepository.getDaysForDirection(directionId);
    }

    public void open() {
        departureRepository.open();
    }

    public void close() {
        departureRepository.close();
    }
}
