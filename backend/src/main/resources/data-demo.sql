INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Alice Svensson',
       'alice.svensson@example.com',
       1001,
       true,
       'Client A',
       'Stella Asplund',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 0 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Bob Eriksson',
       'bob@example.com',
       1002,
       true,
       'Client A',
       'Stella Asplund',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 1 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Leona Rehnquist',
       'leona@example.com',
       1002,
       true,
       'Client A',
       'Stella Asplund',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 2 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Gabriella Tornquist',
       'gabriella@example.com',
       1002,
       true,
       'Client A',
       'Stella Asplund',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 3 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Kira Lagerlöf',
       'kira@example.com',
       1002,
       true,
       'Client A',
       'Stella Asplund',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 4 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Denise Ceder',
       'denise@example.com',
       1002,
       true,
       'Client B',
       'Monica Sjöström',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 5 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Faje Lindblad',
       'faje@example.com',
       1002,
       true,
       'Client B',
       'Monica Sjöström',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 6 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Alf Forsberg',
       'alf@example.com',
       1002,
       true,
       'Client B',
       'Monica Sjöström',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 7 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Ulf Lindahl',
       'ulf@example.com',
       1002,
       true,
       'Client B',
       'Monica Sjöström',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 8 ON CONFLICT DO NOTHING;

INSERT INTO demo_consultant (id, full_name, email, timekeeper_id, active, client, responsiblept, country)
SELECT c.id,
       'Bertil Skarsgård',
       'bertil@example.com',
       1002,
       true,
       'Client B',
       'Monica Sjöström',
       'Sverige'
FROM consultant c
WHERE c.active IS true
ORDER BY c.id
LIMIT 1 OFFSET 9 ON CONFLICT DO NOTHING;