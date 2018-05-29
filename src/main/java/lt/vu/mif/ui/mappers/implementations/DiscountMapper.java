package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.product.Discount;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.DiscountView;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountMapper implements IMapper<Discount, DiscountView> {

    @Override
    public Discount toEntity(DiscountView view) {
        if (view == null) {
            return null;
        }

        Discount discount = new Discount();

        discount.setId(view.getId());
        discount.setPercentageDiscount(view.getPercentageDiscount());
        discount.setAbsoluteDiscount(view.getAbsoluteDiscount());
        discount.setFrom(LocalDateTime.parse(view.getStartDate() + " " + view.getStartTime(),
                DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT)));
        discount.setTo(LocalDateTime.parse(view.getEndDate() + " " + view.getEndTime(),
                DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT)));

        return discount;
    }

    @Override
    public DiscountView toView(Discount entity) {
        if (entity == null) {
            return null;
        }

        DiscountView view = new DiscountView();

        view.setId(entity.getId());
        view.setPercentageDiscount(entity.getPercentageDiscount());
        view.setAbsoluteDiscount(entity.getAbsoluteDiscount());
        view.setStartDate(entity.getFrom().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        view.setEndDate(entity.getTo().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        view.setStartTime(entity.getFrom().format(DateTimeFormatter.ofPattern("HH:mm")));
        view.setEndTime(entity.getTo().format(DateTimeFormatter.ofPattern("HH:mm")));

        return view;
    }

    @Override
    public List<Discount> toEntities(List<DiscountView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<DiscountView> toViews(List<Discount> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }

}
