package info.wondee.app.financeapp;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.google.common.collect.ImmutableMap;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.CostType;
import info.wondee.app.financeapp.fixedcosts.FixedCostPresenter;

public class DisplayUtil {

  public static String createDisplayMonth(int month) {
    return createDisplayMonth(toMonth(month));
  }
  
  public static String createDisplayMonth(Month month) {
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    return dfs.getMonths()[month.getValue() - 1];
    
  }

  public static String createDisplayMonthAndYear(YearMonth yearMonth) {
    return createDisplayMonth(yearMonth.getMonth()) + " / " + yearMonth.getYear();
  }

  public static Month toMonth(int month) {
    return Month.values()[month - 1];
  }

  public static FinanceMonth toDate(int month, Integer year) {
    if (year == null) return null;
    
    return new FinanceMonth(toMonth(month), year);
  }

  public static <T extends Cost> List<CostPresenter<T>> toPresenter(Collection<T> costs) {
    return costs.stream().map(cost -> FixedCostPresenter.from(cost)).collect(Collectors.toList());
  }

  private static Map<CostType, String> costTypeMap = ImmutableMap.of(
      CostType.MONTHLY, "monatlich",

      CostType.QUATERLY, "vierteljährlich",
      CostType.YEARLY, "jährlich"
      );
  
  public static String toDisplayString(CostType type) {
    
    return costTypeMap.get(type);
  }
  

  public static String processSaving(Model model, CostPresenter<?> presenter, 
      BindingResult bindingResult, String type, String successPage, Runnable action) {
    
    if (bindingResult.hasErrors()) {
      
      bindingResult.getAllErrors().forEach(System.out::println);
      
      model.addAttribute("model", presenter);
      model.addAttribute("type", type);
      
      return "fixedcostform";
    }
    
    action.run();
    
    return "redirect:/" + successPage;
  }
  
}
