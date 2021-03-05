package by.legan.android.firealert.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(value = { "editTimestamp", "creationTimestamp" })
public class AlertMessage {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String message_agent_id;
    @JsonProperty
    private String message;
    @JsonProperty
    private String json_intent;
    private LocalDateTime editTimestamp;
    private LocalDateTime creationTimestamp;
}