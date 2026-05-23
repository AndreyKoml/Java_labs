all: build run

.PHONY: build
build:
	javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls *.java gui/*.java service/*.java repository/*.java model/*.java alfragment/*.java util/*.java gui/*.java service/*.java repository/*.java model/*.java alfragment/*.java util/*.java

.PHONY: run
run:
	java --module-path /usr/share/openjfx/lib --add-modules javafx.controls MainApp