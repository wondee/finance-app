package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class FixedCost {

  private Integer id;
  
  private String name;
  private int amount;
  

  public static String sumList(List<? extends FixedCost> fixedCosts) {
    int amount = fixedCosts.stream().map(cost -> cost.getAmount()).reduce(0, Integer::sum);
    
    return displayAmount(amount);
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  
  public static String displayAmount(int amount) {
    return String.format("%,d", amount) + Currency.getInstance(Locale.GERMANY).getSymbol();
  }

  public String getDisplayAmount() {
    return displayAmount(amount);
  }

  public boolean appliesAt(Month month) {
    return true;
  }
}
