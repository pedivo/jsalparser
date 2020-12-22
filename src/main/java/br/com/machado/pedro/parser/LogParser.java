package br.com.machado.pedro.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import com.hiramsoft.commons.jsalparser.JSalParser;
import com.hiramsoft.commons.jsalparser.S3LogEntry;

public class LogParser {

  private File[] input;
  private IParserStore store;
  private Locale locale = Locale.ENGLISH;

  public LogParser(IParserStore store, String input) throws FileNotFoundException {
    setInput(input);
    this.store = store;
  }


  private void setInput(String input) throws FileNotFoundException {
    if (input == null) {
      throw new FileNotFoundException("Input path doesn't exist");
    }

    File inputFile = new File(input);

    if (!inputFile.exists()) {
      throw new FileNotFoundException("Input path doesn't exist");
    }

    if (inputFile.isDirectory()) {
      this.input = inputFile.listFiles();
    } else {
      this.input = new File[]{inputFile};
    }
  }

  public void setLocale(String locale) {
    if (locale != null) {
      this.locale = Locale.forLanguageTag(locale);
    }
  }

  public void parse() throws FileNotFoundException {
    if (this.input == null) {
      throw new FileNotFoundException("Input path doesn't exist");
    }


    if (input.length == 0) {
      System.out.println("No files were found");
      return;
    }

    Stream.of(input)
        .forEach(this::parse);
  }

  private void parse(File file) {
    try {
      FileInputStream fis = new FileInputStream(file);

      store(JSalParser.parseS3Log(fis));

    } catch (FileNotFoundException e) {
      System.out.println("File " + file.getName() + " was not found");
    } catch (IOException e) {
      System.out.println("Was not possible to open file: " + file.getName());
    }
  }

  private void store(List<S3LogEntry> entryList) throws IOException {
    if (entryList != null && !entryList.isEmpty()) {
      for (S3LogEntry entry : entryList) {
        store.store(entry);
      }
    }
  }

}
