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


CREATE TABLE IF NOT EXISTS Users_Records
    (UserId INT REFERENCES ClientUser(UserId),
     RecordId INT REFERENCES Record(RecordId),
     PRIMARY KEY (UserId, RecordId));


CREATE TABLE IF NOT EXISTS UserTransaction
    (TransactionId INT PRIMARY KEY,
     UserId INT REFERENCES ClientUser(UserId) ON DELETE CASCADE,
     RecordId INT REFERENCES Record(RecordId) ON DELETE CASCADE,
     TransactionDateTime TIMESTAMP NOT NULL,
	 Quantity INT NOT NULL CHECK (Quantity > 0),
     CONSTRAINT FK_UserId_RecordId FOREIGN KEY (UserId, RecordId) REFERENCES Users_Records(UserId, RecordId));

