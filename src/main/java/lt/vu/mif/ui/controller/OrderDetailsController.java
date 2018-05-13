package lt.vu.mif.ui.controller;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mif.ui.helpers.interfaces.IOrdersHelper;
import lt.vu.mif.ui.view.OrderView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Getter
@Setter
@Named
@ViewScoped
public class OrderDetailsController {
    @Autowired
    private IOrdersHelper ordersHelper;

    private OrderView orderView;

    public void onPageLoad() {
        if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
            return;
        }

        String orderId = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("orderId");
        if (StringUtils.isBlank(orderId)) {
            throw new IllegalArgumentException("Invalid request parameter");
        }

        orderView = ordersHelper.getOrder(Long.valueOf(orderId));

        if (orderView == null) {
            throw new IllegalStateException("Order" + "with ID=" + orderId + "not found");
        }
    }

    public void saveChanges() {
//        Product product = new Product();
//        List<Image> images = new ArrayList<Image>();
//        List<ImageView> viewImages = productView.getImages();
//        for (int i = 0; i < viewImages.size(); i++)
//        {
//            ImageView current = viewImages.get(i);
//            Image img = new Image();
//            img.setId(current.getId());
//            img.setContent(current.getContent());
//            images.add(img);
//        }
//
//        product.setId(productView.getId());
//        product.setSku(productView.getSku());
//        product.setTitle(productView.getTitle());
//        product.setPrice(productView.getPrice());
//        product.setDescription(productView.getDescription());
//        product.setImages(viewImages);
//        productRepository.save(product);
    }
}
