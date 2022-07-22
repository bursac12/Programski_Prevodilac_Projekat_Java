package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	private int jumpElse;
	private int jumpThen;
	
	
	public int getMainPc() {
		return mainPc;
	}
	
	
	public void visit(MethodTypeName MethodTypeName) {		 
		MethodTypeName.obj.setAdr(Code.pc);
		if ("main".equals(MethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(0);
		Code.put(MethodTypeName.obj.getLocalSymbols().size());			
	}
	
	
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public void visit(MatchedReturnStmt ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public void visit(MatchedReturnStmtNoExpr ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}	
	
	
	public void visit(MatchedPrintStmt printStat) {
		if (printStat.getNumConstOpt() instanceof NumConstOption) {
			int length = ((NumConstOption) printStat.getNumConstOpt()).getLen();
			Code.loadConst(length);
		} else {
			Code.loadConst(1);
		}
		if (printStat.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}		
	}	
	

	public void visit(MatchedReadStmt readStmt) {
		Obj obj = readStmt.getDesignator().obj;
		if (obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(obj);
	}
	
	
	public void visit(FactorNumConst fact) {
		Code.loadConst(fact.getConst());
	}

	
	public void visit(FactorCharConst fact) {
		Code.loadConst(fact.getConst());
	}

	
	public void visit(FactorBoolConst fact) {
		Code.loadConst(fact.getConst() ? 1 : 0);
	}

	
	public void visit(DesignatorStmtItemInc desigInc) {
		Obj obj = desigInc.getDesignator().obj;
		if(obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(obj);
	}
	
	
	public void visit(DesignatorStmtItemDec desigDec) {
		Obj obj = desigDec.getDesignator().obj;
		if(obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(obj);
	}	
	
	
	public void visit(DesignatorStmtItemKva desigKva) {
		Obj obj = desigKva.getDesignator().obj;
		if(obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(obj);
		Code.load(obj);
		Code.put(Code.mul);
		Code.store(obj);
	}
	
	public void visit(FactorMax fact) {
		Code.put(Code.enter);
		Code.put(3);
		Code.put(4);
		
		Code.put(Code.load_n);
		Code.put(Code.load_1);
		Code.putFalseJump(Code.gt, 0);
		int afterThen=Code.pc-2;		
	
		Code.put(Code.load_n);
		Code.put(Code.store_3);
		Code.putJump(0);
		int afterElse=Code.pc-2;
		
		Code.fixup(afterThen);
		Code.put(Code.load_1);
		Code.put(Code.store_3);
		
		Code.fixup(afterElse);
		
		Code.put(Code.load_2);
		Code.put(Code.load_3);
		Code.putFalseJump(Code.gt, 0);
		afterThen=Code.pc-2;
		
		Code.put(Code.load_2);
		Code.put(Code.store_3);
		
		Code.fixup(afterThen);

		Code.put(Code.load_3);
		
		Code.put(Code.exit);		
		
	}
	
	
	public void visit(DesignatorStmtItemExpr Assignment) {		
		Obj o = Assignment.getDesignator().obj;
		Code.store(o);		
	}
	
	
    public void visit(ArrayLoadMarker arrayLoadMarker){
    	DesignatorListItemExpr parent = (DesignatorListItemExpr) arrayLoadMarker.getParent();
        Code.load(parent.getDesignator().obj);
    }
	
	
	public void visit(FuncCall FuncCall) {
		Code.load(FuncCall.getDesignator().obj);
	}
		

	public void visit(FactorArrExp fact) {	
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);
		
		Code.load(fact.getDesignator().obj);
		Code.put(Code.load_n);
		Code.put(Code.aload);
	 
		Code.load(fact.getDesignator().obj);
	 
		Code.put(Code.dup);
		Code.put(Code.arraylength);
		Code.put(Code.load_n);
		Code.put(Code.sub);
		Code.loadConst(1);
		Code.put(Code.sub);
	
		Code.put(Code.aload);
	 
		Code.put(Code.add);
		
		Code.put(Code.exit);
	}
		
	
	public void visit(FactorPom fact) { // 66 -2
//		Code.put(Code.add); // (((64 - 65) mod 26) + 26) mod 26 + 65
//		Code.loadConst('A'); // 64  65
//		Code.put(Code.sub); // -1
//		Code.loadConst(26);
//		Code.put(Code.rem);
//		Code.loadConst(26);
//		Code.put(Code.add);
//		Code.loadConst(26);
//		Code.put(Code.rem);
//		Code.loadConst('A');
//		Code.put(Code.add);
		
		Code.loadConst(26);
		Code.put(Code.rem);
		Code.put(Code.add);

		Code.put(Code.dup);
		Code.loadConst('A');
		Code.putFalseJump(Code.lt, 0);
		int afterThen= Code.pc-2;

		Code.loadConst(26);
		Code.put(Code.add);
		Code.putJump(0);
		int afterElse=Code.pc-2;

		Code.fixup(afterThen);

		Code.put(Code.dup);
		Code.loadConst('Z');
		Code.putFalseJump(Code.gt, 0);
		afterThen= Code.pc-2;

		Code.loadConst(26);
		Code.put(Code.sub);

		Code.fixup(afterThen);
		Code.fixup(afterElse);
	}	
	
	public void visit(ExprsList addExpr) {
		if (addExpr.getAddop() instanceof PlusOperation) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}

	}
	
	
	public void visit(TermsList mulopExpr) {
		if (mulopExpr.getMulop() instanceof MultiplyOperation) {
			Code.put(Code.mul);
		} else if (mulopExpr.getMulop() instanceof DivideOperation) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	
	public void visit(Expression expression) {
		if (expression.getMinusOpt() instanceof MinusOption)
		Code.put(Code.neg);
	}
	
	
	public void visit(FactorTypeArray arrayFact) {			
		Code.put(Code.newarray);
		if (arrayFact.getType().struct == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
		

	public void visit(Checkpoint1 pr) {
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		jumpThen = Code.pc-2;		
	}
	
	
	public void visit(Checkpoint2 pr) {	
		Code.putJump(0);
		jumpElse = Code.pc-2;		
		Code.fixup(jumpThen);			
	}	

	
	public void visit(ExprTern expr) {
		Code.fixup(jumpElse);
	}
	
	 
}