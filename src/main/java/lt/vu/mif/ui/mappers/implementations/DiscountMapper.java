package lt.vu.mif.ui.mappers.implementations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.product.Discount;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.DiscountView;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        discount.setFrom(view.getFrom());
        discount.setTo(view.getTo());

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
        view.setFrom(entity.getFrom());
        view.setTo(entity.getTo());

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
