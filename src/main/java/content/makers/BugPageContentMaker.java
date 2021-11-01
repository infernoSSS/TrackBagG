package content.makers;

import db.DB;
import util.TemplatesPlaceholder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BugPageContentMaker {
    private Map<String, String> contentSlots;

    public BugPageContentMaker() {
        contentSlots = new HashMap();
        contentSlots.put("username", "");
        contentSlots.put("bug_number", "");
        contentSlots.put("bug_status", "");
        contentSlots.put("bug_id", "");
        contentSlots.put("report_date", "");
        contentSlots.put("reported_by", "");
        contentSlots.put("description", "");
        contentSlots.put("answers", "");
        contentSlots.put("bug_post_hidden", "hidden");
        contentSlots.put("bug_edit_hidden", "hidden");
        contentSlots.put("bug_delete_hidden", "hidden");
    }

    public String createBugPage(String userName, String projectUUID){
        Map<String, String> vals = new HashMap();
        vals.put("username", userName);
        vals.put("bug_number", String.valueOf(DB.getDB().getProjectDB().getBugsNumber(projectUUID) + 1));
        vals.put("bug_status", "open");
        vals.put("report_date", "right now");
        vals.put("reported_by", userName);
        vals.put("bug_post_hidden", "");
        vals.put("uuid", projectUUID);
        return fillPage(vals);
    }

    public String viewBug(String userName, int bug_id){
        Map<String, String> vals = new HashMap();
        Map<String, Object> bug = DB.getDB().getBugsDB().getBug(bug_id);
        vals.put("username", userName);
        vals.put("bug_number", String.valueOf((Integer) bug.get("ticket_number")));
        String status = (String) bug.get("solved_status");
        String solutionId = String.valueOf(bug.get("bug_solution_id"));
        if(!solutionId.equals("0")){
            status+=DB.getDB().getAnswersDb().getAnswerAttributes(solutionId).get("bug_or_ficha");
        }
        vals.put("bug_status", status);
        vals.put("report_date", String.valueOf( (Date) bug.get("upload_date")));
        vals.put("reported_by", (String) bug.get("uploaded_by"));
        vals.put("bug_id", String.valueOf(bug_id));
        return fillPage(vals);
    }

    private String fillPage(Map<String, String> content){
        Map<String, String> tempContentSlots = new HashMap(contentSlots);
        tempContentSlots.putAll(content);
        return TemplatesPlaceholder.getInstance().fillTemplateFromFile("bug", tempContentSlots);
    }
}
