// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationItem extends ConstDeclItem {

    private ConstantName ConstantName;
    private ConstDeclItemVal ConstDeclItemVal;

    public ConstDeclarationItem (ConstantName ConstantName, ConstDeclItemVal ConstDeclItemVal) {
        this.ConstantName=ConstantName;
        if(ConstantName!=null) ConstantName.setParent(this);
        this.ConstDeclItemVal=ConstDeclItemVal;
        if(ConstDeclItemVal!=null) ConstDeclItemVal.setParent(this);
    }

    public ConstantName getConstantName() {
        return ConstantName;
    }

    public void setConstantName(ConstantName ConstantName) {
        this.ConstantName=ConstantName;
    }

    public ConstDeclItemVal getConstDeclItemVal() {
        return ConstDeclItemVal;
    }

    public void setConstDeclItemVal(ConstDeclItemVal ConstDeclItemVal) {
        this.ConstDeclItemVal=ConstDeclItemVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstantName!=null) ConstantName.accept(visitor);
        if(ConstDeclItemVal!=null) ConstDeclItemVal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstantName!=null) ConstantName.traverseTopDown(visitor);
        if(ConstDeclItemVal!=null) ConstDeclItemVal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstantName!=null) ConstantName.traverseBottomUp(visitor);
        if(ConstDeclItemVal!=null) ConstDeclItemVal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationItem(\n");

        if(ConstantName!=null)
            buffer.append(ConstantName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclItemVal!=null)
            buffer.append(ConstDeclItemVal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationItem]");
        return buffer.toString();
    }
}
