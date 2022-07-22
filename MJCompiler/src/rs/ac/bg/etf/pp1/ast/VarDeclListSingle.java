// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListSingle extends VarDeclList {

    private VarDeclItem VarDeclItem;

    public VarDeclListSingle (VarDeclItem VarDeclItem) {
        this.VarDeclItem=VarDeclItem;
        if(VarDeclItem!=null) VarDeclItem.setParent(this);
    }

    public VarDeclItem getVarDeclItem() {
        return VarDeclItem;
    }

    public void setVarDeclItem(VarDeclItem VarDeclItem) {
        this.VarDeclItem=VarDeclItem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclItem!=null) VarDeclItem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclItem!=null) VarDeclItem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclItem!=null) VarDeclItem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListSingle(\n");

        if(VarDeclItem!=null)
            buffer.append(VarDeclItem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListSingle]");
        return buffer.toString();
    }
}
