:

# java -classpath "dist/lib/*:lib/*" RecordMerger $*

cd src
javac -cp "../lib/*" *.java
java -classpath "dist/lib/*:../lib/*:." RecordMerger "../data"
