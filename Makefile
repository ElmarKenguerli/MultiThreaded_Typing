#Makefile for Assignment 2
#Elmar Kenguerli
# 07 September 2021
JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src/skeletonCodeAssgnmt2
BINDIR=build/classes/skeletonCodeAssgnmt2

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=Score.class WordDictionary.class WordRecord.class WordPanel.class WordApp.class TypingGame.class TypingGame$1$1.class TypingGame$1$2.class TypingGame$1$3.class TypingGame$1$4.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run: $(CLASS_FILES)
	java -cp $(BINDIR) TypingGame.class 10 4 example_dict.txt
