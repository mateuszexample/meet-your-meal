insert into restaurant_owner (name, surname, phone)
values
('Rekin', 'Biznesu', '123456789'),
('Marta', 'Restauracja', '456789123'),
('Alfred', 'Pizzamaster', '789123456'),
('Robert', 'Thermomix', '987654321');

insert into address (address_id, country, city, postal_code, address, building_number, unit_number)
values
('1', 'Polska', 'Suwałki', '16-400', 'Kowalskiego', '67', '15'),
('2', 'Polska', 'Suwałki', '16-400', 'Pułaskiego', '1', '1'),
('3', 'Polska', 'Suwałki', '16-400', 'Reja', '11', '55');

insert into customer (name, surname, phone, address_id)
values
('Marzena', 'Niejadek', '654321987', '1'),
('Tomasz', 'Okrągły', '321987654', '2'),
('Kamil', 'Sucharek', '654987321', '3');

