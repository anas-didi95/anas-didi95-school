/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.timesheet.dto.CheckInReqDTO;
import com.anasdidi.school.timesheet.dto.CheckInResDTO;
import com.anasdidi.school.timesheet.dto.CheckOutReqDTO;
import com.anasdidi.school.timesheet.dto.CheckOutResDTO;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimesheetConstants {

  @UtilityClass
  public static class Event {
    public static final String TSHT_CHECK_IN = "TSHT_CHECK_IN";
    public static final String TSHT_CHECK_OUT = "TSHT_CHECK_OUT";
  }

  @Getter
  @RequiredArgsConstructor
  public enum EventEnum implements CommonEvent {
    TSHT_CHECK_IN(Event.TSHT_CHECK_IN, CheckInReqDTO.class, CheckInResDTO.class),
    TSHT_CHECK_OUT(Event.TSHT_CHECK_OUT, CheckOutReqDTO.class, CheckOutResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;
  }

  public enum TimesheetTypeEnum {
    CHECK_IN,
    CHECK_OUT;

    public static class Converter implements AttributeConverter<TimesheetTypeEnum, String> {

      @Override
      public String convertToDatabaseColumn(TimesheetTypeEnum attribute) {
        return attribute.name();
      }

      @Override
      public TimesheetTypeEnum convertToEntityAttribute(String dbData) {
        return TimesheetTypeEnum.valueOf(dbData);
      }
    }
  }

  public static final String BASE_URL = "/timesheet";
  public static final String CONNECTION_NAME = "CN-TIMESHEET";
}
