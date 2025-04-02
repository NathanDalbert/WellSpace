CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE usuario (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    integridade BOOLEAN NOT NULL,
    data_nascimento DATE NOT NULL,
    user_role VARCHAR(100) NOT NULL
);

CREATE TABLE contato (
    id_contato UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    telefone VARCHAR(255) NOT NULL,
    id_usuario UUID,
    CONSTRAINT fk_contato_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE
);


 CREATE TABLE localidades (
     localidade_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     nome_local VARCHAR(255) NOT NULL,
     descricao TEXT NOT NULL,
     localizacao VARCHAR(255) NOT NULL,
     locador_id UUID,
     CONSTRAINT fk_locador FOREIGN KEY (locador_id) REFERENCES usuario(id)
 );

CREATE TABLE salas (
    salas_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    localidade_id UUID,
    descricao TEXT NOT NULL,
    tamanho VARCHAR(255) NOT NULL,
    preco_por_hora DECIMAL(10, 2),
    tempo_inicio_disponivel TIME,
    tempo_fim_disponivel TIME,
    CONSTRAINT fk_localidade FOREIGN KEY (localidade_id) REFERENCES localidades(localidade_id) ON DELETE CASCADE
);

CREATE TABLE sala_imagens (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sala_id UUID,
    imagem VARCHAR(255) NOT NULL,
    CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES salas(salas_id) ON DELETE CASCADE
);

CREATE TABLE sala_servicos (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sala_id UUID,
    tipo_servico VARCHAR(255) NOT NULL,
    CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES salas(salas_id) ON DELETE CASCADE
);

CREATE TABLE reservas (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    localidade_id UUID,
    locatario_id UUID,
    locador_id UUID,
    tempo_inicio TIME,
    tempo_fim TIME,
    data_reserva DATE,
    status VARCHAR(20),
    CONSTRAINT fk_localidade FOREIGN KEY (localidade_id) REFERENCES localidades(localidade_id) ON DELETE CASCADE,
    CONSTRAINT fk_locatario FOREIGN KEY (locatario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_locador FOREIGN KEY (locador_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE avaliacao (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sala_id UUID,
    quant_Estrelas INT,
    CONSTRAINT fk_sala FOREIGN KEY (sala_id) REFERENCES salas(salas_id) ON DELETE CASCADE
);
