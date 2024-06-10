package io.github.moisescaldas.jcon.core;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Jcon implements Closeable {

    private Socket socket;
    private Integer id;
    private boolean authenticated = false;

    public Jcon(String address, int port, String password) throws IOException {
        this.socket = new Socket(address, port);
        this.id = new SecureRandom().nextInt();

        if (!authenticate(password)) {
            throw new IOException("Falha durante a execução do login");
        }
    }

    public String sendCommand(String command) throws IOException {
        Packet pakcetCommand = new Packet(this.id++, PacketType.COMMAND, command);
        
        return this.send(pakcetCommand).getBody();
    }

    private boolean authenticate(String password) throws IOException {
        Packet auth = new Packet(this.id, PacketType.LOGIN, password);
        Packet authResponse = send(auth);

        if (this.id.equals(authResponse.getId())) {
            this.id++;
            return true;
        }

        return false;
    }

    private Packet send(Packet packet) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.write(packet.toBytes());
        out.flush();

        DataInputStream in = new DataInputStream(socket.getInputStream());
        while (in.available() == 0);
        
        byte[] responseBytes = new byte[in.available()];
        for (int x = 0; x < responseBytes.length; x++) {
            responseBytes[x] = in.readByte();
        }                

        return Packet.fromBytes(responseBytes);
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(socket)) {
            socket.close();
        }
    }
}
