//class SubtractExpression implements Expression

public class SubtractExpression extends BinaryExpression
{
    //private final Expression lft;
    //private final Expression rht;

    public SubtractExpression(final Expression lft, final Expression rht)
    {
        //this.lft = lft;
        //this.rht = rht;
        super(lft, rht); 
    }

    @Override
    public String getOperatorName(){
        return "-"; 
    }

    @Override
    protected double applyOperator(double d1, double d2){
        return d1 - d2; 
    }

    /*
    public String toString()
    {
        return "(" + lft + " - " + rht + ")";
    }

    public double evaluate(final Bindings bindings)
    {
        return lft.evaluate(bindings) - rht.evaluate(bindings);
    }
    */
}

