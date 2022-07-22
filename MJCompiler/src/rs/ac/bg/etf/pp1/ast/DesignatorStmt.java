// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStmt extends DesignatorStatement {

    private DesignatorStatementItem DesignatorStatementItem;

    public DesignatorStmt (DesignatorStatementItem DesignatorStatementItem) {
        this.DesignatorStatementItem=DesignatorStatementItem;
        if(DesignatorStatementItem!=null) DesignatorStatementItem.setParent(this);
    }

    public DesignatorStatementItem getDesignatorStatementItem() {
        return DesignatorStatementItem;
    }

    public void setDesignatorStatementItem(DesignatorStatementItem DesignatorStatementItem) {
        this.DesignatorStatementItem=DesignatorStatementItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatementItem!=null) DesignatorStatementItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatementItem!=null) DesignatorStatementItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatementItem!=null) DesignatorStatementItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStmt(\n");

        if(DesignatorStatementItem!=null)
            buffer.append(DesignatorStatementItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStmt]");
        return buffer.toString();
    }
}
