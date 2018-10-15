package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Cost {

  @Id
  private Integer id;
  
  private String name;
  private int amount;
  
  public abstract CostType getType();
}
