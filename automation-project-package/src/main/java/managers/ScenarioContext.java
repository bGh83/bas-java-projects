package managers;

import java.util.HashMap;
import java.util.Map;

import enums.Context;

public class ScenarioContext {
	
	private  Map<String, Object> sc;

	public ScenarioContext() {
		sc = new HashMap<>();
	}
	
	public void setContext(Context key, Object value) {
        sc.put(key.toString(), value);
    }

    public Object getContext(Context key){
        return sc.get(key.toString());
    }

    public Boolean isContains(Context key){
        return sc.containsKey(key.toString());
    }

}
