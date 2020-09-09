# HumanResourcesSystem-

# Introduce
This project is a human resource management system based on JavaWeb development technology. MVC design pattern is adopted for design and development. HTML+javascript is adopted for front-end technology.The back-end uses servlet technology to build the communication interface between the front-end and the back-end, and uses JSON technology to define the data format. The back-end database is built by mysql, and the trigger technology is used to limit the illegal operation of the database.

# environment:
windows 10;
Eclipse for Java 2018(plugin +Enterprise java Developer Tools 3.11);
firefox;
tomcat9.x;
Jdk1.8;
# plugin installation:
Help ->eclipse Marketplace->Install the Eclipse Enterprise Java Developer Tools;
# configuration items:
Window -> Preferences -> java -> Installed JRE -> Add -> Select the JDK installation directory;
Preferences -> Server-> Runtime Enviroments -> Add -> Select Apache Tomcat V9.0 -> in the Apache directory;Next, configure the tomcat installation directory and configure the JRE option as JDK.
# create project:
Window -> Perspective -> Open perspective -> Other -> Java EE;
New project -> Dynamic Web Project;
Project configuration Dynamic Web Moddle Version selects 4.0 -> Complete creation;
Project attribute -> Project Facets -> View project configuration;
# set up service:
Double-click the link under the Servers TAB to establish the service default configuration (do not publish the project while establishing the service);
Double-click the service has been established under the Servers TAB to enter configuration, configuration under Server Locations bar service path (path) Server, the custom path for D: \ something \ ComprehensiveProjectTraining \ projectapps;Deploy Path is webApps (under server Path);

