SRC_TUTO=$(wildcard Tutorial/*.java)
SRC_W1=$(wildcard world_1/*.java)
SRC_W2=$(wildcard world_2/*.java)
SRC_W3=$(wildcard world_3/*.java)

CLASS_TUTO=$(wildcard Tutorial/*.class)
CLASS_W1 = $(wildcard world_1/*.class)
CLASS_W2 = $(wildcard world_2/*.class)
CLASS_W3 = $(wildcard world_3/*.class)

all: w3

w3: 
	javac $(SRC_W3)
	jar --create --file exec.jar --main-class=world_3.Main -C . world_3

w2: 
	javac $(SRC_W2)
	jar --create --file exec.jar --main-class=world_2.Main -C . world_2

w1: 
	javac $(SRC_W1)

tuto: 
	javac $(SRC_TUTO)


run_w2:
	java world_2.Main

run_w1:
	java world_1.Main

run_tuto:
	java Tutorial.Tutorial


clean_w3:
	rm -f $(CLASS_W3)
	rm -f exec.jar

clean_w2:
	rm -f $(CLASS_W2)
	rm -f exec.jar

clean_w1:
	rm -f $(CLASS_W1)

clean_tuto:
	rm -f $(CLASS_TUTO)


doc:
	./documentation.sh

doc_clean:
	rm -rf doc