package info.wondee.app.financeapp.export;

//import java.io.IOException;
//import java.io.OutputStream;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import info.wondee.app.financeapp.user.FinanceUser;
//import info.wondee.app.financeapp.user.FinanceUserRepository;
//
//@Controller
//public class ExportImportController {
//
//
//  @Autowired
//  private FinanceUserRepository repository;
//  
//  @GetMapping("/export")
//  public void doExport(HttpServletResponse response) {
//    
//    FinanceUser currentUser = repository.findCurrentUser();
//    
//    try (OutputStream os = response.getOutputStream()) {
//      
//      response.setHeader("Content-Disposition", "attachment; filename=export.json"); 
//      response.setContentType("application/json");
//      
//      new ObjectMapper().writeValue(os, currentUser);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    
//  }
//  
//}
