Pre-requisities:

Java 1.8
maven plugin for intellij


mkdir <yourdirectoryname>

git clone https://github.com/iflanagan/gettingStartedSLJava.git

Open intellij

click on import project

browse to the directory cloned location

click open

click on import project from external model

select maven 

click finish

when you see the pop-up in the bottom right 'maven projects need to be imported' click on 
'Enable Auto-import'

in the top left click on the project link so you can see the project structure

click and open the WebTest.java file 

there should be no errors 

at the top click on build project

Note there should be no errors.

Steps to run EmulatorExampleNative.java file

-Download the Calculator_2.0.apk app

-change the following line in the Constants class 

public static String myFile = "/Users/ianflanagan/Downloads/Calculator_2.0.apk";

to point to where you downloaded the file.

-set your sauceusername and sauceaccesskey in the Constants.java file

-right click in intellig and run the program

MORE information on how to run tests on emulators/simulators on the sauce platform can be found here

https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/





