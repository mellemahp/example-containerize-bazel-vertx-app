package com.example.handlers;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonTypeName("PetInput")
public class PetInput {
  
  public String petId;

  public PetInput(@JsonProperty("petId") String petId) {
    this.petId = petId;
  }

  public PetInput petId(String petId) {
    this.petId = petId;
    return this;
  }

  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getPetId() {
    return petId;
  }


  public void setPetId(String petId) {
    this.petId = petId;
  }
}
