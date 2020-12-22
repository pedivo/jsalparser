package br.com.machado.pedro.parser;

import java.io.IOException;
import java.util.List;

import br.com.machado.pedro.enhancer.EnhancerHeaderVo;
import br.com.machado.pedro.enhancer.IEnhancerFilter;
import com.hiramsoft.commons.jsalparser.S3LogEntry;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelParserStore extends FileSystemParserStore {

  private SXSSFWorkbook workbook;
  private SXSSFSheet dataSheet;
  private List<IEnhancerFilter> enhancers;
  private CellStyle dateStyle;
  private int line = 1;

  public ExcelParserStore(String output, List<IEnhancerFilter> enhancers) throws IOException {
    super(output);
    addEnhancers(enhancers);

    workbook = new SXSSFWorkbook();
    dataSheet = workbook.createSheet("Data");
    this.enhancers = enhancers;
    writeBaseHeasder();
    createDateStyle();
  }

  private void createDateStyle() {
    dateStyle = workbook.createCellStyle();
    dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy HH:mm:ss"));
  }

  private void writeBaseHeasder() {
    SXSSFRow header = dataSheet.createRow(0);
    header.createCell(0).setCellValue("bucketOwner");
    header.createCell(1).setCellValue("bucket");
    header.createCell(2).setCellValue("at");
    header.createCell(3).setCellValue("remoteIpAddress");
    header.createCell(4).setCellValue("requester");
    header.createCell(5).setCellValue("requestId");
    header.createCell(6).setCellValue("operation");
    header.createCell(7).setCellValue("key");
    header.createCell(8).setCellValue("requestUri");
    header.createCell(9).setCellValue("httpStatus");
    header.createCell(10).setCellValue("errorCode");
    header.createCell(11).setCellValue("referrer");
    header.createCell(12).setCellValue("userAgent");
    header.createCell(13).setCellValue("versionId");
    header.createCell(14).setCellValue("extras");

    int index = 15;

    for (IEnhancerFilter filter : enhancers) {
      for (EnhancerHeaderVo headerVo : filter.getHeaders()) {
        header.createCell(index).setCellValue(headerVo.getLabel());
        index++;
      }
    }
  }

  public void store(S3LogEntry entry) throws IOException {
    super.enhance(entry);

    SXSSFRow row = dataSheet.createRow(line);
    row.createCell(0).setCellValue(entry.getBucketOwner());
    row.createCell(1).setCellValue(entry.getBucket());
    row.createCell(2).setCellValue(entry.getTime());
    row.getCell(2).setCellStyle(dateStyle);
    row.createCell(3).setCellValue(entry.getRemoteIpAddress());
    row.createCell(4).setCellValue(entry.getRequester());
    row.createCell(5).setCellValue(entry.getRequestId());
    row.createCell(6).setCellValue(entry.getOperation());
    row.createCell(7).setCellValue(entry.getKey());
    row.createCell(8).setCellValue(entry.getRequestUri());
    row.createCell(9).setCellValue(entry.getHttpStatus());
    row.createCell(10).setCellValue(entry.getErrorCode());
    row.createCell(11).setCellValue(entry.getReferrer());
    row.createCell(12).setCellValue(entry.getUserAgent());
    row.createCell(13).setCellValue(entry.getVersionId());
    if (entry.getExtras() != null) {
      row.createCell(14).setCellValue(entry.getExtras().toString());
    }

    int index = 15;

    for (IEnhancerFilter filter : enhancers) {
      for (EnhancerHeaderVo headerVo : filter.getHeaders()) {
        row.createCell(index).setCellValue(entry.getMetadata().get(headerVo.getKey()));
        index++;
      }
    }
    line++;

  }

  public void close() throws IOException {
    workbook.write(getOutputStream());
    workbook.close();
  }
}
