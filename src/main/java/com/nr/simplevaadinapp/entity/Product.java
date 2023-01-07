package com.nr.simplevaadinapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String name;
  private boolean available = true;
  private Manufacturer manufacturer;
}
