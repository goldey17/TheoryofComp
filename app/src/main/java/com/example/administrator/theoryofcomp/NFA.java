package com.example.administrator.theoryofcomp;

import java.util.ArrayList;

/**
 * Created by goldey17 on 11/24/2015.
 */
public class NFA {
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private ArrayList<String> transitions;
    private String startState;
    private ArrayList<String> acceptingStates;

    public NFA (ArrayList<String> newStates, ArrayList<String> newAlphabet, ArrayList<String> newTransitions,
                String newStartState, ArrayList<String> newAcceptingStates){
        states = newStates;
        alphabet = newAlphabet;
        transitions = newTransitions;
        startState = newStartState;
        acceptingStates = newAcceptingStates;
    }

    public DFA toDFA(){
        return null;
    }

    public ArrayList<String> getStates(){
        return states;
    }

    public ArrayList<String> getAlphabet(){
        return alphabet;
    }

    public ArrayList<String> getTransitions(){
        return transitions;
    }

    public String getStartState(){
        return startState;
    }

    public ArrayList<String> getAcceptingStates(){
        return acceptingStates;
    }

    public void setStates(ArrayList<String> value){
        states = value;
    }

    public void setAlphabet(ArrayList<String> value){
        alphabet = value;
    }

    public void setTransitions(ArrayList<String> value){
        transitions = value;
    }

    public void setStartState(String value){
        startState = value;
    }

    public void setAcceptingStates(ArrayList<String> value){
        acceptingStates = value;
    }
}

