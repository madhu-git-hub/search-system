Search System
-------------

Overview

This project processes a text file and applies the following business rules:

1. Counts and returns the number of words that start with "M" or "m".
2. Returns all words longer than 5 characters.


1. Build & Run Instructions
---------------------------

Using Maven
-----------

1.Build the project:

cd D:\STS Workspace\search-system

mvn clean install

2.Run the application:

java -jar target\search-system-0.0.1-SNAPSHOT.jar "FilePath"

3. Run unit test

mvn test


Example:
-------
java -jar target\search-system-0.0.1-SNAPSHOT.jar "D:\\STS Workspace\\search-system\\src\\main\\resources\\sample.txt"

Checking Logs
-------------
Logs are written to:

logs/application.log

Configuration Properties
------------------------
Configurations for business rules are defined in:

resource/config.properties

2. Future Business Rules
------------------------

To accommodate future business rule changes, an example rule could be added based on the property:

rule.word.endsWith

This might include, for example, counting or filtering words that end with a specific character or substring.

