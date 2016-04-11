package org.avaje.ignite;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.swapspace.file.FileSwapSpaceSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Ignite configuration builder.
 */
public class IgniteConfigBuilder {

  private static final Logger logger = LoggerFactory.getLogger(IgniteServer.class);

  private final Properties properties;

  protected final IgniteConfiguration configuration = new IgniteConfiguration();

  public IgniteConfigBuilder(Properties properties) {
    this.properties = properties;
  }

  public IgniteConfiguration build() {

    Slf4jLogger gridLog = new Slf4jLogger(logger);

    configuration.setGridLogger(gridLog);
    configuration.setClientMode(getBool("ignite.clientmode", false));

    setFileSwap();
    setDiscovery();

    return configuration;
  }

  protected void setDiscovery() {

    String addresses = get("ignite.multicast.addresses", "");
    if (!addresses.trim().isEmpty()) {

      TcpDiscoveryMulticastIpFinder multiCast = new TcpDiscoveryMulticastIpFinder();
      multiCast.setAddresses(parseAddresses(addresses));
      TcpDiscoverySpi tcpDiscovery = new TcpDiscoverySpi();
      tcpDiscovery.setIpFinder(multiCast);

      configuration.setDiscoverySpi(tcpDiscovery);
    }
  }

  protected Collection<String> parseAddresses(String delimitedAddresses) {
    String[] split = delimitedAddresses.split("[,;]");

    Set<String> addresses = new LinkedHashSet<>();
    for (String address : split) {
      addresses.add(address.trim());
    }
    return addresses;
  }

  protected void setFileSwap() {
    if (getBool("ignite.fileswap", true)) {
      configuration.setSwapSpaceSpi(new FileSwapSpaceSpi());
    }
  }


  protected boolean getBool(String key, boolean defaultValue) {

    String val = get(key, Boolean.toString(defaultValue));
    return Boolean.valueOf(val.toLowerCase());
  }

  protected String get(String key, String defaultValue) {

    String value = System.getProperty(key);
    if (value != null) {
      return value;
    }

    value = properties.getProperty(key);
    return (value != null) ? value : defaultValue;
  }
}
