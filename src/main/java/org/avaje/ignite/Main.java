package org.avaje.ignite;

import java.io.File;
import java.util.Properties;

/**
 * Run the Ignite server instance. Use CTRL-D to stop the instance.
 */
public class Main {

  /**
   * Run an Ignite instance loading configuration via properties file.
   */
  public static void main(String[] args) {

    checkLogback();

    Properties properties = PropertiesLoader.load();
    IgniteConfigBuilder configBuilder = new IgniteConfigBuilder("ignite", properties);

    IgniteServer server = new IgniteServer(true, configBuilder.build());
    server.run();
  }

  /**
   * Check that logback has a configuration to use.
   */
  private static void checkLogback() {

    String val = System.getProperty("logback.configurationFile");
    if (val == null) {
      // expect local logback.xml
      File localLogback = new File("logback.xml");
      if (localLogback.exists()) {
        System.setProperty("logback.configurationFile", "logback.xml");
      } else {
        System.setProperty("logback.configurationFile", "default-logback.xml");
      }
    }

  }
}
