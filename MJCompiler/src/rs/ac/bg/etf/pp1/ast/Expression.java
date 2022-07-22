// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class Expression extends Expr1 {

    private MinusOpt MinusOpt;
    private Term Term;

    public Expression (MinusOpt MinusOpt, Term Term) {
        this.MinusOpt=MinusOpt;
        if(MinusOpt!=null) MinusOpt.setParent(this);
        this.Term=Term;
        if(Term!=null) Term.setParent(this);
    }

    public MinusOpt getMinusOpt() {
        return MinusOpt;
    }

    public void setMinusOpt(MinusOpt MinusOpt) {
        this.MinusOpt=MinusOpt;
    }

    public Term getTerm() {
        return Term;
    }

    public void setTerm(Term Term) {
        this.Term=Term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MinusOpt!=null) MinusOpt.accept(visitor);
        if(Term!=null) Term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MinusOpt!=null) MinusOpt.traverseTopDown(visitor);
        if(Term!=null) Term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MinusOpt!=null) MinusOpt.traverseBottomUp(visitor);
        if(Term!=null) Term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expression(\n");

        if(MinusOpt!=null)
            buffer.append(MinusOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Term!=null)
            buffer.append(Term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expression]");
        return buffer.toString();
    }
}
