package com.nr.simplevaadinapp.view;

import com.nr.simplevaadinapp.entity.Manufacturer;
import com.nr.simplevaadinapp.entity.Product;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import java.util.HashSet;
import java.util.Set;

@Route(value = ProductMangerView.ROUTE)
@RouteAlias("")
@PageTitle(value = ProductMangerView.TITLE)
public class ProductMangerView extends Composite<Component> {

  public static final String ROUTE = "productMangerView";
  public static final String TITLE = "Product Manger";

  private static Set<Product> products = new HashSet<>();
  private static Set<Manufacturer> manufacturers = new HashSet<>();
  private VerticalLayout productLayout = new VerticalLayout();

  static {
    manufacturers.add(
      new Manufacturer("PepsiCo", "555111", "pepsi@example.com")
    );
    manufacturers.add(new Manufacturer("Cola", "555222", "cola@example.com"));
    manufacturers.add(new Manufacturer("Dew", "555333", "dew@example.com"));

    products.add(
      new Product(
        "Dew",
        true,
        new Manufacturer("Dew", "555333", "dew@example.com")
      )
    );
  }

  @Override
  protected Component initContent() {
    updateList();
    return new VerticalLayout(
      new Button(
        "New Product",
        VaadinIcon.PLUS.create(),
        event -> {
          showProductForm(new Product());
        }
      ),
      productLayout
    );
  }

  private void updateList() {
    productLayout.removeAll();
    products
      .stream()
      .map(product ->
        new Details(
          product.getName() + (product.isAvailable() ? "" : "(Not Available)"),
          new HorizontalLayout(
            new Button(
              VaadinIcon.EDIT.create(),
              event -> showProductForm(product)
            ),
            new Button(VaadinIcon.TRASH.create(), event -> delete(product))
          )
        )
      )
      .forEach(productLayout::add);
  }

  private void delete(Product product) {
    products.remove(product);
    updateList();
    Notification.show("Item removed: " + product.getName());
  }

  private void save(Product product) {
    products.add(product);
    updateList();
    Notification.show("Item saved: " + product.getName());
  }

  private void showProductForm(Product product) {
    Dialog dialog = new Dialog();
    dialog.setModal(true);
    dialog.open();
    dialog.add(
      new ProductForm(
        product,
        manufacturers,
        () -> {
          dialog.close();
          save(product);
        }
      )
    );
  }
}
