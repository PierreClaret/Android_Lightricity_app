package com.lightricity.station.gateway.data;

import android.content.Context;

import com.lightricity.station.model.Event;
import com.lightricity.station.database.tables.RuuviTagEntity;

/**
 * Created by berg on 18/09/17.
 */

public class ScanEventSingle extends Event {
    public RuuviTagEntity tag;

    public ScanEventSingle(Context context, String deviceId) {
        super(context, deviceId);
    }
}
