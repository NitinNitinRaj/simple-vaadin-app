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
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.function.SerializableRunnable;
import java.util.Set;

public class ProductForm extends Composite<Component> {

  private final SerializableRunnable saveListner;
  private Product product;

  @PropertyId("name")
  private TextField name = new TextField("Name");

  @PropertyId("manufacturer")
  private ComboBox<Manufacturer> manufacturer = new ComboBox<>("Manufacturer");

  @PropertyId("available")
  private Checkbox available = new Checkbox("Availbale");

  private TextField phoneNo = new TextField("Manufacturer Phone No");

  private TextField email = new TextField("Manufacturer Email");

  public ProductForm(
    Product product,
    Set<Manufacturer> manufacturers,
    SerializableRunnable saveListner
  ) {
    this.product = product;
    this.saveListner = saveListner;
    manufacturer.setItems(manufacturers);
    manufacturer.setItemLabelGenerator(Manufacturer::getName);

    Binder<Product> binder = new Binder<>(Product.class);
    binder.bindInstanceFields(this);
    if (product.getName() == null) {
      phoneNo.setVisible(false);
      email.setVisible(false);
    } else {
      manufacturer.setEnabled(false);
      binder.bind(phoneNo, "manufacturer.phoneNo");
      binder.bind(email, "manufacturer.email");
    }
    binder.setBean(product);
  }

  @Override
  protected Component initContent() {
    return new VerticalLayout(
      new H1("Product"),
      name,
      manufacturer,
      phoneNo,
      email,
      available,
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
