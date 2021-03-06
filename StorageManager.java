package meatbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private HashMap<String, ResultValue> variables = null;
    private Parser parser;

    /**
     * <p>StorageManager is responsible for holding declared variables and their value</p>
     */
    public StorageManager(Parser parser) {
        this.variables = new HashMap<String, ResultValue>();
        this.parser = parser;
    }

    /**
     * <p>Add a variable to the global hashmap</p>
     * @param variable  The variable name used as a key for the hashmap
     * @param value     The value the variable will hold (null for undeclared)
     * @throws Exception
     */
    public void addVariable(String variable, ResultValue value) throws Exception {
        if (variables.containsKey(variable)) {
            parser.error("Variable '" + variable + "' has already been instantiated");
        }
        variables.put(variable, value);
    }

    /**
     * <p>Updated a variable's value in the global hashmap</p>
     * @param variable      The name of the variable to be updated
     * @param value         The value to update the variable with
     * @throws Exception
     */
    public void updateVariable(String variable, ResultValue value) throws Exception {
        if (!variables.containsKey(variable)) {
            try {
                addVariable(variable, value);
            } catch (Exception e) {
                parser.error("Variable '" + variable + "' has already been instantiated");
            }
        }
        variables.put(variable, value);
    }


    /**
     * <p>Return a variable's value from the global hashmap</p>
     * @param variable      The variable to be grabbed from the global hashmap
     * @return     The ResultValue that corresponds to the variable being asked for
     * @throws Exception Rethrows whatever it is handed
     */
    public ResultValue getVariableValue(String variable) throws Exception {

        //Initialize an empty ResultValue
        ResultValue rv = new ResultValue();

        // test to see if the variable is just an undeclared integer
        try {
            Integer.parseInt(variable);

            //if it is just an integer, set up rv as int
            rv.type = SubClassif.INTEGER;
            rv.value = variable;
            rv.structure = "primitive";
        } catch (Exception e) {

            // test to see if the variable is just a straight up float
            try {
                Float.parseFloat(variable);

                //setup float rv
                rv.type = SubClassif.FLOAT;
                rv.value = variable;
                rv.structure = "primitive";

            } catch (Exception f) {
                // if the variable passed through "variable" is not found in the SM
                if (!variables.containsKey(variable)) {
                    parser.error("Error: Variable '" + variable + "' does not exist");
                } else {
                    // TODO: Delete this later
                    rv = variables.get(variable);
                }
            }
        }

        return rv;
    }

    public ResultValue getVariableValue(Token token) throws Exception{
        ResultValue rv = new ResultValue();

        // test to see if the variable is a constant
        if(token.subClassif != SubClassif.IDENTIFIER){
            //return basically the token but instead ResultValue
            rv.value = token.tokenStr;
            rv.structure = "primitive";
            rv.type = token.subClassif;
        }
        else
            if(!variables.containsKey(token.tokenStr)) {
                parser.error("Error: Variable '" + token.tokenStr + "' does not exist");
            } else {
                rv = variables.get(token.tokenStr);
            }
        return rv;
    }

    /**
     * <p>Return the requested variable's value in unary minus form</p>
     * @param variable      The variable requested from the global hashmap
     * @return              The ResultValue in unary minus form
     * @throws Exception    Rethrows whatever is handed to it
     */
    public ResultValue getUnaryVariableValue(String variable) throws Exception {

        ResultValue rv = getVariableValue(variable);
        rv.value = "-" + rv.value;
        return rv;

/*
        if (!variables.containsKey(variable)){
            throw new Exception("Error: Variable '" + variable + "' does not exists");
        }
        ResultValue rv = variables.get(variable);
        rv.value = "-" + rv.value;
        variables.put(variable, rv);
        return rv;
  */
    }
}