package br.com.machado.pedro.parser;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.machado.pedro.enhancer.IEnhancerFilter;
import com.hiramsoft.commons.jsalparser.S3LogEntry;

public class JsonParserStore extends FileSystemParserStore {
  private Gson gson;

  public JsonParserStore(String output, List<IEnhancerFilter> enhancers) throws IOException {
    super(output);
    addEnhancers(enhancers);
    gson = new Gson();

    this.getOutputStream().write("[".getBytes());
  }

  public void store(S3LogEntry entry) throws IOException {
    super.enhance(entry);

    getOutputStream().write(gson.toJson(entry).getBytes());
  }

  public void close() throws IOException {
    this.getOutputStream().write("]".getBytes());
    this.getOutputStream().close();
  }
}
