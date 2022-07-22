// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class ExprTern extends Expr {

    private Expr1 Expr1;
    private Checkpoint1 Checkpoint1;
    private Expr1 Expr11;
    private Checkpoint2 Checkpoint2;
    private Expr1 Expr12;

    public ExprTern (Expr1 Expr1, Checkpoint1 Checkpoint1, Expr1 Expr11, Checkpoint2 Checkpoint2, Expr1 Expr12) {
        this.Expr1=Expr1;
        if(Expr1!=null) Expr1.setParent(this);
        this.Checkpoint1=Checkpoint1;
        if(Checkpoint1!=null) Checkpoint1.setParent(this);
        this.Expr11=Expr11;
        if(Expr11!=null) Expr11.setParent(this);
        this.Checkpoint2=Checkpoint2;
        if(Checkpoint2!=null) Checkpoint2.setParent(this);
        this.Expr12=Expr12;
        if(Expr12!=null) Expr12.setParent(this);
    }

    public Expr1 getExpr1() {
        return Expr1;
    }

    public void setExpr1(Expr1 Expr1) {
        this.Expr1=Expr1;
    }

    public Checkpoint1 getCheckpoint1() {
        return Checkpoint1;
    }

    public void setCheckpoint1(Checkpoint1 Checkpoint1) {
        this.Checkpoint1=Checkpoint1;
    }

    public Expr1 getExpr11() {
        return Expr11;
    }

    public void setExpr11(Expr1 Expr11) {
        this.Expr11=Expr11;
    }

    public Checkpoint2 getCheckpoint2() {
        return Checkpoint2;
    }

    public void setCheckpoint2(Checkpoint2 Checkpoint2) {
        this.Checkpoint2=Checkpoint2;
    }

    public Expr1 getExpr12() {
        return Expr12;
    }

    public void setExpr12(Expr1 Expr12) {
        this.Expr12=Expr12;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr1!=null) Expr1.accept(visitor);
        if(Checkpoint1!=null) Checkpoint1.accept(visitor);
        if(Expr11!=null) Expr11.accept(visitor);
        if(Checkpoint2!=null) Checkpoint2.accept(visitor);
        if(Expr12!=null) Expr12.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr1!=null) Expr1.traverseTopDown(visitor);
        if(Checkpoint1!=null) Checkpoint1.traverseTopDown(visitor);
        if(Expr11!=null) Expr11.traverseTopDown(visitor);
        if(Checkpoint2!=null) Checkpoint2.traverseTopDown(visitor);
        if(Expr12!=null) Expr12.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr1!=null) Expr1.traverseBottomUp(visitor);
        if(Checkpoint1!=null) Checkpoint1.traverseBottomUp(visitor);
        if(Expr11!=null) Expr11.traverseBottomUp(visitor);
        if(Checkpoint2!=null) Checkpoint2.traverseBottomUp(visitor);
        if(Expr12!=null) Expr12.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprTern(\n");

        if(Expr1!=null)
            buffer.append(Expr1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Checkpoint1!=null)
            buffer.append(Checkpoint1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr11!=null)
            buffer.append(Expr11.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Checkpoint2!=null)
            buffer.append(Checkpoint2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr12!=null)
            buffer.append(Expr12.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprTern]");
        return buffer.toString();
    }
}
