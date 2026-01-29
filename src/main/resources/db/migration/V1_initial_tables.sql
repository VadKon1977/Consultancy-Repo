CREATE TABLE clients (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   first_name VARCHAR(255) NOT NULL,
   second_name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   phone VARCHAR(50) NOT NULL
   );

CREATE TABLE consultants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    specialization VARCHAR(100),
    experience_years INT
);

CREATE TABLE services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price_per_hour DECIMAL(10, 2)
);

CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT,
    consultant_id BIGINT,
    service_id BIGINT,
    scheduled_at TIMESTAMP,
    status VARCHAR(50),
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_consultant FOREIGN KEY (consultant_id) REFERENCES consultants(id),
    CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES services(id)
);

CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT NOT NULL,
    rating INT,
    comment TEXT,
    CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);