package stepdefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import enums.Context;
import enums.StepData;
import io.cucumber.datatable.DataTable;
import support.TestContext;

public class CommonStepDefFunctions {

	TestContext testContext;
	
	public CommonStepDefFunctions (TestContext testContext) {
		this.testContext = testContext;
	}
	
	public void setDataTable(DataTable dt) {
		
		List<Map<StepData, String>> stepdata = new ArrayList<>();
		for(Map<String, String> row:dt.asMaps(String.class, String.class)) {
				stepdata.add(row.entrySet().stream().collect(
						Collectors.toMap(entry -> StepData.valueOf(entry.getKey()), Map.Entry::getValue)));
		}		
		
		testContext.scenarioContext.setContext(Context.STEPDATA, stepdata);
	}

	public String getDataTable(StepData key) {
		return getDataTable().get(0).get(key);
	}
	
	public List<Map<StepData, String>> getDataTable() {
		return (List<Map<StepData, String>>) testContext.scenarioContext.getContext(Context.STEPDATA);
	}
	
}
