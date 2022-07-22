// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class MethodTypeName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private MethodTypeSelect MethodTypeSelect;
    private String methName;

    public MethodTypeName (MethodTypeSelect MethodTypeSelect, String methName) {
        this.MethodTypeSelect=MethodTypeSelect;
        if(MethodTypeSelect!=null) MethodTypeSelect.setParent(this);
        this.methName=methName;
    }

    public MethodTypeSelect getMethodTypeSelect() {
        return MethodTypeSelect;
    }

    public void setMethodTypeSelect(MethodTypeSelect MethodTypeSelect) {
        this.MethodTypeSelect=MethodTypeSelect;
    }

    public String getMethName() {
        return methName;
    }

    public void setMethName(String methName) {
        this.methName=methName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodTypeSelect!=null) MethodTypeSelect.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeSelect!=null) MethodTypeSelect.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeSelect!=null) MethodTypeSelect.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodTypeName(\n");

        if(MethodTypeSelect!=null)
            buffer.append(MethodTypeSelect.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+methName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodTypeName]");
        return buffer.toString();
    }
}
