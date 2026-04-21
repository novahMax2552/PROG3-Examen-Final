CREATE TABLE member (
                        id UUID PRIMARY KEY,
                        first_name VARCHAR(255),
                        last_name VARCHAR(255),
                        birth_date DATE,
                        gender VARCHAR(10),
                        address VARCHAR(255),
                        profession VARCHAR(255),
                        phone_number BIGINT,
                        email VARCHAR(255),
                        occupation VARCHAR(50),
                        collectivity_id UUID,
                        date_adhesion DATE
                    );
