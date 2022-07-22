// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class ConditionFact extends CondFact {

    private Expr Expr;
    private CondFactOpt CondFactOpt;

    public ConditionFact (Expr Expr, CondFactOpt CondFactOpt) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.CondFactOpt=CondFactOpt;
        if(CondFactOpt!=null) CondFactOpt.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public CondFactOpt getCondFactOpt() {
        return CondFactOpt;
    }

    public void setCondFactOpt(CondFactOpt CondFactOpt) {
        this.CondFactOpt=CondFactOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(CondFactOpt!=null) CondFactOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(CondFactOpt!=null) CondFactOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(CondFactOpt!=null) CondFactOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionFact(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFactOpt!=null)
            buffer.append(CondFactOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionFact]");
        return buffer.toString();
    }
}
