package io.pivotal.gemfire.toolsmiths.hydradb.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Set;

public class UtilityHelper {

  static final String NEW_LINE = System.getProperty("line.separator");
  private static Logger log = Logger.getLogger(UtilityHelper.class);

  public static boolean http_get_url(String urlstring) {
    try {
      log.info("Getting url <" + urlstring + ">");
      URL client = new URL(urlstring);
      URLConnection yc = client.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(
          yc.getInputStream()));
      String inputLine;
      StringBuilder sb = new StringBuilder();
      while ((inputLine = in.readLine()) != null)
        sb.append(NEW_LINE).append(inputLine);
      in.close();
      log.debug("url output <" + sb + ">");
      return true;
    } catch (MalformedURLException e) {
      log.error("Error getting wewbpage ", e);
      return false;
    } catch (IOException e) {
      log.error("Error getting wewbpage ", e);
      return false;
    } finally {
    }
  }

  public static long parseTime(String time) {
    log.debug("Trying to convert " + time + " into long...");
    String split[] = time.split(":");
    String shour = split[0];
    String sminute = split[1];
    int hour = Integer.parseInt(shour);
    int minute = Integer.parseInt(sminute);
    return minute + hour * 60;
  }

  public static String getStringList(Set<String> hostList) {
    StringBuilder hosts = new StringBuilder();
    for (String host : hostList) {
      hosts.append(host).append(", ");
    }
    if(hosts.length() > 1) {
      hosts.deleteCharAt(hosts.length() - 2);
    }
    return hosts.toString();
  }

  public static String getSystemProperty(String propertyName) {
    String regrServer = System.getenv(propertyName);
    if (regrServer == null || "".equals(propertyName))
      regrServer = System.getProperty(propertyName);
    if (regrServer == null) {
      System.err.println("No property defined with name : " + propertyName);
    }
    return regrServer;
  }

  public  static <T> String join(Collection<T> values, char c) {
    StringBuilder sb = new StringBuilder();
    for(T t : values) {
      sb.append(t).append(c);
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  public static void main(String[] args) {

    for(int i = 10100; i < 10200; i++) {
      http_get_url("http://localhost:9090/hdb/run/" + i + "/confirmDelete");
    }
  }

  public static int getRegressionType(String regressionType) {
    if("Full".equals(regressionType)) {
      return 1;
    }
    if("Targeted".equals(regressionType)) {
      return 2;
    }
    return 0;
  }

  public static String getStackTrace(Throwable aThrowable) {
    StringWriter writer = new StringWriter();
    aThrowable.printStackTrace(new PrintWriter(writer, true));;
    return writer.toString();
  }
}
