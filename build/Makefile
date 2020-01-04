run: 
	java -Dfile.encoding=UTF-8 -cp ../target/classes hust.mso.mfea.Main 

compile:
	javac -Xlint:unchecked -d ../target/classes --source-path ../src/main/java ../src/main/java/hust/mso/mfea/Main.java 

compile-test:
	javac -cp ../target/test-classes:junit-4.13.jar:hamcrest-core-1.3.jar -d ../target/test-classes --source-path ../src/test/java ../src/test/java/Main.java

test:
	java -cp ../target/test-classes:junit-4.13.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ../src/test/java/Main
