package io.github.moisescaldas.jcon.core;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PacketType {
    LOGIN(3, false), LOGIN_RESPONSE(2, true), COMMAND(2, false), SERVER_RESPONSE(0, true);    
    private Integer code;
    private Boolean serverResponse;

    public static final PacketType fromCodeAndServerResponse(int code, boolean serverResponse) {
        for (PacketType type : PacketType.values()) {
            if (type.getCode().equals(code) && type.getServerResponse().equals(serverResponse)) {
                return type;
            }
        }

        throw new IllegalArgumentException(MessageFormat.format("Não existe PacketType para o codigo={0} e serverResponse={1}", code, serverResponse));
    }
}
