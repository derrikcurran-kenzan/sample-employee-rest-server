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