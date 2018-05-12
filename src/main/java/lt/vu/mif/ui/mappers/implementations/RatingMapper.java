package lt.vu.mif.ui.mappers.implementations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lt.vu.mif.model.order.Rating;
import lt.vu.mif.repository.repository.interfaces.IOrderRepository;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.RatingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RatingMapper implements IMapper<Rating, RatingView> {
    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public Rating toEntity(RatingView view) {
        Rating entity = new Rating();

        entity.setComment(view.getComment());
        entity.setDate(LocalDateTime.now());
        entity.setValue(view.getValue() == null ? 1 : view.getValue());
        entity.setOrder(orderRepository.get(view.getOrderId()));

        return entity;
    }

    @Override
    public List<Rating> toEntities(List<RatingView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public RatingView toView(Rating entity) {
        RatingView view = new RatingView();

        view.setComment(entity.getComment());
        view.setValue(entity.getValue());
        view.setOrderId(entity.getOrder().getId());

        return view;
    }

    @Override
    public List<RatingView> toViews(List<Rating> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}