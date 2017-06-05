package com.teamtreehouse.popularmovies.datamodel.mapper;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public abstract class Mapper<T1, T2> {

    public abstract T2 map(@NonNull T1 value);


    public List<T2> map(List<T1> values) {
        List<T2> returnValues = new ArrayList<>(values.size());
        for (T1 value : values) {
            returnValues.add(map(value));
        }
        return returnValues;
    }

}