package lt.vu.mif.ui.view;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryView implements Comparable<CategoryView> {

    private Long id;
    private String name;
    private CategoryView parentCategory;
    private List<String> attributes;

    @Override
    public int compareTo(CategoryView other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!CategoryView.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final CategoryView other = (CategoryView) obj;

        return this.name == other.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
