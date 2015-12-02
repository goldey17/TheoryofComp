package com.example.administrator.theoryofcomp;

import java.util.ArrayList;

/**
 * Created by Jess Mann
 */
public class REGEX {
    //Instance Variables
    private String language;

    public REGEX (String newLanguage){ language = newLanguage; }

    public NFA toNFA(){
        if(language.equals("e")){
            return case2(language);
        }else if (language.equals("n")){
            return case3(language);
        }else {
            return baseCase(language);
        }
    }

    //Handles the calling of cases recursively, see summary for limitations
    public NFA baseCase(String language){
        if(language.charAt(0) == '('){
            String parenString = language.substring(1, language.lastIndexOf(")"));
            if(language.substring(language.length() - 1).equals("*")){ //Star case
                return  case6(baseCase(parenString));
            }else if(parenString.contains("u") && (parenString.indexOf('u') < parenString.indexOf("(") //Union Case
                    || parenString.indexOf('u') > parenString.indexOf(")"))){
                String beforeU = parenString.substring(0, parenString.indexOf('u'));
                String afterU = parenString.substring(parenString.indexOf('u') + 1);
                return case4(baseCase(beforeU), baseCase(afterU));
            }else{//dot case
                NFA left = baseCase(parenString.substring(0, parenString.indexOf('.')));
                parenString = parenString.substring(parenString.indexOf('.') + 1);
                NFA right = baseCase(parenString.substring(0,parenString.indexOf('.')));
                parenString = parenString.substring(parenString.indexOf('.') + 1);
                while (parenString.length() > 0){ //While more things to dot
                    left = case5(left, right);
                    if(parenString.contains(".")){
                        right = baseCase(parenString.substring(0, parenString.indexOf('.')));
                        parenString = parenString.substring(parenString.indexOf('.') + 1);
                    }else {
                        right = baseCase(parenString);
                        parenString = "";
                    }
                }
                return case5(left, right);
            }
        }else{ //base case literal
            return case1(language);
        }
    }

    //Make a NFA of one letter
    private NFA case1(String transLetter){
        //variables
        ArrayList<String> newStates = new ArrayList<>();
        ArrayList<String> newAcceptStates = new ArrayList<>();
        ArrayList<Transition> newTransistions = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        String start = "q0";

        newStates.add("q0");
        newStates.add("q1");
        newAcceptStates.add("q1");
        alphabet.add(transLetter);
        Transition temp = new Transition("q0", "q1", transLetter);
        newTransistions.add(temp);
        return (new NFA(newStates,alphabet,newTransistions,start,newAcceptStates));
    }

    //If epsilon just accept
    private NFA case2(String language){
        NFA newNFA = new NFA(); //initialize empty NFA
        newNFA.addState("q0");
        newNFA.setStartState("q0");
        newNFA.addAcceptState("q0");
        return newNFA;
    }

    //If null
    private NFA case3(String language){
        NFA newNFA = new NFA(); //initialize empty NFA
        newNFA.addState("q0");
        newNFA.setStartState("q0");
        return newNFA;
    }

    //Union
    private NFA case4(NFA inputNFA1, NFA inputNFA2){
        NFA newNFA = new NFA(); //initialize empty NFA

        //New Start State
        newNFA.setStartState("q0");

        //Add all r1 transitions
        int y = 1;
        for (Transition t : inputNFA1.getTransitions()){
            int x = 0;
            t.increaseBy(1 + x);
            newNFA.addTransistion(t);
            x++;
            y++;
        }

        //Add all r2 transitions
        int r2newStartNum =  Integer.parseInt(inputNFA1.getLastTransistion().substring(1)) + y;
        for (Transition t : inputNFA2.getTransitions()){
            int x = 0;
            t.increaseBy(r2newStartNum + x);
            newNFA.addTransistion(t);
            x++;
        }

        //Add e transitions from new start state to old start states
        newNFA.addTransistion(new Transition("q0","q" + (Integer.parseInt(inputNFA1.getStartState().substring(1)) + 1),"e"));
        newNFA.addTransistion(new Transition("q0","q" + (Integer.parseInt(inputNFA2.getStartState().substring(1)) + y + 1),"e"));

        //add new start state
        newNFA.addState("q0");

        int x = 1;
        //add all r1 states
        for (String s : inputNFA1.getStates()) {
            newNFA.addState("q" + (Integer.parseInt(s.substring(1))+1));
            x++;
        }

        //add all r2 states
        for (String s : inputNFA2.getStates()) {
            newNFA.addState("q" + (Integer.parseInt(s.substring(1))+x));
        }

        //create alphabet
        newNFA.setAlphabet(inputNFA1.getAlphabet());
        for (String s : inputNFA2.getAlphabet()){
            newNFA.addAlphabet(s);
        }
        newNFA.addAlphabet("e");

        //add r1 accpet states
        for (String s : inputNFA1.getAcceptingStates()){
            newNFA.addAcceptState("q" + (Integer.parseInt(s.substring(1)) + 1));
        }

        //add r2 accpet states
        for (String s : inputNFA2.getAcceptingStates()){
            newNFA.addAcceptState("q" + (Integer.parseInt(s.substring(1)) + y + 1));
        }

        return newNFA;
    }

    //Dot two NFA's together
    private NFA case5(NFA inputNFA1, NFA inputNFA2){

        NFA newNFA = new NFA(); //initialize empty NFA

        //r1 is start
        for (Transition t : inputNFA1.getTransitions()) {
            newNFA.addTransistion(t);
        }

        int r2newStartNum =  Integer.parseInt(inputNFA1.getLastTransistion().substring(1)) + 1;

        //add r2 transistions
        for (Transition t : inputNFA2.getTransitions()){
            int x = 0;
            t.increaseBy(r2newStartNum + x);
            newNFA.addTransistion(t);
            x++;
        }

        //all r1 accept states epsilon transistions to r2 start
        for (String s : inputNFA1.getAcceptingStates()){
            newNFA.addTransistion(new Transition(s,"q"+r2newStartNum,"e"));
        }
        int x = 0;
        //add all r1 states
        for (String s : inputNFA1.getStates()) {
            newNFA.addState(s);
            x++;
        }

        //add all r2 states
        for (String s : inputNFA2.getStates()) {
            newNFA.addState("q" + (Integer.parseInt(s.substring(1))+x));
        }

        //create alphabet
        newNFA.setAlphabet(inputNFA1.getAlphabet());
        for (String s : inputNFA2.getAlphabet()){
            newNFA.addAlphabet(s);
        }
        newNFA.addAlphabet("e");

        //add r1 start state
        newNFA.setStartState("q0");

        //add r2 accpet states
        for (String s : inputNFA2.getAcceptingStates()){
            newNFA.addAcceptState("q" + (Integer.parseInt(s.substring(1))+x));
        }

        return newNFA;
    }

    //Star an NFA
    private NFA case6(NFA inputNFA1){

        NFA newNFA = new NFA(); //initialize empty NFA

        //add r1 transitions
        for (Transition t : inputNFA1.getTransitions()){
            int x = 0;
            t.increaseBy(1 + x);
            newNFA.addTransistion(t);
            x++;
        }

        //add epsilon transitions from accept states to old start state
        for(String s : inputNFA1.getAcceptingStates()){
            newNFA.addTransistion(new Transition("q" + (Integer.parseInt(s.substring(1))+1),"q" + (Integer.parseInt(inputNFA1.getStartState().substring(1))+1),"e"));
        }

        //add new start state transition
        newNFA.addTransistion(new Transition("q0", "q1", "e"));

        newNFA.addState("q0");

        //add all r1 states
        for (String s : inputNFA1.getStates()) {
            newNFA.addState("q" + (Integer.parseInt(s.substring(1))+1));
        }

        //create alphabet
        newNFA.setAlphabet(inputNFA1.getAlphabet());
        newNFA.addAlphabet("e");

        //add r1 start state
        newNFA.setStartState("q0");

        newNFA.addAcceptState("q0");

        //add r2 accpet states
        for (String s : inputNFA1.getAcceptingStates()){
            newNFA.addAcceptState("q" + (Integer.parseInt(s.substring(1))+1));
        }

        return newNFA;
    }
}