package autUtilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageObjectParser {

    public Map<String, String> parse(String filename) {
        Map<String, String> result = null;
        try {
            result = new ObjectMapper().readValue(new File(filename), LinkedHashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

/*

http://www.testautomationguru.com/selenium-webdriver-scriptless-page-object-design-pattern-part-3/

//inject JQuery
var jq = document.createElement('script');
jq.src = "//code.jquery.com/jquery-3.2.1.min.js";
document.getElementsByTagName('head')[0].appendChild(jq);

//create alias
var $j = jQuery.noConflict();

//inject function for page object model
var eles = {};
var pageObjectModel = function(root){
  $j(root).find('input, select, textarea').filter(':enabled:visible:not([readonly])').each(function(){
      let eleName = this.name;
      eles[eleName] = "${" + eleName + '}';
  });
  console.log(JSON.stringify(eles, null, 4));
}

//invoke the function
pageObjectModel(document)

*/