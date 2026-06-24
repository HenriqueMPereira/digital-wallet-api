CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    source_wallet_id BIGINT REFERENCES wallet(id),
    destination_wallet_id BIGINT REFERENCES wallet(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);