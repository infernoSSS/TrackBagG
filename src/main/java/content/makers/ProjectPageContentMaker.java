package content.makers;

import util.TemplatesPlaceholder;

import java.util.HashMap;
import java.util.Map;

public class ProjectPageContentMaker {
    private Map<String, String> contentSlots;

    public ProjectPageContentMaker() {
        contentSlots = new HashMap();
        contentSlots.put("username", "");
        contentSlots.put("project_name", "");
        contentSlots.put("open_closed", "open");
        contentSlots.put("on_page", "10");
        contentSlots.put("page", "1");
        contentSlots.put("choose_open", "choosed");
        contentSlots.put("choose_closed", "unchoosed");
        contentSlots.put("choosed_ope_10", "");
        contentSlots.put("choosed_ope_20", "");
        contentSlots.put("choosed_ope_30", "");
        contentSlots.put("uuid", "");
        contentSlots.put("content", "");
        contentSlots.put("page", "1");
    }

    public String getProjectPage(Map<String, String> parameters){
        Map<String, String> contentSlotsBuff = new HashMap(contentSlots);
        for (String key : parameters.keySet()){
            contentSlotsBuff.put(key, parameters.get(key));
        }
        contentSlotsBuff.put("choosed_ope_"+contentSlotsBuff.get("on_page"), "choosed_ope");
        if(contentSlotsBuff.get("open_closed").equals("close")){
            contentSlots.put("choose_open", "unchoosed");
            contentSlots.put("choose_closed", "choosed");
        }
        return TemplatesPlaceholder.getInstance().fillTemplateFromFile("project_page", contentSlotsBuff);
    }
}
