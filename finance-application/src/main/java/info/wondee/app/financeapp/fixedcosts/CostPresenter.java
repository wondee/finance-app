package info.wondee.app.financeapp.fixedcosts;

import java.util.Currency;
import java.util.Locale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class CostPresenter<T extends Cost> {

  private Integer id;
  private String name;
  private int amount;
  private boolean incoming;
  
  public CostPresenter(T object) {
    amount = Math.abs(object.getAmount());
    name = object.getName();
    incoming = object.getAmount() > 0;
  }
  
  public abstract T toPersistentObject();
  
  public static String displayAmount(int amount) {
    return String.format("%,d", amount) + Currency.getInstance(Locale.GERMANY).getSymbol();
  }

  public String getDisplayAmount() {
    return displayAmount(getRealAmount());
  }

  
  public int getRealAmount() {
    return getAmount() * (incoming ? 1 : -1);
  }

  public static <T extends Cost> CostPresenter<T> from(T cost) {
    return cost.getType().toPresenter(cost);
  }
  
}