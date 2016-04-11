package org.avaje.ignite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Load the properties from file system.
 */
public class PropertiesLoader {

  /**
   * Load the properties file.
   */
  public static Properties load() {

    String propsPath = System.getProperty("propsFile");
    if (propsPath == null) {
      propsPath = "ignite.properties";
    }

    Properties props = new Properties();

    File propsFile = new File(propsPath);
    if (!propsFile.exists()) {
      throw new RuntimeException("Could not find "+propsPath+" properties file");
    }

    try {
      FileInputStream is = new FileInputStream(propsFile);
      props.load(is);
      is.close();
      return props;

    } catch (IOException e) {
      throw new RuntimeException("Error loading properties file "+propsPath, e);
    }
  }
}
