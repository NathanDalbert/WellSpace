package com.WellSpace.modules.contato.DTO;

import java.util.UUID;

public record ContatoResponse(
                UUID contato_id,
                UUID usuario_id,
                String contato) {
}
