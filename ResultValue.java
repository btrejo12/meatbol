package meatbol;

public class ResultValue {
    public String type; // Data type of this ResultValue
    public String value; // Value of this ResultValue
    public String structure; // Structure of this ResultValue (fixed array, primitive, etc)
    public String terminatingStr; // Terminating Statement (endwhile, endfor, etc)

    public ResultValue() {}
}