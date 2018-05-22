package org.team7.server.sensor.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageSetup extends SensorMessage {

    public SensorMessageSetup(int sensorID) {
        values.put("ID", sensorID);
    }

    @Override
    public int getOpCode() {
        return ops.get("SETUP");
    }

    @Override
    public String getOpName() {
        return "SETUP";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = (OPCODE_BYTES + SENSOR_ID_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(getOpCode());
        buf.putInt(values.get("ID"));

        return buf;
    }
}
