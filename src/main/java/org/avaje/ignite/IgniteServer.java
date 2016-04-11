package org.avaje.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Ignite server.
 */
public class IgniteServer {

  private static final Logger logger = LoggerFactory.getLogger(IgniteServer.class);

  private final boolean useStdInShutdown;

  private final Ignite ignite;

  public IgniteServer(boolean useStdInShutdown, IgniteConfiguration configuration) {
    this.useStdInShutdown = useStdInShutdown;
    this.ignite = Ignition.start(configuration);
  }

  /**
   * Return the underlying ignite instance.
   */
  public Ignite getIgnite() {
    return ignite;
  }

  /**
   * Close the server explicitly.
   */
  public void close() {
    ignite.close();
  }

  /**
   * Run until CTRL-D shutdown occurs.
   */
  public void run() {

    try {
      if (useStdInShutdown) {
        logger.info("started server, use CTRL-D to stop");
        // generally for use in IDE via JettyRun, Use CTRL-D in IDE console to shutdown
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        while ((systemIn.readLine()) != null) {
          // ignore anything except CTRL-D by itself
        }
        System.out.println("Shutdown via CTRL-D");
        System.exit(0);

      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(100);
    }
  }
}
