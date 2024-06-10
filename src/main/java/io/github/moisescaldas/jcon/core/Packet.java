package io.github.moisescaldas.jcon.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Packet {
    private static final int MINIMAL_PACKET_SIZE = 10;

    private int id;
    private PacketType type;
    private String body;

    public byte[] toBytes() {
        int packetLength = packetSize();
        ByteBuffer buffer = ByteBuffer.allocate(4 + packetLength).order(ByteOrder.LITTLE_ENDIAN);
        
        buffer.putInt(packetLength);
        buffer.putInt(id);
        buffer.putInt(type.getCode());
        buffer.put(body.getBytes());
        
        // terminators
        buffer.put((byte) 0);
        buffer.put((byte) 0);

        return buffer.array();
    }

    public static Packet fromBytes(byte[] bytes) {
        int packetLength = getInt(Arrays.copyOfRange(bytes, 0, 4));
        int id = getInt(Arrays.copyOfRange(bytes, 4, 8));
        PacketType type = PacketType.fromCodeAndServerResponse(getInt(Arrays.copyOfRange(bytes, 8, 12)), true);
        String body = new String(Arrays.copyOfRange(bytes, 12, 12 + packetLength - MINIMAL_PACKET_SIZE));

        return new Packet(id, type, body);
    }

    private static int getInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private int packetSize() {
        return MINIMAL_PACKET_SIZE + body.length();
    }
}
