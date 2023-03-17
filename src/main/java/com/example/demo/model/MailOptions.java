package com.example.demo.model;

public class MailOptions {
    private String recipient;
    private String fileContent;
    private String key;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isComplete(){
        if(getKey() != null && getRecipient()!= null || getFileContent()!= null) return  true;
        return  false;
    }
}
