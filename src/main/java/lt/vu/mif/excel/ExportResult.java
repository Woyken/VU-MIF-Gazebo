package lt.vu.mif.excel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportResult {

    private String message;
    private byte[] file;
}
