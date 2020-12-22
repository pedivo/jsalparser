package br.com.machado.pedro.parser;

import java.io.IOException;
import java.util.List;

import br.com.machado.pedro.enhancer.IEnhancerFilter;

public class ParserStoreFactory {

  public static IParserStore factory(String storeEngine, String output, List<IEnhancerFilter> enhancers) throws IOException {
    if ("json".equalsIgnoreCase(storeEngine)) {
      return new JsonParserStore(output, enhancers);
    }

    if ("excel".equalsIgnoreCase(storeEngine)) {
      return new ExcelParserStore(output, enhancers);
    }

    return null;
  }
}
