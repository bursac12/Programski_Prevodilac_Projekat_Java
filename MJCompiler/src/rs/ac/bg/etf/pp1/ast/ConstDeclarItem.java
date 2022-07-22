// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarItem extends ConstDeclList {

    private ConstDeclItem ConstDeclItem;

    public ConstDeclarItem (ConstDeclItem ConstDeclItem) {
        this.ConstDeclItem=ConstDeclItem;
        if(ConstDeclItem!=null) ConstDeclItem.setParent(this);
    }

    public ConstDeclItem getConstDeclItem() {
        return ConstDeclItem;
    }

    public void setConstDeclItem(ConstDeclItem ConstDeclItem) {
        this.ConstDeclItem=ConstDeclItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclItem!=null) ConstDeclItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclItem!=null) ConstDeclItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclItem!=null) ConstDeclItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarItem(\n");

        if(ConstDeclItem!=null)
            buffer.append(ConstDeclItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarItem]");
        return buffer.toString();
    }
}
