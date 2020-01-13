INSERT INTO USER (id,name, birthdate, username, password,email) VALUES
  ('51','hamid', '1994-12-04', 'hamidfarmani','123456','h@f.com'),
  ('52','negin', '1996-10-05', 'neg','123','n@n.com');


INSERT INTO Subscription (id,creation_date, action_type,description,user_id) VALUES
  ('53', '2019-05-05', 'SUBSCRIBED','desc 1',51),
  ('54', '2019-10-05', 'SUBSCRIBED','desc 2',52);

