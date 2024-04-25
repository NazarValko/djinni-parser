-- Create a table for storing the providers of links if it does not already exist
CREATE TABLE IF NOT EXISTS link_providers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Primary key, automatically incrementing to ensure uniqueness
    name TEXT NOT NULL                     -- The domain name of the provider
);

-- Create a table for storing links from parser if it does not already exist
CREATE TABLE IF NOT EXISTS parsers_links (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Primary key, automatically incrementing to ensure uniqueness
    FK_provider_id INT NOT NULL,           -- Foreign key linking to the link_providers table (provider of links)
    link TEXT NOT NULL,                    -- Links obtained from the site not just one
    FOREIGN KEY (FK_provider_id) REFERENCES link_providers(id)  -- Ensures integrity by referencing the primary key of the link_providers table
);

