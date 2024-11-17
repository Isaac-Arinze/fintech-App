package com.zikan.fintech_Bank_App.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetails {

    private String from;
    private String recipient;
    private String subject;
    private String messageBody;
    private List<Object> attachment;
    private Map<String, Object> props;

}
