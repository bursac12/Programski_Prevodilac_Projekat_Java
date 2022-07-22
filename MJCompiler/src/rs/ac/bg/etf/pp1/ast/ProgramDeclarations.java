// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class ProgramDeclarations extends ProgramDeclList {

    private ProgramDeclList ProgramDeclList;
    private ProgramDeclOpt ProgramDeclOpt;

    public ProgramDeclarations (ProgramDeclList ProgramDeclList, ProgramDeclOpt ProgramDeclOpt) {
        this.ProgramDeclList=ProgramDeclList;
        if(ProgramDeclList!=null) ProgramDeclList.setParent(this);
        this.ProgramDeclOpt=ProgramDeclOpt;
        if(ProgramDeclOpt!=null) ProgramDeclOpt.setParent(this);
    }

    public ProgramDeclList getProgramDeclList() {
        return ProgramDeclList;
    }

    public void setProgramDeclList(ProgramDeclList ProgramDeclList) {
        this.ProgramDeclList=ProgramDeclList;
    }

    public ProgramDeclOpt getProgramDeclOpt() {
        return ProgramDeclOpt;
    }

    public void setProgramDeclOpt(ProgramDeclOpt ProgramDeclOpt) {
        this.ProgramDeclOpt=ProgramDeclOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramDeclList!=null) ProgramDeclList.accept(visitor);
        if(ProgramDeclOpt!=null) ProgramDeclOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramDeclList!=null) ProgramDeclList.traverseTopDown(visitor);
        if(ProgramDeclOpt!=null) ProgramDeclOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramDeclList!=null) ProgramDeclList.traverseBottomUp(visitor);
        if(ProgramDeclOpt!=null) ProgramDeclOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramDeclarations(\n");

        if(ProgramDeclList!=null)
            buffer.append(ProgramDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramDeclOpt!=null)
            buffer.append(ProgramDeclOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramDeclarations]");
        return buffer.toString();
    }
}
