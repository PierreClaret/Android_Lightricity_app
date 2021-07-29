package com.lightricity.station.gateway.data;

import android.content.Context;

import com.lightricity.station.model.Event;
import com.lightricity.station.database.tables.RuuviTagEntity;

import java.util.ArrayList;

/**
 * Created by ISOHAJA on 14.7.2017.
 */

public class ScanEvent extends Event {
    public ArrayList<RuuviTagEntity> tags = new ArrayList<>();

    public ScanEvent(Context context, String deviceId) {
        super(context, deviceId);
    }
}


