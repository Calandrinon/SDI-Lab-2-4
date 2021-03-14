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


CREATE TABLE IF NOT EXISTS UserTransaction
    (TransactionId INT PRIMARY KEY,
     UserId INT REFERENCES ClientUser(UserId) ON DELETE CASCADE,
     RecordId INT REFERENCES Record(RecordId) ON DELETE CASCADE,
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
    (TransactionId INT PRIMARY KEY,
     UserId INT REFERENCES ClientUserTestTable(UserId) ON DELETE CASCADE,
     RecordId INT REFERENCES RecordTestTable(RecordId) ON DELETE CASCADE,
     TransactionDateTime DATE NOT NULL,
	 Quantity INT NOT NULL CHECK (Quantity > 0));


INSERT INTO ClientUserTestTable (UserId, FirstName, LastName, NumberOfTransactions) VALUES (15, 'userfirstname', 'userlastname', 22);
INSERT INTO RecordTestTable (RecordId, AlbumName, Price, InStock, RecordType) VALUES (14, 'abc', 99, 200, 'VINYL');
INSERT INTO UserTransactionTestTable (TransactionId, UserId, RecordId, TransactionDateTime, Quantity) VALUES (1, 15, 14, '2021-2-25', 2);
