# Game of Life
Solution for the Game of Life Kata: http://codingdojo.org/cgi-bin/index.pl?KataGameOfLife

The following instructions assume that Java 8 and Maven 3.3+ is available from the command line.

##Running Test Cases
From the project root:

1. Run the "test" Maven goal: 
	```
	mvn test
	```

##Running the Program
From the project root:

1. Build the jar file by running the "package" Maven goal (note: jar will include dependencies):
	```
	mvn package
	```
2. To run the default grid:
	```
	java -jar ./target/gameoflife-1.0.jar
	```
3. To run a custom grid from a text file:
	```
	java -jar ./target/gameoflife-1.0.jar /path/to/text/file
	```
