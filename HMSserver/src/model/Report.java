package model;

import java.io.Serializable;

public class Report implements Serializable {

    private String type;
    private String content;

    public Report() { }

    public Report(String type) {
        this.type = type;
    }

   
    public void generateReport() {
        this.content = "Report generated for: " + type;
    }

    public String getType() { return type; }
    public String getContent() { return content; }
}
