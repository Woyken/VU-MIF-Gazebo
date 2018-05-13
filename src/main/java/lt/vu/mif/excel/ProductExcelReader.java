package lt.vu.mif.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ProductExcelReader {

    private static final Log LOG = LogFactory.getLog(ProductExcelReader.class);

    public CompletionStage<List<ExcelProduct>> readFile(InputStream inputStream) {
        return CompletableFuture.supplyAsync(() -> readFileSync(inputStream));
    }

    public List<ExcelProduct> readFileSync(InputStream inputStream) {
        List<ExcelProduct> result = new ArrayList<>();
        List<Object> rowValues = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0) {
                    continue; //ignore header row
                }
                if (currentRow.getCell(0).getStringCellValue().isEmpty()) {
                    // If SKU empty, stop reading new lines.
                    break;
                }

                currentRow.forEach(currentCell -> {
                    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
                        .createFormulaEvaluator();
                    CellValue cellValue = formulaEvaluator.evaluate(currentCell);
                    if (cellValue == null) {
                        return;
                    }

                    switch (cellValue.getCellTypeEnum()) {
                        case NUMERIC:
                            rowValues.add(currentCell.getNumericCellValue());
                            break;
                        case STRING:
                            rowValues.add(currentCell.getStringCellValue());
                            break;
                    }
                });

                ExcelProduct product = parseProduct(rowValues);
                result.add(product);
                rowValues.clear();
            }
        } catch (FileNotFoundException ex) {
            LOG.error("File not found", ex);
        } catch (IOException ex) {
            LOG.error("Unexpected error occurred", ex);
        }
        return result;
    }

    private ExcelProduct parseProduct(List<Object> rowValues) {
        ExcelProduct product = new ExcelProduct();
        product.setSkuCode((String) rowValues.get(0));
        product.setTitle((String) rowValues.get(1));
        product.setDescription((String) rowValues.get(2));
        product.setPrice(new BigDecimal((Double) rowValues.get(3), MathContext.DECIMAL64));
        product.setImageLink((String) rowValues.get(4));
        return product;
    }
}
