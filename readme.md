# Test Application for Analyzing Web Pages (Sites). (ImmobilienScout24 dev-test)

This application does web page fetching and return back analysis results (as it was requested in requirements).


Features:
---------
- By requirements, application should be packed in war archieve and be able to run on tomcat application server.
  This application use spring boot as core framework and allow to package application (for easier usage and maintenance) into Jar-file with embedded Tomcat inside.
  In case of making it in war, the next steps should be done:    
    
        1) Update in pom.xml:
        <packaging>war</packaging>
         
        2) Put this as depency, in order to avoid embedded tomcat inferfere with standalone one.
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>

- Lombok library was used in order to simplify development process and make code more clean and readable 
- Application was designer in easy expandable way. In order to provide more analitycs, developer just need to:
  
        1) Inherit new class from PageAnalyzer interface
        2) Decorate class @Component annotation from spring web frawework package.
        3) cover this class with unit tests. (personally I would start with this one)
  
- Java 8 functionality is used here  

Assumptions:
-------
Since requirements is not full or sometimes contains ambiguous definitions, some assumptions were done:
- Links (internal, external):
  
        Link are internal if it fulfill one of the following rules: 
        1) has the same origin as requested page (after redirects)
            ex. 
                https://www.domain.de/path1 (? or # allowed) is internal for https://www.domain.de
        2) is a subdomain for requested origin url
           ex. 
                https://news.domain.de is internal for https://domain.de
           but this not
                https://news.domain.de is not internal for https://www.domain.de
        3) Schema independent
            ex. 
                http://www.domain.de is internal for https://www.domain.de
- Inaccessible links:
    
        Inaccessible links are links which respond by fetching them with response code different from 2xx or 3xx.
        If link is responding with error, - application will try to re-fetch it up to 10 times, to be sure that link is really inaccessible 
        
- Document version

        Jsoup official documentatio says to take it from Node with class DocumentType. which in a lot of cases return not really version but DOCTYPE tag
        which is in majority tested cases equal to: <!DOCTYPE html>
        
- Document title

        By Spec. document <title> tag should be  part of <head> tag. Nevertheless if I put <title> tag even outside <html> Jsoup library recornized it.
        Not sure that its proper library behavior. 
            
- LoginForm
        
        Login Form should fulfill next 3 rules:
        1) Form should contains at least 1 non-password field
        2) Form should contains at least 1 password field
        3) Form should contains at least 1 Button with type=submit
        

Usage:
-----

To build application, execute next command in project root folder:
        
        mvn clean install 
        
        Under /target folder devtest.jar artifact will be generated. 
 
To run application, execute next command in project root folder:

        java -jar target/devtest.jar


Application is available via browser under the following url:

        http://localhost:8080




<b> Hope you'll enjoy this tool </b>



