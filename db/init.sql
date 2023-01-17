CREATE TABLE users
(
    id         uuid DEFAULT gen_random_uuid(),
    name       varchar(255) NOT NULL,
    age        int4         NOT NULL,
    salary     float8       NOT NULL,
    department varchar(255) NOT NULL
);

INSERT INTO users (name, age, salary, department)
VALUES
('my-name', 30, 45000, 'development'),
('her-name', 32, 80000, 'development');