jESSEdit
========

A Java-based save-game API for The Elder Scrolls V: Skyrim.

I wrote this API because I was interested in cleaning my save games in Skyrim.  While I never actually got to the
part where I could clean my games with it, I did complete the API insofar as reading and writing Skyrim save game
files (ESS files).  As such, I leave it as an exercise for other developers to take this API and do something useful
with it.

Building the API
================

jESSEdit requires [Maven](http://maven.apache.org/) 3.0.x.  Build the project by executing `mvn package`.

Usage
=====

```java
import ca.davidfuchs.jessedit.ess.*;

...

InputStream inputStream = new FileInputStream("savegame.ess");
ESSFile essFile = ESSReader.readESSFile(inputStream);

// Do some work on the ESSFile object...

OutputStream outputStream = new FileOutputStream("savegame2.ess");
ESSWriter.writeESSFile(outputStream, essFile);

...
```

Command-Line Usage
==================

jESSEdit is primarily an API, but comes with a command-line application that prints the header section
of the ESS file, more or less to act as a proof of concept that it can read the file.

**Note:** You may need to adjust this command, as the JAR file may have a different version number.

```
java -jar jESSEdit-app-0.1.jar --file /path/to/savegame.ess
```
