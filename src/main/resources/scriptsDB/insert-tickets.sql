INSERT INTO tickets (datetime_entry, datetime_exit, entry_cancel, exit_cancel, final_price, primary_vacancies_occupied, parked, vehicle_plate)
VALUES
    ('2024-09-29 10:00:00.123456', NULL, 1, NULL, NULL, 2, 1, 'XYZ-1111'),
    ('2024-09-29 11:15:00.654321', '2024-09-29 12:45:00.987654', 2, 6, 12.50, 2, 0, 'XYZ-2222'),
    ('2024-09-29 14:00:00.123456', NULL, 3, NULL, NULL, 1, 1, 'XYZ-3333'),
    ('2024-09-30 08:30:00.654321', '2024-09-30 09:00:00.987654', 4, 7, 5.00, 2, 0, 'XYZ-4444'),
    ('2024-09-30 15:45:00.123456', NULL, 5, NULL, NULL, 1, 1, 'XYZ-5555');
