package br.com.machado.pedro.enhancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hiramsoft.commons.jsalparser.S3LogEntry;

public interface IEnhancerFilter {

  Map<String, String> options = new HashMap<>();

  void enhance(S3LogEntry s3LogEntry);

  default void initializeOptions(String[] options) {
    this.options.putAll(
        Stream.of(options)
            .map(s -> s.split("="))
            .collect(Collectors.toMap(o -> o[0], o -> o[1]))
    );
  }

  default Map<String, String> getOptions() {
    return this.options;
  }

  /**
   * List of all header created by each enhancer
   * @return
   */
  List<EnhancerHeaderVo> getHeaders();
}
