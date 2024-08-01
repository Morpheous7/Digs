package com.digs.dig0.dto;


import com.digs.dig0.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class EventDto extends PropertyEditorSupport implements Serializable {
    private String event_type;
    private String event_Title;
    private MultipartFile inputFile;
    private User event_Organizer;
    private String location;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private LocalDateTime eventStart_Time;
    private Boolean single_Event;
    private Boolean repetitive_Event;
    private Boolean privateEvent;

    public EventDto() { }

    @Override
    public String toString() {
        return "EventDto{" +
                "event_type=" + event_type +
                ", event_Title='" + event_Title + '\'' +
                ", inputFile=" + inputFile +
                ", event_Organizer=" + event_Organizer +
                ", location='" + location + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", eventStart_Time=" + eventStart_Time +
                ", single_Event=" + single_Event +
                ", repetitive_Event=" + repetitive_Event +
                ", privateEvent=" + privateEvent +
                '}';
    }

    @Override
    public String getAsText() {
        EventDto eventDto = (EventDto) getValue();
        if (eventDto.getEvent_type() == null) {
            return eventDto.getEvent_type().toString();
        }
        if (eventDto.getEvent_Title() == null) {
            return eventDto.getEvent_Title().toString();
        }
        if (eventDto.getAddress1() == null) {
            return eventDto.getAddress1().toString();
        }
        if (eventDto.getAddress2() == null) {
            return eventDto.getAddress2().toString();
        }
        if (eventDto.getLocation() == null) {
            return eventDto.getLocation().toString();
        }
        if (eventDto.getCity() == null) {
            return eventDto.getCity().toString();
        }
        if (eventDto.getCountry() == null) {
            return eventDto == null  ? "" : eventDto.getAsText();
        }
        if (eventDto.getZip() == null) {
            return eventDto.getAsText();
        }

        return eventDto.getAsText();
    }
    public LocalDateTime getAsLocalDateTime() {
        EventDto eventDto = (EventDto) getValue();
        if (eventDto.getEventStart_Time() == null) {
            return eventDto == null  ? null : eventDto.getAsLocalDateTime();
        }
        return eventDto.getAsLocalDateTime();
    }

    public Boolean getAsBoolean() {
        EventDto eventDto = (EventDto) getValue();

        if (eventDto.getSingle_Event() == null) {
            return eventDto == null ? null : eventDto.getAsBoolean();
        }
        if (eventDto.getRepetitive_Event() == null) {
            return eventDto == null  ? null : eventDto.getAsBoolean();
        }
        if (eventDto.getPrivateEvent() == null) {
            return eventDto == null  ? null : eventDto.getAsBoolean();
        }
        return eventDto.getAsBoolean();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasLength(text)) {
            setValue(null);
        } else {
            EventDto eventDto = new EventDto();
            eventDto.setEvent_Title(text);
            eventDto.setAddress1(text);
            eventDto.setAddress2(text);
            eventDto.setCity(text);
            eventDto.setState(text);
            eventDto.setZip(text);
            eventDto.setCountry(text);
            setValue(eventDto);
        }
    }

    public void setAsBoolean(String text) {
        if (!StringUtils.hasLength(text)) {
            setValue(null);
        }
        if (text.equalsIgnoreCase("on")) {
            text = "true";
        } else {
            text = "false";
        }

        EventDto eventDto = new EventDto();
        eventDto.setSingle_Event(Boolean.parseBoolean(text));
        eventDto.setRepetitive_Event(Boolean.parseBoolean(text));
        eventDto.setPrivateEvent(Boolean.parseBoolean(text));
        setValue(eventDto);
    }

    public void setLocalDateTime(String localDateTime) {
        if (!StringUtils.hasLength(localDateTime)) {
            setValue(null);
        }        else {
            EventDto eventDto = new EventDto();
            LocalDateTime formatter;
            String dateTime = localDateTime.substring(1,17);
            formatter = LocalDateTime.parse(dateTime);
            eventDto.setEventStart_Time(formatter);
            setValue(eventDto);
        }
    }
}
