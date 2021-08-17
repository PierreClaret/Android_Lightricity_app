package com.lightricity.station.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.database.LocalDatabase;

import java.util.Date;

import javax.annotation.Nullable;


// FIXME: change to the same database table name as was before the refactoring
@Table(name = "Sensor", database = LocalDatabase.class)
public class RuuviTagEntity extends BaseModel {

    @Column
    @PrimaryKey
    private String id;
    @Column
    private String url;
    @Column
    private int sensorID;
    @Column
    private int vendorID;
    @Column
    private int frame;
    @Column
    private int rssi;
    @Column
    private String name;
    @Column
    @Nullable
    private Double temperature;
    @Column
    @Nullable
    private Double humidity;
    @Column
    @Nullable
    private Double pressure;
    @Column
    private boolean favorite;
    @Column
    private double accelX;
    @Column
    private double accelY;
    @Column
    private double accelZ;
    @Column
    private double magX;
    @Column
    private double magY;
    @Column
    private double magZ;
    @Column
    private double voltage;
    @Column
    private Double light;
    @Column
    private Double sound;
    @Column
    private Double co2;
    @Column
    private Date updateAt;
    @Column
    private String gatewayUrl;
    @Column
    private int defaultBackground;
    @Column
    private String userBackground;
    @Column
    private String dataFormat;
    @Column
    private double txPower;
    @Column
    private int movementCounter;
    @Column
    private int measurementSequenceNumber;
    @Column
    public Date createDate;
    @Column
    private double humidityOffset;
    @Column
    private Date humidityOffsetDate;
    @Column
    public boolean connectable;
    @Column
    private Date lastSync;
    @Column
    private Date networkLastSync;
    @Column
    private String networkBackground;

    public RuuviTagEntity() {
    }

    public RuuviTagEntity(FoundSensor tag) {
        this.id = tag.getId();
        this.url = tag.getUrl();
        this.vendorID = tag.getVendorID() != null ? tag.getVendorID() : 0;
        this.frame = tag.getFrame() != null ? tag.getFrame() : 0;
        this.rssi = tag.getRssi() != null ? tag.getRssi() : 0;
        this.sensorID = tag.getSensorID() != null? tag.getSensorID() : 0;
        this.temperature = tag.getTemperature() != null ? tag.getTemperature() : 0.0;
        this.humidity = tag.getHumidity() != null ? tag.getHumidity() : 0.0;
        this.pressure = tag.getPressure() != null ? tag.getPressure() : 0.0;
        this.accelX = tag.getAccelX() != null ? tag.getAccelX() : 0.0;
        this.accelY = tag.getAccelY() != null ? tag.getAccelY() : 0.0;
        this.accelZ = tag.getAccelZ() != null ? tag.getAccelZ() : 0.0;
        this.magX = tag.getMagX() != null ? tag.getMagX() : 0.0;
        this.magY = tag.getMagY() != null ? tag.getMagY() : 0.0;
        this.magY = tag.getMagZ() != null ? tag.getMagZ() : 0.0;
        this.light = tag.getLight() != null ? tag.getLight() : 0.0;
        this.sound = tag.getSound() != null ? tag.getSound() : 0.0;
        this.co2 = tag.getCo2() != null ? tag.getCo2() : 0.0;
        this.voltage = tag.getVoltage() != null ? tag.getVoltage() : 0.0;
        this.dataFormat = tag.getDataFormat() != null ? tag.getDataFormat() : "";
        this.txPower = tag.getTxPower() != null ? tag.getTxPower() : 0;
        this.movementCounter = tag.getMovementCounter() != null ? tag.getMovementCounter() : 0;
        this.connectable = tag.getConnectable() != null ? tag.getConnectable() : false;
    }

    public void updateData(TagSensorReading reading) {
        this.rssi = reading.rssi;
        this.temperature = reading.temperature;
        this.humidity = reading.humidity;
        this.pressure = reading.pressure;
        this.accelX = reading.accelX;
        this.accelY = reading.accelY;
        this.accelZ = reading.accelZ;
        this.magX = reading.magX;
        this.magY = reading.magY;
        this.magZ = reading.magZ;
        this.light = reading.light;
        this.sound = reading.sound;
        this.co2 = reading.co2;
        this.voltage = reading.voltage;
        this.dataFormat = reading.dataFormat;
        this.txPower = reading.txPower;
        this.movementCounter = reading.movementCounter;
        this.measurementSequenceNumber = reading.measurementSequenceNumber;
        this.updateAt = reading.createdAt;
        this.networkLastSync = reading.createdAt;
    }

    public RuuviTagEntity preserveData(RuuviTagEntity tag) {
        tag.setName(this.getName());
        tag.setVendorID(this.getVendorID());
        tag.setSensorID(this.getSensorID());
        tag.setFrame(this.getFrame());
        tag.setCreateDate(this.getCreateDate());
        tag.setFavorite(this.isFavorite());
        tag.setGatewayUrl(this.getGatewayUrl());
        tag.setDefaultBackground(this.getDefaultBackground());
        tag.setUserBackground(this.getUserBackground());
        tag.setUpdateAt(new Date());
        tag.setHumidityOffset(this.getHumidityOffset());
        tag.setHumidityOffsetDate(this.getHumidityOffsetDate());
        tag.setLastSync(this.getLastSync());
        tag.setNetworkLastSync(this.getNetworkLastSync());
        tag.setNetworkBackground(this.getNetworkBackground());
        return tag;
    }

    public String getDisplayName() {
        return (this.getName() != null && !this.getName().isEmpty()) ? this.getName() : this.getId();
    }

    @org.jetbrains.annotations.Nullable
//@Override
    public String getId() {
        return id;
    }

    //@Override
    public void setId(String id) {
        this.id = id;
    }

    //@Override
    public int getSensorID() {
        return sensorID;
    }
    //@Override
    public void setSensorID(Integer sensorID) {
        this.sensorID = sensorID;
    }

    @org.jetbrains.annotations.Nullable
//@Override
    public String getUrl() {
        return url;
    }

    //@Override
    public void setUrl(String url) {
        this.url = url;
    }

    //@Override
    public int getVendorID() {
        return vendorID;
    }

    //@Override
    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    //@Override
    public int getFrame() {
        return frame;
    }

    //@Override
    public void setFrame(int frame) {
        this.frame = frame;
    }

    //@Override
    public Integer getRssi() {
        return rssi;
    }

    //@Override
    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }


    @org.jetbrains.annotations.Nullable
//@Override
    public String getName() {
        return name;
    }

    //@Override
    public void setName(String name) {
        this.name = name;
    }

    //@Override
    public Double getTemperature() {
        return temperature;
    }

    //@Override
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    //@Override
    public Double getHumidity() {
        return humidity;
    }

    //@Override
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    //@Override
    public @org.jetbrains.annotations.Nullable Double getPressure() {
        return pressure;
    }

    //@Override
    public void setPressure(@org.jetbrains.annotations.Nullable Double pressure) {
        this.pressure = pressure;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    //@Override
    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    //@Override
    public Double getAccelX() {
        return accelX;
    }

    //@Override
    public void setAccelX(Double accelX) {
        this.accelX = accelX;
    }

    //@Override
    public Double getAccelY() { return accelY; }

    //@Override
    public void setAccelY(Double accelY) {
        this.accelY = accelY;
    }

    //@Override
    public Double getAccelZ() {
        return accelZ;
    }

    //@Override
    public void setAccelZ(Double accelZ) {
        this.accelZ = accelZ;
    }

    //@Override
    public Double getMagX() {
        return magX;
    }

    //@Override
    public void setMagX(Double magX) {
        this.magX = magX;
    }

    //@Override
    public Double getMagY() {
        return magY;
    }

    //@Override
    public void setMagY(Double magY) {
        this.magY = magY;
    }

    //@Override
    public Double getMagZ() {
        return magZ;
    }

    //@Override
    public void setMagZ(Double magZ) {
        this.magZ = magZ;
    }

    //@Override
    public Double getLight() {
        return light;
    }

    //@Override
    public void setLight(Double light) {
        this.light = light;
    }
    //@Override
    public Double getSound() {
        return sound;
    }

    //@Override
    public void setSound(Double sound) {
        this.sound = sound;
    }

    //@Override
    public Double getCO2() {
        return co2;
    }

    //@Override
    public void setCO2(Double co2) {
        this.co2 = co2;
    }

    //@Override
    public Double getVoltage() {
        return voltage;
    }

    //@Override
    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    @org.jetbrains.annotations.Nullable
//@Override
    public Date getUpdateAt() {
        return updateAt;
    }

    //@Override
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @org.jetbrains.annotations.Nullable
//@Override
    public String getGatewayUrl() {
        return gatewayUrl;
    }

    //@Override
    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    //@Override
    public Integer getDefaultBackground() {
        return defaultBackground;
    }

    //@Override
    public void setDefaultBackground(Integer defaultBackground) {
        this.defaultBackground = defaultBackground;
    }

    //@Override
    public String getDataFormat() {
        return dataFormat;
    }

    //@Override
    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    //@Override
    public Double getTxPower() {
        return txPower;
    }

    //@Override
    public void setTxPower(Double txPower) {
        this.txPower = txPower;
    }

    //@Override
    public Integer getMovementCounter() {
        return movementCounter;
    }

    //@Override
    public void setMovementCounter(Integer movementCounter) {
        this.movementCounter = movementCounter;
    }

    //@Override
    @Nullable
    public Integer getMeasurementSequenceNumber() {
        return measurementSequenceNumber;
    }

    //@Override
    public void setMeasurementSequenceNumber(Integer measurementSequenceNumber) {
        this.measurementSequenceNumber = measurementSequenceNumber;
    }

    @org.jetbrains.annotations.Nullable
//@Override
    public String getUserBackground() {
        return userBackground;
    }

    //@Override
    public void setUserBackground(String userBackground) {
        this.userBackground = userBackground;
    }

    //@Override
    @Nullable
    public Boolean getFavorite() {
        return favorite;
    }

    @org.jetbrains.annotations.Nullable
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getHumidityOffset() {
        return humidityOffset;
    }

    public void setHumidityOffset(Double humidityOffset) {
        this.humidityOffset = humidityOffset;
    }

    @org.jetbrains.annotations.Nullable
    public Date getHumidityOffsetDate() {
        return humidityOffsetDate;
    }

    public void setHumidityOffsetDate(Date humidityOffsetDate) {
        this.humidityOffsetDate = humidityOffsetDate;
    }

    //@Override
    public Boolean getConnectable() {
        return connectable;
    }

    //@Override
    public void setConnectable(Boolean connectable) {
        this.connectable = connectable;
    }

    @org.jetbrains.annotations.Nullable
    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    @org.jetbrains.annotations.Nullable
    public Date getNetworkLastSync() {
        return networkLastSync;
    }

    public void setNetworkLastSync(Date networkLastSync) {
        this.networkLastSync = networkLastSync;
    }

    @org.jetbrains.annotations.Nullable
    public String getNetworkBackground() {
        return networkBackground;
    }

    public void setNetworkBackground(String networkBackground) {
        this.networkBackground = networkBackground;
    }
}