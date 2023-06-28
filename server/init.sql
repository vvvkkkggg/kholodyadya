CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       first_name VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          product_name VARCHAR(50) NOT NULL,
                          category VARCHAR(50) NOT NULL,
                          shelf_life VARCHAR(50) NOT NULL
);

CREATE TABLE product_relations (
                                   user_id INTEGER REFERENCES users(user_id),
                                   product_id INTEGER REFERENCES products(product_id),
                                   PRIMARY KEY (user_id, product_id)
);

CREATE TABLE groups (
                       group_id SERIAL PRIMARY KEY,
                       group_name VARCHAR(50) NOT NULL,
                       description VARCHAR(200) NOT NULL
);

CREATE TABLE group_relations (
                                   group_id INTEGER REFERENCES groups(group_id),
                                   user_id INTEGER REFERENCES users(user_id),
                                   role VARCHAR(50) NOT NULL,
                                   PRIMARY KEY (group_id, user_id)
);