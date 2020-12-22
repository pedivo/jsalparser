package br.com.machado.pedro.enhancer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hiramsoft.commons.jsalparser.S3LogEntry;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.GeoIp2Provider;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CountryResponse;

public class GeoIP2EnhancerFilter implements IEnhancerFilter {

  private GeoIp2Provider provider;

  public GeoIP2EnhancerFilter(String[] options) {
    if (options != null) {
      initializeOptions(options);

      if (getOptions().containsKey("geoip-database-path")) {
        loadDatabase(getOptions().get("geoip-database-path"));
      } else if (getOptions().containsKey("geoip-account-id") && getOptions().containsKey("geoip-licence-key")) {
        setupClient(getOptions().get("geoip-account-id"), getOptions().get("geoip-licence-key"));
      }
    }
  }

  @Override
  public void enhance(S3LogEntry s3LogEntry) {
    s3LogEntry.addMetadata(getCountryInformation(s3LogEntry.getRemoteIpAddress()));
  }

  @Override
  public List<EnhancerHeaderVo> getHeaders() {
    return Arrays.asList(
        new EnhancerHeaderVo("country", "Country")
    );
  }

  private Map<String, String> getCountryInformation(String ip) {
    if (provider == null) {
      return Collections.emptyMap();
    }

    try {
      CountryResponse response = provider.country(InetAddress.getByName(ip));

      Map<String, String> data = new HashMap<>();

      data.put("country", response.getCountry().getIsoCode());

      return data;

    } catch (Exception e) {
      System.out.println("Fail parsing ip " + ip);
    }

    return Collections.emptyMap();
  }

  private void setupClient(String accountId, String licenceKey) {
    provider = new WebServiceClient.Builder(Integer.valueOf(accountId), licenceKey).build();
  }

  private void loadDatabase(String path) {
    File database = new File(path);
    if (database.exists()) {
      try {
        provider = new DatabaseReader.Builder(database).build();
      } catch (IOException e) {
        System.out.println("Was not possible to load Maxmind database");
      }
    }
  }


}
