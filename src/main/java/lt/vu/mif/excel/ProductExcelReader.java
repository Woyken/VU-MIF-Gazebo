package lt.vu.mif.excel;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lt.vu.mif.model.product.Category;
import lt.vu.mif.repository.repository.interfaces.ICategoryRepository;
import lt.vu.mif.repository.repository.interfaces.IProductRepository;
import lt.vu.mif.utils.interfaces.IImageReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ProductExcelReader {

    private static final Log LOG = LogFactory.getLog(ProductExcelReader.class);
    private static List<String> headers = new ArrayList<>();

    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IImageReader imageReader;
    @Autowired
    private IProductRepository productRepository;

    public ProductExcelReader() {
        headers.add("Product Name");
        headers.add("Title");
        headers.add("Price");
        headers.add("Image");
        headers.add("SKU Code");
        headers.add("Description");
        headers.add("Category");
        headers.add("Properties");
    }

    public CompletionStage<ImportResult> readFile(InputStream inputStream) {
        return CompletableFuture.supplyAsync(() -> readFileSync(inputStream));
    }

    public ImportResult readFileSync(InputStream inputStream) {
        ImportResult importResult = new ImportResult();
        List<Object> rowValues = new ArrayList<>();

        String errorMessage;

        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            errorMessage = validateSheetStructure(sheet);
            if (errorMessage != null) {
                importResult.setMessage(errorMessage);
                return importResult;
            }

            List<CellRangeAddress> productsAddresses = getProductAddresses(sheet);
            CellRangeAddress newProductAddress = getAndRemove(productsAddresses, sheet);
            ParsedProductResult product;

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0) {
                    continue; //ignore header row
                }

                boolean newProduct;
                if (newProductAddress != null && currentRow.getRowNum() == newProductAddress
                    .getFirstRow()) {
                    newProduct = true;
                    newProductAddress = getAndRemove(productsAddresses, newProductAddress, sheet);
                } else {
                    newProduct = false;
                }

                currentRow.forEach(currentCell -> {
                    FormulaEvaluator formulaEvaluator = workbook.getCreationHelper()
                        .createFormulaEvaluator();
                    CellValue cellValue = formulaEvaluator.evaluate(currentCell);
                    if (cellValue == null) {
                        rowValues.add(null);
                    } else {
                        switch (cellValue.getCellTypeEnum()) {
                            case NUMERIC:
                                rowValues.add(currentCell.getNumericCellValue());
                                break;
                            case STRING:
                                rowValues.add(currentCell.getStringCellValue());
                                break;
                            case BLANK:
                                rowValues.add(null);
                        }
                    }
                });

                if (newProduct) {
                    product = parseProduct(rowValues, currentRow);
                    if (product.getMessage() != null) {
                        importResult.setMessage(product.getMessage());
                        return importResult;
                    }
                    importResult.getProducts().add(product.getExcelProduct());
                } else {
                    // Parse and set properties here
                }
                rowValues.clear();
            }
        } catch (FileNotFoundException ex) {
            LOG.error("File not found", ex);
        } catch (IOException ex) {
            LOG.error("Unexpected error occurred", ex);
        }

        return importResult;
    }

    private Property parseProperty(Row row, List<Object> rowValues) {
        String key = (String) rowValues.get(7);
        String value = (String) rowValues.get(8);

        return new Property(key, value);
    }

    private List<CellRangeAddress> getProductAddresses(Sheet sheet) {
        List<CellRangeAddress> list = sheet.getMergedRegions();
        list.removeIf(cell -> cell.getFirstColumn() != 0);

        list.sort((o1, o2) -> o1.getFirstRow() < o2.getFirstRow() ? 1 : -1);
        return list;
    }

    private CellRangeAddress getAndRemove(List<CellRangeAddress> addresses,
        CellRangeAddress oldProductAddress, Sheet sheet) {
        if (CollectionUtils.isEmpty(addresses)) {
            if (oldProductAddress.getLastRow() < sheet.getLastRowNum()) {
                return createOneRow(oldProductAddress);
            }
            return null;
        }

        CellRangeAddress result = addresses.get(addresses.size() - 1);

        if (oldProductAddress != null && oldProductAddress.getLastRow() + 1 != result
            .getFirstRow()) {
            return createOneRow(oldProductAddress);
        }

        addresses.remove(result);
        return result;
    }

    private CellRangeAddress createOneRow(CellRangeAddress address) {
        return new CellRangeAddress(address.getLastRow() + 1, address.getLastRow() + 1, 0, 0);
    }

    private CellRangeAddress getAndRemove(List<CellRangeAddress> addresses, Sheet sheet) {
        return getAndRemove(addresses, null, sheet);
    }

    private String validateSheetStructure(Sheet sheet) {
        Row row = sheet.getRow(0);

        for (Cell cell : row) {
            if (!headers.contains(cell.getStringCellValue()) && StringUtils
                .isNotBlank(cell.getStringCellValue())) {
                return "Neteisinga failo struktūra";
            }
        }
        return null;
    }

    private ParsedProductResult parseProduct(List<Object> rowValues, Row row) {
        ParsedProductResult result = new ParsedProductResult();
        ExcelProduct product = new ExcelProduct();

        int rowNo = row.getRowNum() + 1;

        String productName = (String) rowValues.get(0);
        if (StringUtils.isBlank(productName)) {
            result.setMessage("Produkto pavadinimas yra privalomas. Eilutė: " + rowNo);
            return result;
        }

        String title = (String) rowValues.get(1);
        if (StringUtils.isBlank(title)) {
            result.setMessage("Produkto pavadinimas yra privalomas. Eilutė: " + rowNo);
            return result;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(
                Double.valueOf((String.valueOf(rowValues.get(2))).replace(",", ".")),
                MathContext.DECIMAL64);
        } catch (NumberFormatException ex) {
            result.setMessage("Nurodyta netinkama kaina. Eilutė: " + rowNo);
            return result;
        }

        if (price.signum() < 0) {
            result.setMessage("Kaina negali būti neigiama. Eilutė: " + rowNo);
            return result;
        }

        String imageLink = ((String) rowValues.get(3));
        if (StringUtils.isBlank(imageLink)) {
            result.setMessage("Nuotraukos nuoroda negali būti tuščia. Eilutė: " + rowNo);
            return result;
        }

        String[] imageLinks = imageLink.split("[\\n\\r\\s]+");
        List<byte[]> imagesBytes = new ArrayList<>();

        for (String link : imageLinks) {
            try {
                byte[] bytes;

                if (link.startsWith("http")) {
                    bytes = imageReader.downloadImage(link);
                } else {
                   bytes = imageReader.readImageFromFile(link);
                }

                imagesBytes.add(bytes);

            } catch (IOException ex) {
                result.setMessage("Neteisinga nuotraukos nuoroda. Eilutė:" + rowNo);
                return result;
            }
        }

        String skuCode = (String) rowValues.get(4);
        if (StringUtils.isBlank(skuCode)) {
            result.setMessage("Nenurodytas SKU kodas. Eilutė: " + rowNo);
            return result;
        }

        if (productRepository.checkIfProductExists(skuCode)) {
            result.setMessage("Produktas su SKU kodu: " + skuCode  + " jau egzistuoja sistemoje. Eilutė " + rowNo);
            return result;
        }

        String description = (String) rowValues.get(5);
        if (StringUtils.isBlank(description)) {
            result.setMessage("Nenurodytas prekės aprašymas kodas. Eilutė: " + rowNo);
            return result;
        }

        String category = (String) rowValues.get(6);
        if (StringUtils.isBlank(category)) {
            result.setMessage("Nenurodyta kategorija. Eilutė: " + rowNo);
            return result;
        }

        String[] categories = category.split("\\/");
        Category latestCategory = categoryRepository
            .getCategoryByName(categories[categories.length - 1]);
        if (latestCategory == null) {
            result.setMessage("Nurodyta neegzistuojanti kategorija. Eilutė: " + rowNo);
            return result;
        } else {
            Category temp = latestCategory;
            for (int i = categories.length - 1; i >= 0; i--) {
                if (!Objects.equals(categories[i], temp.getName())) {
                    result.setMessage("Nurodyta neteisinga kategorija. Eilutė: " + rowNo);
                    break;
                }
                temp = temp.getParentCategory();
            }
        }

        product.setTitle(productName);
        product.setPrice(price);
        product.setImagesBytes(imagesBytes);
        product.setSkuCode(skuCode);
        product.setDescription(description);
        product.setCategory(latestCategory);

        result.setExcelProduct(product);
        return result;
    }
}
