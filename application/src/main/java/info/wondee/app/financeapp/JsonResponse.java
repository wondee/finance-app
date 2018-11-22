package info.wondee.app.financeapp;

import java.util.Collections;
import java.util.List;

import info.wondee.app.financeapp.Message.Severity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JsonResponse {

  public JsonResponse(Severity severity, String message) {
    this(Collections.singletonList(new Message(severity, message)));
  }

  List<Message> messages;
  
}
