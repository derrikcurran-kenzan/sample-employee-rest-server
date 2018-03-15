CREATE TABLE employees (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  middle_initial CHAR(1),
  last_name VARCHAR(50),
  date_of_birth DATE,
  date_of_employment DATE,
  status VARCHAR(255) DEFAULT 'ACTIVE',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  UNIQUE (username),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;