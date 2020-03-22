package info.wondee.app.financeapp.specialcosts;

import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/specialcosts")
public class SpecialCostRestController {

  UserService service;

  FinanceDataRepository repository;

  @PostMapping
  public void post(@RequestBody @Valid SpecialCostUi costUi) {
    SpecialCost cost = costUi.toDataObject();

    repository.save(service.findFinanceData(), cost, cost.getId());
  }

}
