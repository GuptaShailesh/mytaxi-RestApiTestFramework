# mytaxi-RestApiTestFramework

Project Title: 
JsonPlaceHolderAPi Test
This Project is build to Automate the JsonPlaceHolder Rest Api tests 
we can fecth the various infomration from JsonPlaceHolder Apis like users , their details, post written by each users and cooments details on each posts using this Framework 
and we can comare data against our baselines and can mark our testcases pass/failed

This Project cotians below main to java files
JsonPlaceHolderApi.java:- all the apis call and realted methods are wriiten in this file 
JsonPlaceHolderApiTess.java :- All the test methods to test response and Assertions are written in this file 

Config Files:
1.log4j2.xml : -To configuring the logging
2.config.yml:-under .circleci/ circle ci related configuration to run the build in Ci/CD Mode


Tools/Software Used 
Maven:-For Build and dependencies download
Testng:- For Assertions , running testcases and reporting  
RestAssured:- To get the details from RestApi
Java:Coding Language 
Log4j:- For Logging 
CircleCi:Ci/Cd
GitHub:For publishing code and version control

Prerequesite:
For running the tests one should have maven and java installed
rest all the dependencies will be downloaded by maven . 


How to run tests:
1.Clone the Git repository https://github.com/GuptaShailesh/mytaxi-RestApiTestFramework.git to testenvironemnt
2.Make sure Java and Maven installed on system
3.Import the project to java supported IDE tool
4.Installed the Maven plugin in IdE tool
5.Run the pom.xml file using maven test command in IDE or go to the directory where pom.xml is reside and run mvn test commnad from command line 
6.Check the test results on console or in reports created under target/surefire-reports folder 
7.Check the out-put logs created under target/surefire-reports/log folder 

Enhancements :

1. Add more testcases to validate the RestAPi responses on various parameters
2. Add the TestNg Listeners for reporting 
3. Add the baseslines either in-form of DataProvider methods or as some files to store and compare results rather holding results in some data structure 
4. Add Pojos e.g like test clases for Json Responses 





 






