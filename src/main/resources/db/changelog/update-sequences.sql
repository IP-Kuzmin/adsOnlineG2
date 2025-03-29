SELECT setval(pg_get_serial_sequence('users', 'id'), 5, true);
SELECT setval(pg_get_serial_sequence('ads', 'id'), 5, true);
SELECT setval(pg_get_serial_sequence('comments', 'id'), 5, true);