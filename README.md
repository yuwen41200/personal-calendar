# Personal Calendar #

A Project for NCTU Java Programming

## Getting Started ##

Install the MySQL driver and add it to your `CLASSPATH`,  
then execute the following command:

```
$ git clone https://github.com/yuwen41200/personal-calendar.git
$ cd build/libs
$ java -jar personal-calendar-0.99.jar
```

Or use Gradle to build,  
as an alternative:

```
$ git clone https://github.com/yuwen41200/personal-calendar.git
$ ./gradlew build
$ ./gradlew run
```

Please note that for security reasons,  
the following files are not provided directly:

```
src/main/resources/client_secret.json
src/main/resources/passwd
build/resources/main/client_secret.json
build/resources/main/passwd
```

## Features ##

+ A cross-platform application released under the MIT License.
+ Built with Gradle 2.3 or greater.
+ Use Model-View-Controller architectural pattern.
+ Support multiple users.
+ All data are stored in a remote relational database with each independent table for each user.
+ Synchronize Google Calendar accounts via Google Calendar API.
+ Use HTML to style Swing components.
+ Sensitive data are stored in stand-alone files.

## Screenshots ##

To be released.

## Troubleshooting ##

Copy all error messages,  
and [report the issues][issue-link] on GitHub.

## Documentation ##

Class Hierarchy:

![UML Diagram][diagram-link]

To be released.

## License ##

[The MIT License][license-link]

[issue-link]: https://github.com/yuwen41200/personal-calendar/issues/new
[diagram-link]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/Diagram.png "UML Diagram"
[license-link]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/LICENSE
