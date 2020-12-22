package br.com.machado.pedro.runner;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsUtils {

  public static Options RUNNER_OPTIONS;

  static {
    RUNNER_OPTIONS = new Options();
    RUNNER_OPTIONS.addOption(buildOptionHelp());
    RUNNER_OPTIONS.addOption(buildOptionInputFolder());
    RUNNER_OPTIONS.addOption(buildOptionOutputFolder());
    RUNNER_OPTIONS.addOption(buildOptionStoreengine());
    RUNNER_OPTIONS.addOption(buildOptionLocale());
    RUNNER_OPTIONS.addOption(buildOptionEnhancers());
    RUNNER_OPTIONS.addOption(buildOptionEnhancerOptions());
  }

  private static Option buildOptionHelp() {
    return Option.builder()
        .longOpt("help")
        .build();
  }

  private static Option buildOptionInputFolder() {
    return Option.builder()
        .argName("path")
        .longOpt("input")
        .hasArg()
        .desc("Path to folder with file all file logs or a specific file log")
        .build();
  }

  private static Option buildOptionOutputFolder() {
    return Option.builder()
        .argName("path")
        .hasArg()
        .longOpt("output")
        .desc("Folder path where output file must be saved")
        .build();
  }

  private static Option buildOptionStoreengine() {
    return Option.builder()
        .argName("path")
        .hasArg()
        .longOpt("store-engine")
        .desc("Define which store engine will be used. Allowed values: json,excel")
        .build();
  }

  private static Option buildOptionLocale() {
    return Option.builder()
        .argName("locale")
        .hasArg()
        .longOpt("locale")
        .desc("Define locale to parse date if it isn't in English")
        .build();
  }

  private static Option buildOptionEnhancers() {
    return Option.builder()
        .argName("enhancers")
        .hasArg()
        .longOpt("enhancers")
        .desc("Define which data enhancers do you want to use. Allowed values: geoip and useragent")
        .build();
  }

  private static Option buildOptionEnhancerOptions() {
    return Option.builder()
        .argName("options")
        .hasArg()
        .longOpt("enhancer-options")
        .desc("Options required to initialize some enhancers. Allowed values: geoip-account-id, geoip-licence-key, geoip-database-path")
        .build();
  }
}
