package info.wondee.app.financeapp;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FinanceMonthSerializer extends JsonSerializer<FinanceMonth> {

	@Override
	public void serialize(FinanceMonth value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		int[] yearMonthArray = new int[] {
				value.getYear(),
				value.getMonth().getValue()
		};
		
		gen.writeArray(yearMonthArray, 0, 2);
		
	}

}
