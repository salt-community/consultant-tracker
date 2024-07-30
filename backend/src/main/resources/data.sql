INSERT INTO consultant(id, active, email, full_name, phone_number, timekeeper_id, salt_user_id)
VALUES ('785e60ac-0f34-44bb-b2c8-ed724e467772', true,'jagoda.bodnar@appliedtechnology.se','Jagoda Bodnar',null, 22784,null),
       ('afe0bab1-1b3c-43e9-ae18-651909d317c5', true, 'kostadinka.karova@appliedtechnology.se', 'Kostadinka Karova', null, 22748, null),
       ('19f853cf-5769-4153-99bf-90fd972ff253', true, 'mauro.morales@appliedtechnology.se', 'Mauro Morales', null, 22749, null),
       ('2db569df-0032-40ed-8d68-cabf07160aaf', true, 'kevin.gida@appliedtechnology.se', 'Kevin Gida', null,22422, null)
on conflict do nothing;
