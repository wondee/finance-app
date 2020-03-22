package info.wondee.app.financeapp;

import java.io.IOException;
import java.time.YearMonth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class FinanceMonthDeserializer extends JsonDeserializer<FinanceMonth> {

	@Override
	public FinanceMonth deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec codec = p.getCodec();
		JsonNode tree = codec.readTree(p);
		
		validateArray(tree);
		
		Integer year = codec.treeToValue(tree.get(0), int.class);
		Integer month = codec.treeToValue(tree.get(1), int.class);
		
		return new FinanceMonth(YearMonth.of(year, month));
	}

	private void validateArray(JsonNode tree) {
		if (!tree.isArray() && tree.size() != 2) {
			throw new IllegalArgumentException("FinanceMonth must be an Array [year, month]");
		}
	}

}
