package info.wondee.app.financeapp.fixedcosts;

import java.util.Currency;
import java.util.Locale;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import info.wondee.app.financeapp.DisplayUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class CostPresenter<T extends Cost> {

  @Min(0)
  @Max(100)
  private Integer id;
  
  @NotNull
  @Size(min=1, max=100)
  private String name;
  
  @Min(1)
  private int amount;
  private boolean incoming;
  
  public CostPresenter(T object) {
    amount = Math.abs(object.getAmount());
    name = object.getName();
    incoming = object.getAmount() > 0;
    id = object.getId();
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

  public abstract String getType();
  
  @JsonProperty
  public String getDisplayType() {
    return DisplayUtil.toDisplayString(CostType.valueOf(getType().toUpperCase()));
  }
  
  public static <T extends Cost> CostPresenter<T> from(T cost) {
    return cost.getType().toPresenter(cost);
  }
  
}