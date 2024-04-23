CREATE TABLE IF NOT EXISTS link_providers (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS parsers_links (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        FK_provider_id INT NOT NULL,
        link VARCHAR(255) NOT NULL,
        FOREIGN KEY (FK_provider_id) REFERENCES link_providers(id));

