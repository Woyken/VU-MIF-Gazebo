package lt.vu.mif.ui.view;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryView {

    private Long id;
    private String name;
    private CategoryView parenCategory;
    private List<String> attributes;
}
