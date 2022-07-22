// generated with ast extension for cup
// version 0.8
// 18/0/2021 14:25:52


package rs.ac.bg.etf.pp1.ast;

public class NumConstOption extends NumConstOpt {

    private Integer len;

    public NumConstOption (Integer len) {
        this.len=len;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len=len;
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
        buffer.append("NumConstOption(\n");

        buffer.append(" "+tab+len);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstOption]");
        return buffer.toString();
    }
}
