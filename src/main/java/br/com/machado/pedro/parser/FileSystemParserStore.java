package br.com.machado.pedro.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class FileSystemParserStore implements IParserStore {

  private FileOutputStream outputStream;

  public FileSystemParserStore(String output) throws IOException, IllegalArgumentException {
    if (output == null) {
      throw new FileNotFoundException("Output path doesn't exist");
    }

    File file = new File(output);

    if (file.exists()) {
      throw new FileNotFoundException("Output file already exists");
    }

    if (file.isDirectory()) {
      throw new IllegalArgumentException("Output path is a folder");
    }

    file.createNewFile();

    this.outputStream = new FileOutputStream(file);
  }

  protected FileOutputStream getOutputStream() {
    return outputStream;
  }
}
