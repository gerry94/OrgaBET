.PHONY: all clean

all: pom.xml src/GestioneBiblioteca.java src/DBManager.java
	mvn compile
	mvn clean install
	make run
run:
	mvn exec:java
	
