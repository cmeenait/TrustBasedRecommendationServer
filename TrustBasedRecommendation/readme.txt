To Open the Project in eclipse  EE
1.$ cd TrustBasedRecommendation
2. mvn eclipse:eclipse -Dwtpversion=2.0
3. Open Eclipse, File--->Import-->General---->Existing Project into Workspace



To Run the Project
1.Before running the project make sure you do the following
    Right Click on Project--->Properties-->DeploymentAssembly--->Add--->Java BuildPath Entries-->Select all the build path entries
2. Right click on project and click on run on server. Select Tomcat from the list of servers to deploy the project
3.Type the following in the browser in eclipse to make sure the project deploys without any errors
   http://localhost:8080/TrustBasedRecommendation/restservice/test
   
 
 