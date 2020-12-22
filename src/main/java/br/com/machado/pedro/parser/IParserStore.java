package br.com.machado.pedro.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.machado.pedro.enhancer.IEnhancerFilter;
import com.hiramsoft.commons.jsalparser.S3LogEntry;

public interface IParserStore {

  List<IEnhancerFilter> enhancers = new ArrayList();

  void store(S3LogEntry entry) throws IOException;

  void close() throws IOException;

  default void addEnhancers(IEnhancerFilter filter) {
    enhancers.add(filter);
  }

  default void addEnhancers(List<IEnhancerFilter> filters) {
    enhancers.addAll(filters);
  }

  default void enhance(S3LogEntry entry) {
    if (!enhancers.isEmpty()) {
      enhancers
          .forEach(e -> e.enhance(entry));
    }
  }
}
