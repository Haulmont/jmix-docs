package datamodel.ex1.screen.product;

import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import datamodel.ex1.entity.Product;
import datamodel.ex1.entity.ProductPart;

@UiController("sample_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {

    @Autowired
    private InstanceContainer<Product> productDc;

    // tag::data-context-create[]
    @Autowired
    private DataContext dataContext;

    @Autowired
    private CollectionPropertyContainer<ProductPart> partsDc;

    @Subscribe("partsTable.create")
    public void onPartsTableCreate(Action.ActionPerformedEvent event) {
        ProductPart part = dataContext.create(ProductPart.class);
        partsDc.getMutableItems().add(part);
    }
    // end::data-context-create[]

    @Subscribe("partsTable.remove")
    public void onPartsTableRemove(Action.ActionPerformedEvent event) {
        if (partsDc.getItemOrNull() != null) {
            partsDc.getMutableItems().remove(partsDc.getItem());
        }
    }

    @Subscribe(id = "partsDc", target = Target.DATA_CONTAINER)
    public void onPartsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<ProductPart> event) {
        dataContext.setModified(productDc.getItem(), true);
    }
}