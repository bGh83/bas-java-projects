package support;

public class CommonFunctionManager {
	
	private static CommonFunctionManager commonFunctionManager = new CommonFunctionManager();
	private static CommonWebDriverFunctions commonWebDriverFunctions;

	private CommonFunctionManager() {
	}

	 public static CommonFunctionManager getInstance() {
	      return commonFunctionManager;
	 }

	 public CommonWebDriverFunctions getCommonDriverFunctions() {
		 return (commonWebDriverFunctions == null) ? new CommonWebDriverFunctions() : commonWebDriverFunctions;
	 }
}
