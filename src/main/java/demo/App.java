package demo;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class App {

  public enum Status {
    TESTING, ONE, TWO;
  }

  public static void main(String[] args) throws Exception {
    File baseDir = new File(System.getProperty("java.io.tmpdir"), "tomcat.8080." + System.nanoTime());

    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.setBaseDir(baseDir.getAbsolutePath());
    tomcat.getConnector().setProperty("address", "0.0.0.0");
    Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
    tomcat.start();
    Runtime.getRuntime().addShutdownHook(new ResourceCleanup(tomcat, baseDir));
    System.out.println("Tomcat started: http://localhost:8080/hello.jsp");
    tomcat.getServer().await();
  }

  public static class ResourceCleanup extends Thread {
    private final Tomcat tomcat;
    private final File baseDir;

    public ResourceCleanup(Tomcat tomcat, File baseDir) {
      this.tomcat = tomcat;
      this.baseDir = baseDir;
    }

    @Override
    public void run() {
      try {
        tomcat.stop();
        tomcat.destroy();
      }
      catch (Exception ignored) {
      }

      deleteRecursively(baseDir);
    }

    static void deleteRecursively(File f) {
      if (f.isDirectory()) {
        File[] children = f.listFiles();
        if (children != null) {
          for (File c : children) deleteRecursively(c);
        }
      }
      f.delete();
    }
  }
}
