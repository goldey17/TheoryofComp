package com.example.administrator.theoryofcomp;

import java.util.ArrayList;

/**
 * Created by goldey17 on 11/24/2015.
 */
public class DFA {
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private ArrayList<String> transitions;
    private String startState;
    private ArrayList<String> acceptingStates;

    public DFA (ArrayList<String> newStates, ArrayList<String> newAlphabet, ArrayList<String> newTransitions,
                String newStartState, ArrayList<String> newAcceptingStates){
        states = newStates;
        alphabet = newAlphabet;
        transitions = newTransitions;
        startState = newStartState;
        acceptingStates = newAcceptingStates;
    }

    public String toRegex(){
        //Add new start state and epsilon transition to old start state
        states.add("q'");
        transitions.add("(q'," + startState + ",e");

        //Add new accept state and epsilon transition from old accept states
        states.add("f'");
        for(int i = 0; i < acceptingStates.size(); i++){
            transitions.add("(" + acceptingStates.get(i) + ",f',e)");
        }

        //Rip out a state
        for(int i = 0; i < (states.size() - 2); i++){ //For each state except the two added
            for(int j = 0; j < transitions.size(); j ++){ //For each transition
                String string = transitions.get(j); //Get first transition
                if(string.contains(states.get(i))){ //if transition has the state
                    if(string.substring(string.indexOf("(") + 1).equals(states.get(i))){ //if the state is the start state

                    }
                    if(string.substring(string.indexOf(",") + 1).equals(states.get(i))){ //if the state is the end state

                    }
                    if((string.substring(string.indexOf("(") + 1).equals(states.get(i)) && //if the state is the start and end state
                            (string.substring(string.indexOf(",") + 1).equals(states.get(i))))){

                    }
                }
            }
        }

        return "test";
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
