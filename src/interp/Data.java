package interp;

import parser.*;
import java.util.*;

public class Data
{
    public enum Type {VOID, BOOLEAN, INTEGER, ARR;}

    private Type type;
    private int value; 

    Data(int v) { type = Type.INTEGER; value = v; }
    Data(boolean b) { type = Type.BOOLEAN; value = b?1:0; }
//    Data(ArrayList<Integer> arr) {type = Type.ARR; value = arr; }

    Data() {type = Type.VOID; }
    Data(Data d) { type = d.type; value = d.value; }

    public Type getType() { return type; }

    public boolean isBoolean() { return type == Type.BOOLEAN; }
    public boolean isInteger() { return type == Type.INTEGER; }
    public boolean isArray() { return type == Type.ARR; }
    public boolean isVoid() { return type == Type.VOID; }

    public int getIntegerValue() 
	{
        assert type == Type.INTEGER;
        return value;
    }

    public boolean getBooleanValue() 
	{
        assert type == Type.BOOLEAN;
        return (value==0)?false:true;
    }

    public int getArrayValue(int idx)
    {
        assert type == Type.ARR;
        return value;
    }

    public void setValue(boolean b) { type = Type.BOOLEAN; value = b ? 1 : 0; }
    public void setValue(int v) { type = Type.INTEGER; value = v; }
    public void setData(Data d) { type = d.type; value = d.value; }

    public String toString() 
	{
        if (type == Type.BOOLEAN)
		{
 			return value == 1 ? "true" : "false";
		}

        return Integer.toString(value);
    }
    
    private void checkDivZero(Data d) 
	{
        if (d.value == 0) 
		{
			throw new RuntimeException ("Division by zero");
		}
    }

    /**
     * Evaluation of arithmetic expressions. The evaluation is done
     * "in place", returning the result on the same data.
     * @param op Type of operator (token).
     * @param d Second operand.
     */
     
    public void evaluateArithmetic (int op, Data d) 
	{
        assert type == Type.INTEGER && d.type == Type.INTEGER;
        switch (op) 
		{
            case AslLexer.PLUS: value += d.value; break;
            case AslLexer.MINUS: value -= d.value; break;
            case AslLexer.MUL: value *= d.value; break;
            case AslLexer.DIV: checkDivZero(d); value /= d.value; break;
            case AslLexer.MOD: checkDivZero(d); value %= d.value; break;
            default: assert false;
        }
    }

    /**
     * Evaluation of expressions with relational operators.
     * @param op Type of operator (token).
     * @param d Second operand.
     * @return A Boolean data with the value of the expression.
     */
    public Data evaluateRelational (int op, Data d) 
	{
        assert type != Type.VOID && type == d.type;
        switch (op) {
            case AslLexer.EQUAL: return new Data(value == d.value);
            case AslLexer.NOT_EQUAL: return new Data(value != d.value);
            case AslLexer.LT: return new Data(value < d.value);
            case AslLexer.LE: return new Data(value <= d.value);
            case AslLexer.GT: return new Data(value > d.value);
            case AslLexer.GE: return new Data(value >= d.value);
            default: assert false; 
        }
        return null;
    }
}
