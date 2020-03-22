package info.wondee.app.financeapp;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.YearMonth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import info.wondee.app.financeapp.financedata.FinanceData;
import info.wondee.app.financeapp.financedata.FinanceDataRepository;
import info.wondee.app.financeapp.fixedcosts.MonthlyFixedCost;
import info.wondee.app.financeapp.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FinanceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private FinanceDataRepository financeDataRepository;

	@Test
	public void fixedCostControllerTest() throws Exception {

		FinanceData data = new FinanceData();
		data.getMonthlyFixedCosts().addAll(
			asList(
				new MonthlyFixedCost("Gehalt", 500, null, null),
				new MonthlyFixedCost("Miete", -400, new FinanceMonth(YearMonth.of(2021, 2)), null)
			)
		);
		
		when(userService.findFinanceData()).thenReturn(data );
		
		MvcResult result = mockMvc.perform(get("/api/costs")).andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	
	

}
