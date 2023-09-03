package com.pjait.dataextraction.helper;

import com.pjait.dataextraction.entity.MarketData;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MarketDataSerializer implements Serializer<MarketData> {
    @Override
    public byte[] serialize(String s, MarketData data) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(data);
            objOut.flush();
            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error serializing value", e);
        }
    }
}
