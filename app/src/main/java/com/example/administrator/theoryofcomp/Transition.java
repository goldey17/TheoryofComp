package com.example.administrator.theoryofcomp;

/**
 * Created by Jess Mann
 */
public class Transition {
    //Instance Variables
    String startState;
    String endState;
    String transitionLetter;

    public Transition(String input){
        int firstParen = input.indexOf("(");
        int endParen = input.indexOf(")");
        int firstComma = input.indexOf(",");
        int secondComma = input.indexOf(",",firstComma + 1);

        startState = input.substring(firstParen + 1, firstComma).trim();
        endState = input.substring(firstComma + 1, secondComma).trim();
        transitionLetter = input.substring(secondComma + 1, endParen).trim();
    }

    public Transition(String startState, String endState, String transitionLetter){
        this.startState = startState;
        this.endState = endState;
        this.transitionLetter = transitionLetter;
    }

    public String getStartState(){return startState;}

    public String getTransitionLetter(){return transitionLetter;}

    public String getEndState(){return endState;}

    public void setTransitionLetter(String value){ transitionLetter = value;}

    public String displayTransistion() {
        return "(" + this.startState + "," + this.endState + "," + this.transitionLetter + ")";
    }

    //Method to increase states in the transition by a given amount
    public void increaseBy(int inc) {
        int startStateNum = Integer.parseInt(startState.substring(1));
        startState = "q" + (startStateNum+inc);
        int endStateNum = Integer.parseInt(endState.substring(1));
        endState = "q" + (endStateNum+inc);
    }
}
