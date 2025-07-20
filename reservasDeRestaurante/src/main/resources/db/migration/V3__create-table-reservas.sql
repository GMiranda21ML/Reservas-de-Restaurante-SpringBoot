CREATE TABLE reservas (
    id SERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    mesa_id BIGINT NOT NULL,
    data_reserva TIMESTAMP NOT NULL,
    status_reserva VARCHAR(20) NOT NULL,

    CONSTRAINT fk_reserva_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_reserva_mesa
        FOREIGN KEY (mesa_id) REFERENCES mesas(id)
        ON DELETE CASCADE
);
