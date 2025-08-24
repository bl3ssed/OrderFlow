-- Добавляем столбец role (предполагая, что это строка)
ALTER TABLE users ADD COLUMN role VARCHAR(50);

-- Обновляем существующие записи (если нужно)
UPDATE users SET role = 'USER' WHERE role IS NULL;