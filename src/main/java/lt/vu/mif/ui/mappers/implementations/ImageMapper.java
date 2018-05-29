package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.product.Image;
import lt.vu.mif.ui.view.ImageView;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("imageMapper")
class ImageMapper {

    public Image toEntity(ImageView view) {
        Image image = new Image();

        image.setId(view.getId());
        image.setContent(view.getBytes());

        return image;
    }

    public ImageView toView(Image entity) {
        ImageView view = new ImageView();

        view.setId(entity.getId());
        view.setBytes(entity.getContent());

        return view;
    }

    public List<Image> toEntities(List<ImageView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<ImageView> toViews(List<Image> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}
