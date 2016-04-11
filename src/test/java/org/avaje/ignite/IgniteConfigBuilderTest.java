package org.avaje.ignite;

import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class IgniteConfigBuilderTest {

  private Properties props = new Properties();
  private IgniteConfigBuilder builder;

  IgniteConfigBuilderTest() {

    builder = new IgniteConfigBuilder(props);
  }

  @Test
  public void setDiscovery() throws Exception {

    props.setProperty("ignite.multicast.addresses","127.0.0.1,127.0.0.2;127.0.0.3");
    builder.setDiscovery();
    assertNotNull(builder.configuration.getDiscoverySpi());
  }

  @Test
  public void parseAddresses() throws Exception {

    Collection<String> addresses = builder.parseAddresses("127.0.0.1, 127.0.0.2;127.0.0.3");
    assertTrue(addresses.contains("127.0.0.1"));
    assertTrue(addresses.contains("127.0.0.2"));
    assertTrue(addresses.contains("127.0.0.3"));
  }

  @Test
  public void setFileSwap_defaultsTrue() throws Exception {

    builder.configuration.setSwapSpaceSpi(null);

    builder.setFileSwap();
    assertNotNull(builder.configuration.getSwapSpaceSpi());
  }

  @Test
  public void testGetBool() throws Exception {

    props.setProperty("ignite.something", "TRUE");
    assertTrue(builder.getBool("ignite.something.else", true));
    assertFalse(builder.getBool("ignite.something.else", false));
    assertTrue(builder.getBool("ignite.something", false));

    props.setProperty("ignite.something", "false");
    assertFalse(builder.getBool("ignite.something", false));

    props.clear();
  }

  @Test
  public void testGet() throws Exception {

    props.setProperty("ignite.something", "Foo");
    assertEquals(builder.get("ignite.something", "junk"), "Foo");
    assertEquals(builder.get("ignite.something.notExisting", "junk"), "junk");

    props.clear();
  }

}