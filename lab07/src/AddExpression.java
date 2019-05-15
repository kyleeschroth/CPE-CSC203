//class AddExpression implements Expression

class AddExpression extends BinaryExpression
{
    //private final Expression lft;
    //private final Expression rht;



    public AddExpression(final Expression lft, final Expression rht)
    {
        //this.lft = lft;
        //this.rht = rht;
        super(lft, rht);
    }

    /*
    public String toString()
    {
        return "(" + lft + " + " + rht + ")";
    }

    public double evaluate(final Bindings bindings)
    {
        return lft.evaluate(bindings) + rht.evaluate(bindings);
    }
    */

    @Override
    public String getOperatorName(){
        return "+"; 
    }

    @Override
    protected double applyOperator(double d1, double d2){
        return d1 + d2; 
    }
}
