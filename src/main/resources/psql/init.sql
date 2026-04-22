-- Création de la base de données
CREATE DATABASE federation_agricultural;

\c federation_agricultural;

CREATE TABLE member (
                        id UUID PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender VARCHAR(10) NOT NULL,
                        address VARCHAR(255),
                        profession VARCHAR(255),
                        phone_number BIGINT,
                        email VARCHAR(255) UNIQUE,
                        occupation VARCHAR(50) NOT NULL,
                        collectivity_id UUID,
                        date_adhesion DATE NOT NULL,
                        CONSTRAINT fk_collectivity FOREIGN KEY (collectivity_id) REFERENCES collectivity(id) ON DELETE CASCADE
);

CREATE TABLE collectivity (
                              id UUID PRIMARY KEY,
                              number INT UNIQUE,
                              name VARCHAR(255) UNIQUE,
                              location VARCHAR(255) NOT NULL
);

CREATE TABLE collectivity_structure (
                                        id UUID PRIMARY KEY,
                                        collectivity_id UUID NOT NULL,
                                        president UUID NOT NULL,
                                        vice_president UUID NOT NULL,
                                        treasurer UUID NOT NULL,
                                        secretary UUID NOT NULL,
                                        CONSTRAINT fk_collectivity_structure FOREIGN KEY (collectivity_id) REFERENCES collectivity(id) ON DELETE CASCADE,
                                        CONSTRAINT fk_president FOREIGN KEY (president) REFERENCES member(id),
                                        CONSTRAINT fk_vice_president FOREIGN KEY (vice_president) REFERENCES member(id),
                                        CONSTRAINT fk_treasurer FOREIGN KEY (treasurer) REFERENCES member(id),
                                        CONSTRAINT fk_secretary FOREIGN KEY (secretary) REFERENCES member(id)
);

CREATE INDEX idx_member_collectivity ON member (collectivity_id);
CREATE INDEX idx_collectivity_number ON collectivity (number);
CREATE INDEX idx_collectivity_name ON collectivity (name);