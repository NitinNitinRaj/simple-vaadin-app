package com.nr.simplevaadinapp.view;

import com.nr.simplevaadinapp.entity.Manufacturer;
import com.nr.simplevaadinapp.entity.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableRunnable;
import java.util.Set;

public class ProductForm extends Composite<Component> {

  private final SerializableRunnable saveListner;
  private Product product;

  private TextField name = new TextField("Name");
  private ComboBox<Manufacturer> manufacturer = new ComboBox<>("Manufacturer");
  private Checkbox availbale = new Checkbox("Availbale");

  public ProductForm(
    Product product,
    Set<Manufacturer> manufacturers,
    SerializableRunnable saveListner
  ) {
    this.product = product;
    this.saveListner = saveListner;
    manufacturer.setItems(manufacturers);
    manufacturer.setItemLabelGenerator(Manufacturer::getName);

    if (product.getName() != null) {
      name.setValue(product.getName());
      manufacturer.setValue(product.getManufacturer());
      availbale.setValue(product.isAvailable());
    }
  }

  @Override
  protected Component initContent() {
    return new VerticalLayout(
      new H1("Product"),
      name,
      manufacturer,
      availbale,
      new Button(
        "Save",
        VaadinIcon.CHECK.create(),
        e -> {
          saveProduct();
        }
      )
    );
  }

  private void saveProduct() {
    product.setName(name.getValue());
    product.setManufacturer(manufacturer.getValue());
    product.setAvailable(availbale.getValue());
    saveListner.run();
  }
}
