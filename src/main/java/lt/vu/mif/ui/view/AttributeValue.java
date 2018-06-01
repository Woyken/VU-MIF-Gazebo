package lt.vu.mif.ui.view;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValue {
    private Long id;
    private String value;
    //Used in filtering
    private Boolean isSelected = false;

    public AttributeValue() {
    }

    AttributeValue(AttributeValue other) {
        id = other.id;
        value = other.value;
        isSelected = other.isSelected;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeValue)) {
            return false;
        }
        AttributeValue that = (AttributeValue) o;
        return Objects.equals(getId(), that.getId()) &&
            Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getValue());
    }
}
