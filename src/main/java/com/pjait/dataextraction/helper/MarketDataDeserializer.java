package com.pjait.dataextraction.helper;

import com.pjait.dataextraction.entity.MarketData;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MarketDataDeserializer implements Deserializer<MarketData> {
    @Override
    public MarketData deserialize(String s, byte[] bytes) {
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            return (MarketData) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error deserializing value", e);
        }
    }
}
