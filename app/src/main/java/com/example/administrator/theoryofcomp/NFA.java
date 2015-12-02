package com.example.administrator.theoryofcomp;

import java.util.ArrayList;

/**
 * Created by Jess Mann
 */
public class NFA {
    //Instance Variables
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private ArrayList<Transition> transitions;
    private String startState;
    private ArrayList<String> acceptingStates;

    //Constructor
    public NFA (ArrayList<String> newStates, ArrayList<String> newAlphabet, ArrayList<Transition> newTransitions,
                String newStartState, ArrayList<String> newAcceptingStates){
        if (newStates != null)
            states = newStates;
        else {
            states = new ArrayList<>();
            states.add("");
        }

        if (newAlphabet != null)
            alphabet = newAlphabet;
        else {
            alphabet = new ArrayList<>();
            alphabet.add("");
        }

        if (newTransitions != null)
            transitions = newTransitions;
        else {
            transitions = new ArrayList<>();
            transitions.add(new Transition("","",""));
        }

        if (newStartState != null)
            startState = newStartState;
        else
            startState = "";

        if (newAcceptingStates != null)
            acceptingStates = newAcceptingStates;
        else {
            acceptingStates = new ArrayList<>();
            acceptingStates.add("");
        }
    }

    //Default Constructor
    public NFA (){
        states = new ArrayList<>();
        alphabet = new ArrayList<>();
        transitions = new ArrayList<>();
        startState = null;
        acceptingStates = new ArrayList<>();
    }

    public ArrayList<String> getStates(){
        return states;
    }

    public ArrayList<String> getAlphabet(){
        return alphabet;
    }

    public ArrayList<Transition> getTransitions(){
        return transitions;
    }

    public String getStartState(){
        return startState;
    }

    public ArrayList<String> getAcceptingStates(){
        return acceptingStates;
    }

    public void setAlphabet(ArrayList<String> value){
        alphabet = value;
    }

    public void setStartState(String value){
        startState = value;
    }

    public void addAlphabet(String newAlphabet) {
        if(!alphabet.contains(newAlphabet)){
            alphabet.add(newAlphabet);
        }
    }

    public void addState(String state){
        states.add(state);
    }

    //Returns the highest state number
    public String getLastTransistion(){
        int biggestInt = 0;
        for(String s : states){
            if(Integer.parseInt(s.substring(1)) > biggestInt){
                biggestInt = Integer.parseInt(s.substring(1));
            }
        }
        return "q" + biggestInt;
    }

    public void addTransistion(Transition newTransistion){
       transitions.add(newTransistion);
    }

    public void addAcceptState(String newAcceptState){
        acceptingStates.add(newAcceptState);
    }
}

