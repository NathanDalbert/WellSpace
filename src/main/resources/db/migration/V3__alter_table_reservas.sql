ALTER TABLE reservas DROP CONSTRAINT fk_localidade;

ALTER TABLE reservas DROP COLUMN localidade_id;

ALTER TABLE reservas ADD COLUMN salas_id UUID;

ALTER TABLE reservas ADD CONSTRAINT fk_sala_reserva
FOREIGN KEY (salas_id) REFERENCES salas(salas_id) ON DELETE CASCADE;
