insert into reader value 
  ('READER_1', 0, NULL, NULL, NULL), 
  ('READER_2', 0, NULL, NULL, NULL), 
  ('READER_3', 0, NULL, NULL, NULL),
  ('READER_4', 0, NULL, NULL, NULL);

insert into logicalreader value 
  ('READERS_1',0 ), 
  ('READERS_2',0);

insert into readermapping value 
  ('READERS_1', 'READER_1'),
  ('READERS_1', 'READER_2'),
  ('READERS_2', 'READER_3'),
  ('READERS_2', 'READER_4');