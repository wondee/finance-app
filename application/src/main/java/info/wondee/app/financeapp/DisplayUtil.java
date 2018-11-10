package info.wondee.app.financeapp;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.ImmutableMap;

import info.wondee.app.financeapp.fixedcosts.Cost;
import info.wondee.app.financeapp.fixedcosts.CostPresenter;
import info.wondee.app.financeapp.fixedcosts.CostType;
import info.wondee.app.financeapp.fixedcosts.FixedCostPresenter;

public class DisplayUtil {

  private static DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("MM/yyyy").toFormatter();
  
  public static String format(YearMonth yearMonth) {
    return formatter.format(yearMonth);
  }

  public static String format(FinanceMonth dueDate) {
    return format(dueDate.toDate());
  }
  
  public static YearMonth parse(String string) {
    if (string == null) return null;
    return YearMonth.parse(string, formatter);
  }
  

  public static FinanceMonth parseToDate(String string) {
    if (string == null) return null;
    return new FinanceMonth(parse(string));
  }
  
  public static String createDisplayMonth(int month) {
    return createDisplayMonth(toMonth(month));
  }

  public static String createDisplayMonth(Month month) {
    
    DateFormatSymbols dfs = new DateFormatSymbols();
    return dfs.getMonths()[month.getValue() - 1];
    
  }

  public static String createDisplayMonthAndYear(String string) {
    return createDisplayMonthAndYear(parse(string));
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
  

  public static String processSaving(RedirectAttributes attr, CostPresenter<?> presenter, 
      BindingResult bindingResult, String type, Runnable action) {
    
    if (bindingResult.hasErrors()) {
      
      attr.addFlashAttribute("model", presenter);
      attr.addFlashAttribute("org.springframework.validation.BindingResult.model", bindingResult);
      
      attr.addAttribute("type", type);
      attr.addAttribute("target", presenter.getTarget());
      
      if (presenter.getId() != null) {
        attr.addAttribute("id", presenter.getId());
      }
      
      return "redirect:/fixedcosts/edit";
    }
    
    action.run();
    
    return "redirect:/" + presenter.getTargetUri();
  }

  
  public static enum Target {
    OVERVIEW("overview"), 
    SPECIAL("specialcosts"),
    FIXED("fixedcosts");
    
    public String uri;
    
    Target(String uri) {
      this.uri = uri;
    }
    
    public String getUri() {
      return uri;
    }

    public static String getUri(String target) {
      return Target.valueOf(target.toUpperCase()).getUri();
    }
  }


  
}
