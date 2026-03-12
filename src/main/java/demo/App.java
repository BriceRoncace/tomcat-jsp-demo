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
    tomcat.setBaseDir(createTempDir());
    Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
    tomcat.start();
    System.out.println("Tomcat started: http://localhost:8080/hello.jsp");
    tomcat.getServer().await();
  }

  private static String createTempDir() {
    try {
      File tempDir = File.createTempFile("tomcat.", ".tmp");
      if (!tempDir.delete()) {
        throw new RuntimeException("Could not delete temp file: " + tempDir.getAbsolutePath());
      }
      if (!tempDir.mkdir()) {
        throw new RuntimeException("Could not create temp directory: " + tempDir.getAbsolutePath());
      }
      tempDir.deleteOnExit();
      return tempDir.getAbsolutePath();
    }
    catch (Exception ex) {
      throw new RuntimeException("Failed to create temp dir for Tomcat", ex);
    }
  }
}
