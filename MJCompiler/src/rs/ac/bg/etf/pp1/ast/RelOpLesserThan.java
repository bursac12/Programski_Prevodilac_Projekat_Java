// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class RelOpLesserThan extends Relop {

    public RelOpLesserThan () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelOpLesserThan(\n");

        buffer.append(tab);
        buffer.append(") [RelOpLesserThan]");
        return buffer.toString();
    }
}
