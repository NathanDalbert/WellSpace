CREATE TABLE favoritos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    sala_id UUID NOT NULL,
    CONSTRAINT fk_favorito_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorito_sala FOREIGN KEY (sala_id) REFERENCES salas(salas_id) ON DELETE CASCADE,
    CONSTRAINT unique_favorito UNIQUE (usuario_id, sala_id)
);
