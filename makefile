SRC_W1=$(wildcard world_1/*.java)
SRC_W2=$(wildcard world_2/*.java)
SRC_TUTO=$(wildcard Tutorial/*.java)

CLASS_W1 = $(wildcard world_1/*.class)
CLASS_W2 = $(wildcard world_2/*.class)
CLASS_TUTO=$(wildcard Tutorial/*.class)

all: w2

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