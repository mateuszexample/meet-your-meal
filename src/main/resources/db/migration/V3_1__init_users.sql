ALTER TABLE restaurant_owner
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES meet_your_meal_user (user_id);

ALTER TABLE customer
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES meet_your_meal_user (user_id);

insert into meet_your_meal_user (user_id, user_name, email, password, active) values (1, 'rekin_biznesu', 'r.biznesu@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into meet_your_meal_user (user_id, user_name, email, password, active) values (2, 'marta_restauracja', 'm.restauracja@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into meet_your_meal_user (user_id, user_name, email, password, active) values (3, 'alfred_pizzamaster', 'a.pizzamaster@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into meet_your_meal_user (user_id, user_name, email, password, active) values (4, 'kasia_thermomix', 'k.thermomix@gmail.com', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into meet_your_meal_user (user_id, user_name, email, password, active) values (5, 'marzena_niejadek', 'marzena_niejadek@gmail.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into meet_your_meal_user (user_id, user_name, email, password, active) values (6, 'tomasz_okrągły', 'tomasz_okrągły@gmail.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into meet_your_meal_user (user_id, user_name, email, password, active) values (7, 'kamil_sucharek', 'kamil_sucharek@gmail.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into meet_your_meal_user (user_id, user_name, email, password, active) values (8, 'test_user', 'test_user@gmail.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

UPDATE restaurant_owner SET user_id = 1 WHERE pesel = '94051311769';
UPDATE restaurant_owner SET user_id = 2 WHERE pesel = '95061422870';
UPDATE restaurant_owner SET user_id = 3 WHERE pesel = '96071433971';
UPDATE restaurant_owner SET user_id = 4 WHERE pesel = '95073322230';

UPDATE customer SET user_id = 5 WHERE phone = '654321987';
UPDATE customer SET user_id = 6 WHERE phone = '321987654';
UPDATE customer SET user_id = 7 WHERE phone = '654987321';

insert into meet_your_meal_role (role_id, role) values (1, 'RESTAURANT_OWNER'), (2, 'CUSTOMER'), (3, 'REST_API');

insert into meet_your_meal_user_role (user_id, role_id) values (1, 1), (2, 1), (3, 1), (4, 1);
insert into meet_your_meal_user_role (user_id, role_id) values (1, 2), (2, 2), (3, 2), (4, 2);
insert into meet_your_meal_user_role (user_id, role_id) values (5, 2), (6, 2), (7, 2);
insert into meet_your_meal_user_role (user_id, role_id) values (8, 3);

ALTER TABLE restaurant_owner
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE customer
ALTER COLUMN user_id SET NOT NULL;