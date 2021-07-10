# dependency-demo
The homework of management system

# how to use
Firstly run the crebas.sql and you will get a mysql databse called "pidb". 

Then, change the connection detail in the file src/main/java/quesmanagement/utils/DBUtil.java ,line 18 & 19
Filnally, compile all the java class and deploy all the classes into src/main/webapp/WEB-INF/. 
( This can be done by intellj idea, only need to open the whole 
repository as a project and build. Notice that the application context is "/")

Copy all the stuff in the folder "webapps" (exclude the webapps floder itself) to "you tomcat root folder"/webapps/ROOT . the website can only run in ROOT folder.
