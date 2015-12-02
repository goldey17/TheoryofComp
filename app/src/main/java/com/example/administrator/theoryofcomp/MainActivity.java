package com.example.administrator.theoryofcomp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    //Input for DFA
    EditText dfa_input_states;
    EditText dfa_input_aphabet;
    EditText dfa_input_transitions;
    EditText dfa_input_start_state;
    EditText dfa_input_accept_states;

    //Result for DFA
    TextView dfa_regex_expression;

    //Test Cases for DFA
    Button dfa_test_case_1;
    Button dfa_test_case_2;
    Button dfa_test_case_3;
    Button dfa_test_case_4;

    //Layouts
    LinearLayout dfa_layout;
    LinearLayout nfa_layout;
    LinearLayout regex_layout;

    //Input for regex
    EditText regex_input_expression;

    //Results for regex
    TextView regex_nfa_states;
    TextView regex_nfa_alphabet;
    TextView regex_nfa_transitions;
    TextView regex_nfa_start_state;
    TextView regex_nfa_accept_states;

    //Test Cases for DFA
    Button regex_test_case_1;
    Button regex_test_case_2;
    Button regex_test_case_3;
    Button regex_test_case_4;

    //Submit buttons
    Button dfa_submit;
    Button regex_submit;

    //Intilizes everything,sets on click listeners, creates layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dfa_input_states = (EditText)findViewById(R.id.dfa_to_regex_states);
        dfa_input_aphabet = (EditText)findViewById(R.id.dfa_to_regex_alphabet);
        dfa_input_transitions = (EditText)findViewById(R.id.dfa_to_regex_transitions);
        dfa_input_start_state = (EditText)findViewById(R.id.dfa_to_regex_start_state);
        dfa_input_accept_states = (EditText)findViewById(R.id.dfa_to_regex_accept_states);
        dfa_regex_expression = (TextView)findViewById(R.id.dfa_to_regex_expression);
        dfa_layout = (LinearLayout)findViewById(R.id.dfa);
        dfa_submit = (Button)findViewById(R.id.dfa_submit);
        dfa_submit.setOnClickListener(this);

        dfa_test_case_1 = (Button)findViewById(R.id.testcase1);
        dfa_test_case_2 = (Button)findViewById(R.id.testcase2);
        dfa_test_case_3 = (Button)findViewById(R.id.testcase3);
        dfa_test_case_4 = (Button)findViewById(R.id.testcase4);

        dfa_test_case_1.setOnClickListener(this);
        dfa_test_case_2.setOnClickListener(this);
        dfa_test_case_3.setOnClickListener(this);
        dfa_test_case_4.setOnClickListener(this);

        nfa_layout = (LinearLayout)findViewById(R.id.nfa);
        nfa_layout.setVisibility(View.GONE);

        regex_input_expression = (EditText)findViewById(R.id.regex_to_nfa_expression);
        regex_nfa_states = (TextView)findViewById(R.id.regex_to_nfa_states);
        regex_nfa_alphabet = (TextView)findViewById(R.id.regex_to_nfa_alphabet);
        regex_nfa_transitions = (TextView)findViewById(R.id.regex_to_nfa_transitions);
        regex_nfa_start_state = (TextView)findViewById(R.id.regex_to_nfa_start_state);
        regex_nfa_accept_states = (TextView)findViewById(R.id.regex_to_nfa_accept_states);
        regex_layout = (LinearLayout)findViewById(R.id.regex);
        regex_submit = (Button)findViewById(R.id.regex_submit);
        regex_submit.setOnClickListener(this);

        regex_test_case_1 = (Button)findViewById(R.id.regex_1);
        regex_test_case_2 = (Button)findViewById(R.id.regex_2);
        regex_test_case_3 = (Button)findViewById(R.id.regex_3);
        regex_test_case_4 = (Button)findViewById(R.id.regex_4);

        regex_test_case_1.setOnClickListener(this);
        regex_test_case_2.setOnClickListener(this);
        regex_test_case_3.setOnClickListener(this);
        regex_test_case_4.setOnClickListener(this);
    }

    @Override
    //Handles button clicking and buttons for test cases
    public void onClick(View view) {
        if(view == dfa_submit){
            //Get states and put them into an arraylist
            String states = dfa_input_states.getText().toString().trim().toLowerCase();
            ArrayList<String> arrayListOfStates = new ArrayList<>();
            while (states.indexOf(",") != -1){
                String subString = states.substring(0,states.indexOf(",") + 1);
                subString = subString.replace(",", "");
                arrayListOfStates.add(subString.trim());
                states = states.substring(states.indexOf(",") + 1);
            }
            arrayListOfStates.add(states.trim());

            //Get alphabet and put into an arraylist
            String alphabet = dfa_input_aphabet.getText().toString().trim().toLowerCase();
            ArrayList<String> arrayListOfAlphabet = new ArrayList<>();
            while (alphabet.indexOf(",") != -1){
                String subString = alphabet.substring(0,alphabet.indexOf(",") + 1);
                subString = subString.replace(",", "");
                arrayListOfAlphabet.add(subString.trim());
                alphabet = alphabet.substring(alphabet.indexOf(",") + 1).trim();
            }
            arrayListOfAlphabet.add(alphabet.trim());

            //Get transitions and put into an arraylist
            String transitions = dfa_input_transitions.getText().toString().trim().toLowerCase();
            ArrayList<Transition> arrayListOfTransitions = new ArrayList<>();
            while (transitions.indexOf(")") != -1){
                String subString = transitions.substring(0,transitions.indexOf(")") + 1);
                Transition trans = new Transition(subString);
                arrayListOfTransitions.add(trans);
                transitions = transitions.substring(transitions.indexOf(")") + 1).trim();
            }

            //Get start state
            String startState = dfa_input_start_state.getText().toString().trim().toLowerCase();

            //Get accept states and put into an arraylist
            String acceptStates = dfa_input_accept_states.getText().toString().trim().toLowerCase();
            ArrayList<String> arrayListOfAcceptStates = new ArrayList<>();
            while (acceptStates.indexOf(",") != -1){
                String subString = acceptStates.substring(0,acceptStates.indexOf(",") + 1);
                subString = subString.replace(",", "");
                arrayListOfAcceptStates.add(subString.trim());
                acceptStates = acceptStates.substring(acceptStates.indexOf(",") + 1).trim();
            }
            arrayListOfAcceptStates.add(acceptStates.trim());

            //Create a dfa based on the input
            DFA dfa = new DFA(arrayListOfStates,arrayListOfAlphabet, arrayListOfTransitions, startState, arrayListOfAcceptStates);

            //Transform the dfa to regex and display results
            dfa_regex_expression.setText(dfa.toRegex());

        }else if(view == regex_submit){
            //Get input and create a regex object
            String regex_input = regex_input_expression.getText().toString().trim().toLowerCase();
            REGEX regex = new REGEX(regex_input);

            //Transform the regex into a nfa
            NFA nfa = regex.toNFA();

            //Strings for the data from the nfa
            String nfa_states = "";
            String nfa_alphabet = "";
            String nfa_transitions = "";
            String nfa_accept_states = "";

            //The data from the nfa
            ArrayList<String> states = nfa.getStates();
            ArrayList<String> alphabet = nfa.getAlphabet();
            ArrayList<Transition> transitions = nfa.getTransitions();
            String startState = nfa.getStartState();
            ArrayList<String> acceptStates = nfa.getAcceptingStates();

            //Make all the arraylists into strings
            for(int i = 0; i < states.size(); i++){
                nfa_states = nfa_states + " " + states.get(i);
            }

            for(int i = 0; i < alphabet.size(); i++){
                nfa_alphabet = nfa_alphabet + " " + alphabet.get(i);
            }

            for(int i = 0; i < transitions.size(); i++){
                nfa_transitions = nfa_transitions + transitions.get(i).displayTransistion();
            }

            for(int i = 0; i < acceptStates.size(); i++){
                nfa_accept_states = nfa_accept_states + " " + acceptStates.get(i);
            }

            //Send out the results
            regex_nfa_states.setText(nfa_states);
            regex_nfa_alphabet.setText(nfa_alphabet);
            regex_nfa_transitions.setText(nfa_transitions);
            regex_nfa_start_state.setText(startState);
            regex_nfa_accept_states.setText(nfa_accept_states);
        }else if(view == dfa_test_case_1){
            dfa_input_states.setText("q0,q1,q2");
            dfa_input_aphabet.setText("0,1");
            dfa_input_transitions.setText("(q0,q1,0)(q0,q2,1)(q1,q0,0)(q1,q2,1)(q2,q0,1)(q2,q1,0)");
            dfa_input_start_state.setText("q0");
            dfa_input_accept_states.setText("q1,q2");
        }else if(view == dfa_test_case_2){
            dfa_input_states.setText("q0,q1,q2");
            dfa_input_aphabet.setText("a,b");
            dfa_input_transitions.setText("(q0,q1,b)(q0,q0,a)(q1,q1,a)(q1,q2,b)(q2,q2,a)(q2,q2,b)");
            dfa_input_start_state.setText("q0");
            dfa_input_accept_states.setText("q1");
        }else if(view == dfa_test_case_3){
            dfa_input_states.setText("s,t");
            dfa_input_aphabet.setText("a,b");
            dfa_input_transitions.setText("(s,t,a)(s,s,b)(t,t,b)(t,s,a)");
            dfa_input_start_state.setText("s");
            dfa_input_accept_states.setText("s");
        }else if(view == dfa_test_case_4){
            dfa_input_states.setText("q2,q3,q1");
            dfa_input_aphabet.setText("a,b");
            dfa_input_transitions.setText("(q1,q2,a)(q1,q2,b)(q2,q2,a)(q2,q3,b)(q3,q1,b)(q3,q2,a)");
            dfa_input_start_state.setText("q1");
            dfa_input_accept_states.setText("q1,q3");
        }else if(view == regex_test_case_1){
            regex_input_expression.setText("((aub).(a)*.(b)*)");
        }else if(view == regex_test_case_2){
            regex_input_expression.setText("(a.b.c.d.e.f.g)");
        }else if(view == regex_test_case_3){
            regex_input_expression.setText("((b)*.(a)*.(a)*)");
        }else if(view == regex_test_case_4){
            regex_input_expression.setText("((a.d.b)ud)");
        }
    }
}
