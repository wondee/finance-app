package info.wondee.app.financeapp.fixedcosts;

import java.time.Month;
import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Cost {

  private Integer id;
  
  private String name;
  private int amount;
  

  public static int sumList(Collection<? extends Cost> fixedCosts) {
    return fixedCosts.stream().map(cost -> cost.getAmount()).reduce(0, Integer::sum);
  }
  
  public static String sumListDisplay(Collection<? extends Cost> fixedCosts) {
    return displayAmount(sumList(fixedCosts));
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
