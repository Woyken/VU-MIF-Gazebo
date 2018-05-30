package lt.vu.mif.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeView {
    private Long id;
    private String name;
    private List<AttributeValue> values = new ArrayList<>();
    private AttributeValue selectedValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeView)) {
            return false;
        }
        AttributeView that = (AttributeView) o;
        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }
}
