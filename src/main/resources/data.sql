INSERT INTO transacao (id, tipo, valor, moeda, data) VALUES
    (1, 'SAQUE', 1000, 'BTC', CURRENT_TIMESTAMP),
    (2, 'SAQUE', 5000, 'ETC', CURRENT_TIMESTAMP);

INSERT INTO transacao_historico (transacao_id, tipo, valor, moeda, operacao, data_operacao) VALUES
    (1, 'SAQUE', 1000, 'BTC', 'CREATE', CURRENT_TIMESTAMP),
    (2, 'SAQUE', 5000, 'ETC', 'CREATE', CURRENT_TIMESTAMP);