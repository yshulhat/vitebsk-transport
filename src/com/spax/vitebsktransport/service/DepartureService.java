package com.spax.vitebsktransport.service;

import android.content.Context;

import com.spax.vitebsktransport.domain.Departure;
import com.spax.vitebsktransport.domain.MoveTime;
import com.spax.vitebsktransport.domain.Time;
import com.spax.vitebsktransport.domain.TimeTableRecord;
import com.spax.vitebsktransport.repository.DepartureRepository;
import com.spax.vitebsktransport.repository.MoveTimeRepository;

import java.util.ArrayList;
import java.util.Collections;
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
        List<Long> path = getPath(directionId);
        List<MoveTime> times = moveTimeRepository.getAll(directionId);
        List<Departure> deps = departureRepository.getAll(directionId, day);
        for (Departure d : deps) {
            if (isStopInPath(path, stopId, d.getFromStopId(), d.getToStopId())) {
                String t = d.getTime();
                Time time = parseTime(t);
                int deltaTime = countDeltaTime(d, stopId, times);
                time.addMins(deltaTime);
                result.add(time);
            }
        }
        Collections.sort(result);
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
            if (!rec.getTimes().isEmpty()) {
                result.add(rec);
            }
        }
        return result;
    }

    public List<Long> getPath(long directionId) {
        List<Long> path = new ArrayList<Long>();
        List<MoveTime> times = moveTimeRepository.getAll(directionId);
        if (!times.isEmpty()) {
            path.add(times.get(0).getFromStopId());
            for (MoveTime mt : times) {
                path.add(mt.getToStopId());
            }
        }
        return path;
    }

    private boolean isStopInPath(List<Long> path, long selectedStop, long fromStop, long toStop) {
        int beginIdx = path.indexOf(fromStop);
        int endIdx = path.lastIndexOf(toStop);
        int stopIdx = path.indexOf(selectedStop);
        return stopIdx >= beginIdx && stopIdx <= endIdx;
//        boolean baseFound = false;
//        for (long s : path) {
//            if (!baseFound && s == fromStop) {
//                baseFound = true;
//            }
//            if (baseFound && s == selectedStop) {
//                return true;
//            }
//            if (s == toStop) {
//                break;
//            }
//        }
//        return false;
    }

    private int countDeltaTime(Departure dep, long stopId, List<MoveTime> times) {
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
