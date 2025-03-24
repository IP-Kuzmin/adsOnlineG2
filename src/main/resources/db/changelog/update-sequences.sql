SELECT setval(pg_get_serial_sequence('users', 'id'), 3, true);
SELECT setval(pg_get_serial_sequence('ads', 'id'), 2, true);
SELECT setval(pg_get_serial_sequence('comments', 'id'), 2, true);