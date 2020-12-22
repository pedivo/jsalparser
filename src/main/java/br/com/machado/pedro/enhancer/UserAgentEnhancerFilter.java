package br.com.machado.pedro.enhancer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hiramsoft.commons.jsalparser.S3LogEntry;
import is.tagomor.woothee.Classifier;

public class UserAgentEnhancerFilter implements IEnhancerFilter {

  @Override
  public void enhance(S3LogEntry s3LogEntry) {
    s3LogEntry.addMetadata(getUserAgentInformation(s3LogEntry.getUserAgent()));
  }

  @Override
  public List<EnhancerHeaderVo> getHeaders() {
    return Arrays.asList(
        new EnhancerHeaderVo("uaname", "Browser"),
        new EnhancerHeaderVo("uacategory", "Browser Category"),
        new EnhancerHeaderVo("uaos", "Browser OS"),
        new EnhancerHeaderVo("uaversion", "Browser Version"),
        new EnhancerHeaderVo("uavendor", "Browser Vendor")
    );
  }

  private Map<String, String> getUserAgentInformation(String useragent) {
    return Classifier.parse(useragent)
        .entrySet().stream()
        .collect(Collectors.toMap(e -> "ua" + e.getKey(), Map.Entry::getValue));

  }


}
