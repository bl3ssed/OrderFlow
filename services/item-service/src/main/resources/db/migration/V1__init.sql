-- Создание таблицы items
CREATE TABLE items (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       category_id BIGINT NOT NULL REFERENCES categories,
                       description TEXT,
                       status VARCHAR(255) NOT NULL DEFAULT 'AVAILABLE',
                       owner_id BIGINT NOT NULL,
                       created_at TIMESTAMP DEFAULT NOW(),
                       updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE categories(
  id BIGSERIAL PRIMARY KEY ,
  parent_category_id BIGINT REFERENCES categories,
  name  VARCHAR(255) NOT NULL
);

-- Индекс для быстрого поиска по имени
CREATE INDEX idx_items_name ON items(name);

-- Индекс для поиска по владельцу
CREATE INDEX idx_items_owner ON items(owner_id);
