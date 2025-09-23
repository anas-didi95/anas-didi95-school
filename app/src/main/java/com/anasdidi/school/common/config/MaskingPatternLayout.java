/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MaskingPatternLayout extends PatternLayout {

  private final List<String> maskPatterns = new ArrayList<>();
  private volatile Pattern multilinePattern;

  @Override
  public String doLayout(ILoggingEvent event) {
    return maskMessage(super.doLayout(event));
  }

  public synchronized void addMaskPattern(String maskPattern) {
    try {
      maskPatterns.add(maskPattern);
      multilinePattern =
          Pattern.compile(
              maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
    } catch (Exception e) {
      addError("Invalid mask pattern: " + maskPattern, e);
    }
  }

  public List<String> getMaskPatterns() {
    return Collections.unmodifiableList(maskPatterns);
  }

  private String maskMessage(String message) {
    if (multilinePattern == null) {
      return message;
    }
    StringBuilder sb = new StringBuilder(message);
    Matcher matcher = multilinePattern.matcher(sb);
    while (matcher.find()) {
      IntStream.rangeClosed(1, matcher.groupCount())
          .forEach(
              group -> {
                if (matcher.group(group) != null) {
                  IntStream.range(matcher.start(group), matcher.end(group))
                      .forEach(i -> sb.setCharAt(i, '*'));
                }
              });
    }
    return sb.toString();
  }
}
