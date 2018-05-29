package lt.vu.mif.excel;

import lt.vu.mif.model.product.Category;
import lt.vu.mif.model.product.Image;
import lt.vu.mif.model.product.Product;
import lt.vu.mif.utils.constants.Constants;
import lt.vu.mif.utils.zip.ZipUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class ProductExcelWriter extends ProductExcelComponent {

    @Autowired
    private ZipUtils zipUtils;

    public CompletionStage<ExportResult> exportProducts(List<Product> products) {
        return CompletableFuture.supplyAsync(() -> export(products));
    }

    private ExportResult export(List<Product> products) {
        ExportResult result = new ExportResult();
        Workbook workbook = createWorkBook(products);
        result.setMessage(write(workbook));

        List<Image> images = new ArrayList<>();
        products.forEach(product -> images.addAll(product.getImages()));

        byte bytes[] = zipUtils.createZipForExport(images);
        result.setFile(bytes);

        return result;
    }

    public Workbook createWorkBook(List<Product> products) {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(Constants.SHEET_NAME);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(createHeaderFont(workbook));

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerCellStyle);
        }

        writeProducts(products, sheet);
        autoSizeColumns(sheet);

        return workbook;
    }

    private void writeProducts(List<Product> products, Sheet sheet) {
        int rowNo = 1;
        int mergeFrom = 0;

        for (Product product : products) {
            Row row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(product.getName());
            row.createCell(1).setCellValue(product.getTitle());
            row.createCell(2).setCellValue(product.getPrice().toString());
            row.createCell(3).setCellValue(getImagesText(product.getImages()));
            row.createCell(4).setCellValue(product.getSku());
            row.createCell(5).setCellValue(product.getDescription());
            row.createCell(6).setCellValue(getCategoryText(product.getCategory()));
            row.createCell(7).setCellValue("add attributes here");

            //Merging rows
            if (!product.getCategory().getAttributes().isEmpty()) {
                int mergeTo = mergeFrom + product.getCategory().getAttributes().size();
                for (int i = 0; i < 7; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeFrom, mergeTo, i, i));

                }
                mergeFrom = mergeTo + 1;
            }
        }

        //Merge header column
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
    }


    private synchronized String getImagesText(List<Image> images) {
        StringBuilder imagesText = new StringBuilder();

        for (Image image : images) {
            imagesText.append(Constants.IMAGES_FOLDER).append(Constants.IMAGE_PREFIX)
                    .append(image.getId()).append(Constants.IMAGE_TYPE).append("\n");
        }

        return removeLastCharacter(imagesText);
    }

    private String removeLastCharacter(StringBuilder builder) {
        return StringUtils.isNotBlank(builder.toString()) ? builder
                .substring(0, builder.length() - 1) : builder.toString();
    }

    private synchronized String getCategoryText(Category category) {
        StringBuilder categoryText = new StringBuilder();

        Category parent = category;
        while (parent.getParentCategory() != null) {
            categoryText = categoryText.append(parent.getName()).append("/");
            parent = parent.getParentCategory();
        }

        return removeLastCharacter(categoryText);
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }

    public String write(Workbook workbook) {
        try (FileOutputStream fileOut = new FileOutputStream(Constants.EXCEL_FILE_PATH)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Įvyko klaida bandant eksportuoti produktus";
        }
        return null;
    }

    private Font createHeaderFont(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        return headerFont;
    }
}
