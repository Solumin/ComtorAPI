javac -cp .;../lib/antlr-3.3.jar;../lib/cantlr.jar *.java
jar cf ComtorDocs.jar *.class
del *.class
copy ComtorDocs.jar ..\lib\ComtorDocs.jar
move ComtorDocs.jar ../../ComtorEngine/lib/ComtorDocs.jar