ALTER TABLE salas ADD COLUMN nome_sala VARCHAR(255) NOT NULL;


ALTER TABLE salas ADD COLUMN disponibilidade_dia_semana VARCHAR(255) NOT NULL;


ALTER TABLE salas ADD COLUMN disponibilidade_inicio TIME NOT NULL;
ALTER TABLE salas ADD COLUMN disponibilidade_fim TIME NOT NULL;


DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'disponibilidade_sala_enum') THEN
        CREATE TYPE disponibilidade_sala_enum AS ENUM ('DISPONIVEL', 'OCUPADA', 'AGENDADA', 'INDISPONIVEL');
END $$;


ALTER TABLE salas ADD COLUMN disponibilidade disponibilidade_sala_enum NOT NULL;
