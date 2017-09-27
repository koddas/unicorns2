# About this project

This project implements version 2 of a silly unicorn database using the Spark
framework. As such, Java 8 or later is required.

The project also showcases how a Java application can access a SQLite database.
There is ABSOLUTELY NO WAY you should run this in a real project, as it is
really, really insecure. Just don't do it. Learn from it, but that's all.

# How do I build this project?

You can easily build the code directly from within your IDE of choice (I'm
personally rather fond of [Eclipse](http://www.eclipse.org)), using its build
features (in Eclipse, right click the project and select *Run As* ->
*Maven build*, type *package* into the *Goals* field, then *Run*). If you
prefer doing stuff more old school, you can use the command line to build the
project as well. To do so, you need to have Maven installed on your machine.
Navigate to your project location. Then, simply type

    mvn package

in your terminal. Your generated JAR files can be found as
*./target/server-java-2.0.0.jar* and
*./target/server-java-2.0.0-with-dependencies.jar*.

# How do I run this project?

The easiest way of running the code is directly from within your IDE of choice,
simply by hitting the *Run* button (in Eclipse, that is). If you prefer running
your code from the command line, navigate to the project location and type

    java -jar target/server-java-2.0.0-with-dependencies.jar

Don't forget to kill the process when done (using the stop button in the
Eclipse console, or by hitting ctrl-c in the terminal).
