.PHONY: all clean

all: pom.xml src/main/java/*.java
	mvn -q clean install
	make run
run:
	mvn -q exec:java
	
