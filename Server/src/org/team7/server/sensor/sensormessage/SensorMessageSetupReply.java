package org.team7.server.sensor.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageSetupReply extends SensorMessage {

    private static final int X_BYTES = 4;
    private static final int Y_BYTES = 4;

    public SensorMessageSetupReply(int sensorId, int x, int y) {
        values.put("ID", sensorId);
        values.put("X", x);
        values.put("Y", y);
    }

    @Override
    public int getOpCode() {
        return SensorMessage.ops.get("SETUPREPLY");
    }

    @Override
    public String getOpName() {
        return "SETUPREPLY";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = (OPCODE_BYTES + SENSOR_ID_BYTES + X_BYTES + Y_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(getOpCode());
        buf.putInt(values.get("ID"));
        buf.putInt(values.get("X"));
        buf.putInt(values.get("Y"));

        return buf;
    }
}
