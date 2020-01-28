.PHONY: all clean

all: pom.xml src/BookRater.java src/DBManager.java
	mvn -q clean install
	make run
run:
	mvn -q exec:java
	
