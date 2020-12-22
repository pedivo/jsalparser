package br.com.machado.pedro.enhancer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnhancerFilterFactory {

  public static IEnhancerFilter factory(String enhancer, String[] options) {
    if ("geoip".equalsIgnoreCase(enhancer)) {
      return new GeoIP2EnhancerFilter(options);
    }

    if ("useragent".equalsIgnoreCase(enhancer)) {
      return new UserAgentEnhancerFilter();
    }

    return null;
  }

  public static List<IEnhancerFilter> factory(String[] enhancers, String[] options) {
    if (enhancers == null || enhancers.length == 0) {
      return Collections.emptyList();
    }

    return Stream.of(enhancers)
        .map(enchancer -> factory(enchancer, options))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
