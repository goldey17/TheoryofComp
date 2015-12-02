package com.example.administrator.theoryofcomp;

import java.util.ArrayList;

/**
 * Created by Jordan Goldey
 */
public class DFA {
    //Instance variables
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private ArrayList<Transition> transitions;
    private String startState;
    private ArrayList<String> acceptingStates;

    public DFA (ArrayList<String> newStates, ArrayList<String> newAlphabet, ArrayList<Transition> newTransitions,
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
        Transition trans = new Transition("(q'," + startState + ",e)");
        transitions.add(trans);

        //Add new accept state and epsilon transition from old accept states and remove them
        states.add("f'");
        for(int i = 0; i < acceptingStates.size(); i++){
            Transition trans1 = new Transition("(" + acceptingStates.get(i) + ",f',e)");
            transitions.add(trans1);
        }
        acceptingStates.clear();
        acceptingStates.add("f'");

        //Create arrays for storing transition info and an array list for transitions to be removed
        String[] goesTo = new String[states.size()];
        String[] comesFrom = new String[states.size()];
        ArrayList<Transition> transitionsToBeRemoved = new ArrayList<>();
        String loops = "";

        //Clear the arrays
        for(int x = 0; x < states.size(); x++){
            goesTo[x] = "-1";
            comesFrom[x] = "-1";
        }

        //Rip out a state
        for(int i = 0; i < (states.size() - 2); i++){ //For each state except the two added
            String stateLookingAt = states.get(i);
            for(int j = 0; j < transitions.size(); j ++){ //For each transition
                Transition trans1 = transitions.get(j); //Get first transition
                if(trans1.getStartState().equals(stateLookingAt) || trans1.getEndState().equals(stateLookingAt)){ //if transition has the state
                    if(trans1.getStartState().equals(stateLookingAt) && trans1.getEndState().equals(stateLookingAt)){ //if loop on self
                        if(loops.equals("")){
                            loops = "(" + trans1.getTransitionLetter() + ")*";
                        }else{
                            loops = loops + "u(" +  trans1.getTransitionLetter() + ")*";
                        }
                    }else if(trans1.getStartState().equals(stateLookingAt)){ //if the state is the start state
                        int endState = states.indexOf(trans1.getEndState()); //get location of end state
                        if(!goesTo[endState].equals("-1")){ //if there is already a transition or it
                            if(goesTo[endState].contains("u")){
                                goesTo[endState] = goesTo[endState].replace(")","") + "u" + trans1.getTransitionLetter() + ")";
                            }else{
                                goesTo[endState] = "(" + goesTo[endState] + "u" + trans1.getTransitionLetter() + ")";
                            }
                        }else{ //if not just add it
                            goesTo[endState] = trans1.getTransitionLetter();
                        }
                    }else if(trans1.getEndState().equals(stateLookingAt)){ //if the state is the end state
                        int startState = states.indexOf(trans1.getStartState()); //get location of start state
                        if(!comesFrom[startState].equals("-1")){ //if there is already a transition or it
                            if(comesFrom[startState].contains("u")){
                                comesFrom[startState] = comesFrom[startState].replace(")","") + "u" + trans1.getTransitionLetter() + ")";
                            }else{
                                comesFrom[startState] = "(" + comesFrom[startState] + "u" + trans1.getTransitionLetter() + ")";
                            }
                        }else{ //if not just add it
                            comesFrom[startState] = trans1.getTransitionLetter();
                        }
                    }
                    //Keep track of transistions to be removed
                    transitionsToBeRemoved.add(trans1);
                }
            }
            //Remove old transitions
            for(int k = 0; k < transitionsToBeRemoved.size(); k++){
                transitions.remove(transitionsToBeRemoved.get(k));
            }
            //Add new transitions
            for(int m = 0; m < states.size(); m++){
                if(!comesFrom[m].equals("-1")){ //if there is a come from
                    for(int k = 0; k < states.size(); k++){
                            if(!goesTo[k].equals("-1")){ //if there is a goes to
                                //Add new transition
                                String startState = states.get(m);
                                String endState = states.get(k);
                                String transition = "";
                                if(loops.equals("")){
                                    transition = comesFrom[m] + goesTo[k];
                                }else{
                                    transition = comesFrom[m] + "(" + loops + ")" + goesTo[k];
                                }
                                Transition trans2 = new Transition(startState,endState,transition);
                                transitions.add(trans2);
                            }
                    }
                }

            }

            //Clear the arrays and the array list
            for(int x = 0; x < states.size(); x++){
                goesTo[x] = "-1";
                comesFrom[x] = "-1";
            }
            transitionsToBeRemoved.clear();
            loops = "";
        }

        //If there are more than 2 transitions u them together
        String finalTransition = "";
        if(transitions.size() != 1){
            for(int p = 0; p < transitions.size(); p++){
                if(finalTransition.equals("")){
                    finalTransition = transitions.get(p).getTransitionLetter();
                }else{
                    finalTransition = finalTransition + "u" + transitions.get(p).getTransitionLetter();
                }

            }
            transitions.get(0).setTransitionLetter(finalTransition);
        }

        //Get final regex expression and return it
        String regex = transitions.get(0).getTransitionLetter();
        return regex;
    }
}
