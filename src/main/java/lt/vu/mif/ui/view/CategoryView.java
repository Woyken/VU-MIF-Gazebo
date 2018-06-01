package lt.vu.mif.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryView implements Comparable<CategoryView> {

    private Long id;
    private String name;
    private String nameWithParents;
    private CategoryView parentCategory;
    private DiscountView discount;
    private List<AttributeView> attributes = new ArrayList<>();

    public CategoryView() {
    }

    public CategoryView(CategoryView other) {
        this.id = other.id;
        this.name = other.name;
        this.attributes = other.attributes.stream().map(AttributeView::new).collect(Collectors.toList());
        this.parentCategory = other.getParentCategory() == null ? null :
            new CategoryView(other.parentCategory);
        this.discount = other.getDiscount() == null ? null :
            new DiscountView(other.getDiscount());
        this.nameWithParents = other.getNameWithParents();
    }

    @Override
    public int compareTo(CategoryView other) {
        return other == null ? 1 : this.nameWithParents == null ? -1 :
            this.nameWithParents.compareToIgnoreCase(other.nameWithParents);
    }

    @Override
    public String toString() {
        return nameWithParents;
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

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
