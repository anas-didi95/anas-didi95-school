/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.converter;

import io.vertx.core.json.JsonArray;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class ListStringConverter implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    return new JsonArray(Optional.ofNullable(attribute).orElse(Collections.emptyList())).encode();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    return new JsonArray(Optional.ofNullable(dbData).orElse("")).getList();
  }
}
