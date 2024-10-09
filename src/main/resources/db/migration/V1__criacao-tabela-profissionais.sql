CREATE TABLE profissionais (
                               id SERIAL PRIMARY KEY,
                               nome VARCHAR(255) NOT NULL,
                               cargo VARCHAR(50) NOT NULL,
                               nascimento DATE NOT NULL,
                               created_date DATE NOT NULL DEFAULT CURRENT_DATE
);