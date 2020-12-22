package br.com.machado.pedro.runner;

import java.util.List;

import br.com.machado.pedro.enhancer.EnhancerFilterFactory;
import br.com.machado.pedro.enhancer.IEnhancerFilter;
import br.com.machado.pedro.parser.IParserStore;
import br.com.machado.pedro.parser.LogParser;
import br.com.machado.pedro.parser.ParserStoreFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

public class Executable {

  public static void main(String[] args) {
    // create the parser
    CommandLineParser parser = new DefaultParser();
    LogParser logParser;

    try {
      // parse the command line arguments
      CommandLine line = parser.parse(OptionsUtils.RUNNER_OPTIONS, args);

      if (line.hasOption("help")) {
        printHelp();
        return;
      }

      List<IEnhancerFilter> enhancers = EnhancerFilterFactory.factory(
          line.getOptionValues("enhancers"),
          line.getOptionValues("enhancer-options")
      );

      IParserStore store = ParserStoreFactory.factory(
          line.getOptionValue("store-engine"),
          line.getOptionValue("output"),
          enhancers
      );

      logParser = new LogParser(store, line.getOptionValue("input"));
      logParser.setLocale(line.getOptionValue("locale"));

      logParser.parse();

      store.close();

    } catch (Exception exp) {
      System.out.println(exp.getMessage());
      exp.printStackTrace();
      printHelp();
    }
  }

  private static void printHelp() {
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp("parser", OptionsUtils.RUNNER_OPTIONS);
  }
}
