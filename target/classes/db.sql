CREATE DATABASE IF NOT EXISTS myDB;

ALTER DATABASE myDB --изменяет атрибуты базы данных --
  DEFAULT CHARACTER SET utf8mb4  -- utf8/ кодировка, преобразующая номера ячеек таблицы Юникод в двоичн. код --
  DEFAULT COLLATE  utf8mb4_unicode_ci; -- utf8_general_ci / способ, с помощью которого следует упорядочивать и сравнивать данные в БД -- 
--с MySQL 5.5.3 utf8mb4, а не utf8 --

DROP TABLE mydb.elector;
DROP TABLE mydb.candidateDetails;
DROP TABLE mydb.candidate;

CREATE TABLE IF NOT EXISTS candidate (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    firstName VARCHAR (100) default NULL,
    lastName VARCHAR (100) default NULL,
    age INTEGER default NULL,
    voices BIGINT default NULL --INTEGER был!  --
);

CREATE TABLE IF NOT EXISTS candidateDetails
(
    --id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,--
    candidate_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE,
    UNIQUE (candidate_id),  --только для связи один к одному--
    education VARCHAR (100) default NULL,
    annualIncome INTEGER default NULL,
    property VARCHAR (100) default NULL,
    electionPromises VARCHAR (100) default NULL
);


CREATE TABLE IF NOT EXISTS elector (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    voted BIT DEFAULT false,
    candidate_id BIGINT UNSIGNED,   --UNSIGNED только положительные числа --
    FOREIGN KEY (candidate_id) REFERENCES candidate(id) ON DELETE CASCADE,
    firstName VARCHAR(100) default NULL,
    lastName VARCHAR(100) default NULL,
    age INTEGER default NULL,
    login VARCHAR (100) default NULL UNIQUE,
    password VARCHAR (100) default NULL,
    passportSeries VARCHAR (100) default NULL,
    passportNum VARCHAR (100) default NULL   
);


INSERT INTO `candidate` (firstName, lastName, age, voices)
VALUES
  ('Ivan', 'Ivanov', '56','0'),
  ('Sidor', 'Sidorov', '45','0' ),
  ('Kirill', 'Kirilov', '60', '0');


INSERT INTO `candidatedetails` (candidate_id, education, annualIncome, property, electionPromises)
VALUES 
  (1,'Department of Politics and International Studies of University of Cambridge', '100000', 
'A semi-detached house on the outskirts of London', 'Import substitution and economic growth'),
   (2, 'Department of Government of Harvard University', '80000', 
'An apartment in California', 'Environmental protection and development of science and IT technologies'),
   (3, 'The Department of Politics and International Relations of University of Oxford', '90000', 
'A townhouse in London', 'Independent foreign policy and economic reforms');

INSERT INTO `elector` (firstName, lastName,age,login, password,passportSeries,passportNum)
VALUES 
  ('Ivan', 'Ivanov', '28','Ivan123','0f2bb66c','BN','9876543'); -- 0f2bb66c = Ivan123