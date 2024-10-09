CREATE TABLE contatos (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          contato VARCHAR(255) NOT NULL,
                          created_date DATE NOT NULL DEFAULT CURRENT_DATE,
                          profissional_id INT,
                          FOREIGN KEY (profissional_id) REFERENCES profissionais(id) ON DELETE CASCADE
);