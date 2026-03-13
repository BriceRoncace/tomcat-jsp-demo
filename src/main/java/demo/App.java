package demo;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class App {

  public enum Status {
    TESTING, ONE, TWO;
  }

  public static void main(String[] args) throws Exception {
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.getConnector().setProperty("address", "0.0.0.0");
    Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
    tomcat.start();
    System.out.println("Tomcat started: http://localhost:8080/hello.jsp");
    tomcat.getServer().await();
  }
}
