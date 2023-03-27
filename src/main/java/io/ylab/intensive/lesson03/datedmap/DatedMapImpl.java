package io.ylab.intensive.lesson03.datedmap;

import java.util.*;

public class DatedMapImpl implements DatedMap {
    private final Map<String, String> map;
    private final Map<String,Date> dates;

    public DatedMapImpl() {
        this.map = new HashMap<>();
        this.dates = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
        dates.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
        dates.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return dates.getOrDefault(key, null);
    }
}
