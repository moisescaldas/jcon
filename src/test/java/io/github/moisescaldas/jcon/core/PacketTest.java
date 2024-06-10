package io.github.moisescaldas.jcon.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PacketTest {
    @Test
    void testFromBytes() {
        // setup
        Packet packet = new Packet(0, PacketType.LOGIN_RESPONSE, "senha-foda");

        // act
        byte[] packetBytes = packet.toBytes();
        Packet result = Packet.fromBytes(packetBytes);

        // assert
        assertEquals(packet, result);
    }
}
