-- USERS
INSERT INTO users (id, email, first_name, last_name, phone, role, image, password)
VALUES (10, 'user@example.com', 'John', 'Doe', '+7234567890', 'USER', '/images/avatars/1.jpg', '$2a$10$hFcnDCyWHx1I8NC82bXZQuFuQZJ7tjbI8wG6b2GpE55a0OhP3zlSq');

-- ADS
INSERT INTO ads (id, author_id, title, price, description, image)
VALUES
(10, 10, 'Old Bike', 500, 'Used bicycle', '/images/ads/1.jpg'),
(11, 10, 'New Laptop', 1500, 'Barely used', '/images/ads/2.jpg');

-- COMMENTS
INSERT INTO comments (author_id, author_image, author_first_name, created_at, text)
VALUES
(10, '/images/avatars/1.jpg', 'John', 1711373600000, 'Отличный велосипед!'),
(10, '/images/avatars/1.jpg', 'John', 1711374600000, 'А есть скидка?');
