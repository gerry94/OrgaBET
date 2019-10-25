.PHONY: all clean

all: pom.xml src/sample/Main.java src/sample/DBManager.java src/sample/Controller.java
	mvn compile
	mvn clean install
	make run
run:
	mvn exec:java -e
	
