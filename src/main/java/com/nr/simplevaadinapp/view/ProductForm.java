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
import com.vaadin.flow.data.binder.Binder;
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

    Binder<Product> binder = new Binder<>();
    binder.bind(name, Product::getName, Product::setName);
    binder.bind(
      manufacturer,
      Product::getManufacturer,
      Product::setManufacturer
    );
    binder.bind(availbale, Product::isAvailable, Product::setAvailable);

    binder.setBean(product);
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
          saveListner.run();
        }
      )
    );
  }
}
