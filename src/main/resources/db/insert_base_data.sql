
INSERT INTO t_team (product, name, default_location)
VALUES ('Plant', 'The Gardeners', 'Lisbon'),
       ('Car', 'Stars', 'Porto'),
       ('Moto', 'Speedsters', 'Braga'),
       ('Dashboards', 'The Analytics', 'Lisbon'),
       ('Car', 'Wheels', 'Porto'),
       ('Car', 'Sonic Team', 'Porto');

SELECT * FROM t_team;

INSERT INTO t_team_member(team_id, ctw_id, name)
VALUES ((SELECT id FROM t_team where name = 'Stars'), 'CTW0001', 'João Pires'),
       ((SELECT id FROM t_team where name = 'Stars'), 'CTW0002', 'Amália Rodrigues'),
       ((SELECT id FROM t_team where name = 'Speedsters'), 'CTW0003', 'Alberto Meireles'),
       ((SELECT id FROM t_team where name = 'The Analytics'), 'CTW0004', 'Ana Luísa'),
       ((SELECT id FROM t_team where name = 'The Analytics'), 'CTW0005', 'António Costa'),
       ((SELECT id FROM t_team where name = 'Sonic Team'), 'CTW0006', 'Catarina Silva');
--
-- INSERT INTO t_rack (serial_number, team_id, default_location, status)
-- VALUES ('1000-12021-01', (SELECT id FROM t_team where name = 'Stars'), 'PORTO', 'AVAILABLE'),
--        ('1000-12021-02', (SELECT id FROM t_team where name = 'Speedsters'), 'PORTO', 'UNAVAILABLE'),
--        ('2222-10000-01', (SELECT id FROM t_team where name = 'The Gardeners'), 'LISBON', 'UNAVAILABLE'),
--        ('1000-12021-03', (SELECT id FROM t_team where name = 'Wheels'), 'BRAGA', 'AVAILABLE'),
--        ('3100-11031-01', (SELECT id FROM t_team where name = 'Wheels'), 'PORTO', 'AVAILABLE');
--
--
-- INSERT INTO t_rack_asset (asset_tag, rack_id)
-- VALUES ('ABCDEF001', (SELECT id FROM t_rack where serial_number = '1000-12021-01')),
--        ('ABCDEF002', (SELECT id FROM t_rack where serial_number = '1000-12021-02')),
--        ('ABCDEF003', (SELECT id FROM t_rack where serial_number = '2222-10000-01')),
--        ('ABCDEF004', (SELECT id FROM t_rack where serial_number = '1000-12021-03')),
--        ('ABCDEF005', (SELECT id FROM t_rack where serial_number = '3100-11031-01'));
--
-- SELECT id, name FROM t_team_member;
--
-- INSERT INTO t_booking (rack_id, requester_id, book_from, book_to)
-- VALUES ((SELECT id FROM t_rack where serial_number = '1000-12021-01'), (SELECT id FROM t_team_member WHERE name = 'João Pires'), '2024-01-01', '2024-01-31'),
--        ((SELECT id FROM t_rack where serial_number = '3100-11031-01'), (SELECT id FROM t_team_member WHERE name = 'Amália Rodrigues'), '2024-07-01', '2025-03-01'),
--        ((SELECT id FROM t_rack where serial_number = '1000-12021-02'), (SELECT id FROM t_team_member WHERE name = 'Ana Luísa'), '2024-03-01', '2024-05-15'),
--        ((SELECT id FROM t_rack where serial_number = '1000-12021-03'), (SELECT id FROM t_team_member WHERE name = 'Alberto Meireles'), '2024-01-01', '2024-03-01'),
--        ((SELECT id FROM t_rack where serial_number = '1000-12021-03'), (SELECT id FROM t_team_member WHERE name = 'Catarina Silva'), '2024-04-01', '2024-04-30');