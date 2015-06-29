# Personal Calendar #

A Project for NCTU Java Programming

## Getting Started ##

Install the Java Runtime Environment,  
then execute the following command:

```
$ git clone https://github.com/yuwen41200/personal-calendar.git
$ cd build/libs/
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

![Main-window][prtsc-link-0]

![Sub-window1][prtsc-link-1]

![Sub-window2][prtsc-link-2]

![Sub-window3][prtsc-link-3]

## Troubleshooting ##

Copy all error messages,  
and [report the issue][issue-link] on GitHub.

## Documentation ##

Class Hierarchy:

![UML Diagram][diagram-link]

Document:  
[Read full documents.][document-link]

## License ##

[The MIT License][license-link]

[prtsc-link-0]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/docs/screenshot0.png "Main-window"
[prtsc-link-1]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/docs/screenshot1.png "Sub-window1"
[prtsc-link-2]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/docs/screenshot2.png "Sub-window2"
[prtsc-link-3]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/docs/screenshot3.png "Sub-window3"
[issue-link]: https://github.com/yuwen41200/personal-calendar/issues/new
[diagram-link]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/docs/diagram.png "UML Diagram"
[document-link]: https://cdn.rawgit.com/yuwen41200/personal-calendar/master/docs/index.html
[license-link]: https://raw.githubusercontent.com/yuwen41200/personal-calendar/master/LICENSE
