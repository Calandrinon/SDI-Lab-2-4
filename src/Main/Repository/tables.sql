CREATE TABLE IF NOT EXISTS Record
    (RecordId INT PRIMARY KEY,
	 AlbumName VARCHAR(64) NOT NULL,
     Price REAL NOT NULL,
     InStock INT NOT NULL,
     RecordType VARCHAR(10) NOT NULL);


CREATE TABLE IF NOT EXISTS ClientUser
    (UserId INT PRIMARY KEY,
	 FirstName VARCHAR (64) NOT NULL,
	 LastName VARCHAR (64) NOT NULL,
     NumberOfTransactions INT NOT NULL CHECK (NumberOfTransactions >= 0));


DROP TABLE UserTransaction;
CREATE TABLE IF NOT EXISTS UserTransaction
    (TransactionId SERIAL PRIMARY KEY,
     UserId INT REFERENCES ClientUser(UserId),
     RecordId INT REFERENCES Record(RecordId),
     TransactionDateTime DATE NOT NULL,
	 Quantity INT NOT NULL CHECK (Quantity > 0));


CREATE TABLE IF NOT EXISTS ClientUserTestTable
    (UserId INT PRIMARY KEY,
	 FirstName VARCHAR (64) NOT NULL,
	 LastName VARCHAR (64) NOT NULL,
     NumberOfTransactions INT NOT NULL CHECK (NumberOfTransactions >= 0));


CREATE TABLE IF NOT EXISTS RecordTestTable
    (RecordId INT PRIMARY KEY,
	 AlbumName VARCHAR(64) NOT NULL,
     Price REAL NOT NULL,
     InStock INT NOT NULL,
     RecordType VARCHAR(10) NOT NULL);


CREATE TABLE IF NOT EXISTS UserTransactionTestTable
    (TransactionId SERIAL PRIMARY KEY,
     UserId INT REFERENCES ClientUserTestTable(UserId),
     RecordId INT REFERENCES RecordTestTable(RecordId),
     TransactionDateTime DATE NOT NULL,
	 Quantity INT NOT NULL CHECK (Quantity > 0));


DROP TABLE ClientUser;
DROP TABLE Record;
DROP TABLE UserTransaction;
DROP TABLE ClientUserTestTable;
DROP TABLE RecordTestTable;
DROP TABLE UserTransactionTestTable;

SELECT * FROM ClientUser;
SELECT * FROM Record;
SELECT * FROM UserTransaction;
SELECT * FROM ClientUserTestTable;
SELECT * FROM RecordTestTable;
SELECT * FROM UserTransactionTestTable;

select count(*) from information_schema.columns where table_name='clientuser';
SELECT column_name FROM information_schema.columns WHERE table_name='clientuser';

SELECT
   data_type
FROM
   information_schema.columns
WHERE
   table_name = 'usertransactiontesttable';

/**
INSERT INTO ClientUserTestTable (UserId, FirstName, LastName, NumberOfTransactions) VALUES (15, 'userfirstname', 'userlastname', 22);
INSERT INTO RecordTestTable (RecordId, AlbumName, Price, InStock, RecordType) VALUES (14, 'abc', 99, 200, 'VINYL');
INSERT INTO UserTransactionTestTable (TransactionId, UserId, RecordId, TransactionDateTime, Quantity) VALUES (1, 15, 14, '2021-02-25', 2);
**/