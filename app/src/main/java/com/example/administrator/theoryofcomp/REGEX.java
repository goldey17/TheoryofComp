package com.example.administrator.theoryofcomp;

/**
 * Created by goldey17 on 11/24/2015.
 */
public class REGEX {
    private String language;

    public REGEX (String newLanguage){
        language = newLanguage;
    }

    public NFA toNFA(){
        return null;
    }

    public void setLanguage (String newLanguage){
        language = newLanguage;
    }

    public String getLanguage (){
        return language;
    }
}
