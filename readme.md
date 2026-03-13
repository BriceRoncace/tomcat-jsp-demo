## Demo of JSP/JSTL classpath ordering issue
_See https://bz.apache.org/bugzilla/show_bug.cgi?id=69974_

The issue is classpath flattening in embedded containers.

When mixing container implementations and API jars on the same flat classpath (which embedded containers do), you can run into ordering related issues.  This is not considered a bug because the spec assumes container classloader separation, not a Maven classpath.

The fix? Explicit dependency ordering:

Ensure  

```
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-core</artifactId>
  <version>11.0.15</version>
</dependency>

<!--
  tomcat-embed-jasper  
  tomcat-embed-el
--> 
```

appears before JSTL

```
<dependency>
  <groupId>jakarta.servlet.jsp.jstl</groupId>
  <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
  <version>3.0.2</version>
</dependency>

<!--
  tomcat-embed-jasper
  jakarta.servlet.jsp.jstl-api
-->
```

This problem disappears in traditional WAR deployments where there are two classloaders eliminating order conflicts::

Container classloader  
├─ Tomcat EL implementation  
├─ Jasper  
└─ Servlet APIs  

Webapp classloader  
└─ JSTL API  

Embedded Tomcat (Spring Boot) flattens this into a single classpath:
 - jakarta.servlet.jsp.jstl-api  
 - jakarta.el-api  
 - tomcat-embed-el  
 - tomcat-embed-jasper   

Once everything shares the same classpath, implementation vs API jars can interfere depending on order.

Tomcat’s code assumes the classic servlet container hierarchy.

This classpath fragility is one of the reasons JSP/JSTL is fading in modern Spring Boot apps. Newer templating engines like [JTE](https://jte.gg/) and [Thymeleaf](https://www.thymeleaf.org/) avoid container-provided APIs, so dependency ordering issues disappear.