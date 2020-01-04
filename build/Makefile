run: 
	java -Dfile.encoding=UTF-8 -cp /home/q2uy/20192/GA/GAFrameWork/GAFrameWork/target/classes hust.mso.gaframework.Main 

compile:
	javac -Xlint:unchecked -d ../target/classes --source-path ../src/main/java ../src/main/java/hust/mso/gaframework/Main.java 

compile-test:
	javac -cp ../target/test-classes:junit-4.13.jar:hamcrest-core-1.3.jar -d ../target/test-classes --source-path ../src/test/java ../src/test/java/Main.java

test:
	java -cp ../target/test-classes:junit-4.13.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore ../src/test/java/Main
