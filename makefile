JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Asst2.java \
	MakeInfile.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class