package info.wondee.app.financeapp.fixedcosts;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.common.base.Preconditions;

import info.wondee.app.financeapp.specialcosts.SpecialCostPresenter;

public enum CostType {

    SPECIAL(SpecialCostPresenter.class), 
    MONTHLY(MonthlyFixedCostPresenter.class), 
    YEARLY(YearlyFixedCostPresenter.class)
  
  ;

  private Class<? extends CostPresenter<?>> type;

  private CostType(Class<? extends CostPresenter<?>> type) {
    this.type = type;
  }
  
  @SuppressWarnings("unchecked")
  public <T extends Cost> CostPresenter<T> createInstance() {
    try {
      return (CostPresenter<T>) type.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends Cost> CostPresenter<T> toPresenter(T cost) {
    Preconditions.checkNotNull(cost);
    
    try {
      Constructor<? extends CostPresenter<?>> constructor = type.getConstructor(cost.getClass());
      return (CostPresenter<T>) constructor.newInstance(cost);
      
    } catch (NoSuchMethodException | SecurityException | InstantiationException | 
           IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
    
  }
}
