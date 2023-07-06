CREATE TABLE item
(
    id    serial primary key,
    name        text not null
);

CREATE TABLE users
(
    id    serial primary key,
    name        text not null,
	email        text not null
);

CREATE TABLE orders
(
    id    serial primary key,
    creation_date        timestamp,
	item_id bigint references item(id),
	quantity bigint,
	user_id bigint references users(id),
	status text
);

CREATE TABLE stock_movement
(
    id    serial primary key,
    creation_date        timestamp,
	item_id bigint references item(id),
	quantity bigint
);