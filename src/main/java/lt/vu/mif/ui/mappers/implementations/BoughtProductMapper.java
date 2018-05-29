package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.order.OrderProduct;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.BoughtProductView;
import lt.vu.mif.utils.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoughtProductMapper implements IMapper<OrderProduct, BoughtProductView> {

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public OrderProduct toEntity(BoughtProductView view) {
        throw new NotImplementedException();
    }

    @Override
    public List<OrderProduct> toEntities(List<BoughtProductView> views) {
        throw new NotImplementedException();
    }

    @Override
    public BoughtProductView toView(OrderProduct entity) {
        if (entity == null) {
            return null;
        }

        BoughtProductView view = new BoughtProductView();
        view.setId(entity.getProduct().getId());
        //TODO: fetch only one product from DB, not all
        view.setImage(imageMapper.toView(entity.getProduct().getImages().get(0)));
        view.setDate(entity.getOrder().getCreationDate()
                .format(DateTimeFormatter.ofPattern(ValidationUtils.DATETIME_FORMAT)));
        view.setQuantity(entity.getQuantity());
        view.setPrice(
                entity.getPrice().multiply(new BigDecimal(entity.getQuantity())));
        view.setTitle(entity.getProduct().getTitle());
        view.setProductId(entity.getProduct().getId());

        return view;
    }

    @Override
    public List<BoughtProductView> toViews(List<OrderProduct> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
