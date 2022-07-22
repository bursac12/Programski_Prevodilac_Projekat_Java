package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	public static final Struct boolType = new Struct(Struct.Bool);

	Logger log = Logger.getLogger(getClass());

	private Struct lastVisitedType;
	private Struct methType;
	private boolean hasMinus;
	private int desKind;
	private boolean isDesignatorMethod;

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	private static Obj findInCurrentScope(String name) {
		Obj ret;
		if (Tab.currentScope().getLocals() == null)
			ret = Tab.noObj;
		else {
			ret = Tab.currentScope().getLocals().searchKey(name);
			if (ret == null)
				ret = Tab.noObj;
		}
		return ret;
	}

	private String getType() {
		if (lastVisitedType == Tab.intType)
			return "Int";
		else if (lastVisitedType == Tab.charType)
			return "Char";
		else if (lastVisitedType == Tab.find("bool").getType())
			return "Bool";

		else
			return "Nepoznat tip!!! ";
	}

	public void visit(Program program) {
		Obj func = Tab.currentScope.findSymbol("main");
		if (func != null) {
			if (func.getKind() != Obj.Meth) {
				report_error("Greska!!! Simbol main nije metoda", program);
			} else if (func.getType() != Tab.noType) {
				report_error("Greska!!! main mora biti tipa void", program);
			}
		} else {
			report_error("Greska!!! Ne postoji main metoda", program);
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		if (Tab.currentScope().findSymbol(progName.getProgName()) == null)
			progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		else {
			progName.obj = Tab.noObj;
			report_error("Simbol sa imenom " + progName.getProgName() + " vec postoji u trenutnom scope-u!", progName);
		}
		Tab.openScope();
	}

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": metoda " + currentMethod.getName()
					+ " nema return iskaz!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
	}

	public void visit(MethodTypeName methodTypeName) {
		Struct type = Tab.noType;
		if (methodTypeName.getMethodTypeSelect() instanceof MethodTypeSelectType) {
			MethodTypeSelectType methodTypeSelectType = (MethodTypeSelectType) methodTypeName.getMethodTypeSelect();
			type = methodTypeSelectType.getType().struct;
		}
		if (Tab.currentScope().findSymbol(methodTypeName.getMethName()) != null) {
			report_error(
					"Greska!!! Metoda sa imenom " + methodTypeName.getMethName() + " vec postoji u trenutnom scope-u!",
					null);
			currentMethod = Tab.noObj;
		} else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), type);
			methodTypeName.obj = currentMethod;
		}
		Tab.openScope();
	}

	public void visit(MethodTypeSelectVoid type) {
		lastVisitedType = Tab.noType;
		methType = Tab.noType;
	}

	public void visit(MethodTypeSelectType type) {

		methType = type.getType().struct;
	}

	public void visit(ConstDeclarationItem constDecl) {
		String constName = constDecl.getConstantName().getCName();
		if (findInCurrentScope(constName) != Tab.noObj) {
			report_error("Greska! Konstanta je vec definisana " + constName, constDecl);
		} else

		if (constDecl.getConstDeclItemVal() instanceof ConstDeclItemValNum) {
			if (lastVisitedType != Tab.intType) {
				report_error("Pokusaj dodele vrednosti konstanti tipa " + getType() + " vrednost tipa int", constDecl);
			} else {
				Obj newConst = Tab.insert(Obj.Con, constName, lastVisitedType);
				ConstDeclItemValNum vr = (ConstDeclItemValNum) constDecl.getConstDeclItemVal();
				newConst.setAdr(vr.getConstVal());
			}
		} else

		if (constDecl.getConstDeclItemVal() instanceof ConstDeclItemValChar) {
			if (lastVisitedType != Tab.charType) {
				report_error("Pokusaj dodele vrednosti konstanti tipa " + getType() + " vrednost tipa char", constDecl);
			} else {
				Obj newConst = Tab.insert(Obj.Con, constName, lastVisitedType);
				ConstDeclItemValChar vr = (ConstDeclItemValChar) constDecl.getConstDeclItemVal();
				newConst.setAdr(vr.getConstVal());
			}
		} else

		if (constDecl.getConstDeclItemVal() instanceof ConstDeclItemValBool) {
			if (lastVisitedType != boolType) {
				report_error("Pokusaj dodele vrednosti konstanti tipa " + getType() + " vrednost tipa bool", constDecl);
			} else {
				Obj newConst = Tab.insert(Obj.Con, constName, lastVisitedType);
				ConstDeclItemValBool vr = (ConstDeclItemValBool) constDecl.getConstDeclItemVal();

				newConst.setAdr(vr.getConstVal() ? 1 : 0);
			}
		}
	}

	public void visit(VarDeclItemArray varDecl) {
		Obj myObj = findInCurrentScope(varDecl.getVarName());
		if (myObj != Tab.noObj) {
			report_error("Greska!!! Deklarisani niz " + varDecl.getVarName() + " vec postoji.", varDecl);
		} else {
			Struct type = new Struct(Struct.Array, lastVisitedType);
			Tab.insert(Obj.Var, varDecl.getVarName(), type);
		}
	}

	public void visit(VarDeclItemRegular varDeclItemRegular) {
		Obj myObj = findInCurrentScope(varDeclItemRegular.getVarName());
		if (myObj != Tab.noObj) {
			report_error("Greska!!! Deklarisana promenljiva " + varDeclItemRegular.getVarName() + " vec postoji.",
					varDeclItemRegular);
		} else {
			Tab.insert(Obj.Var, varDeclItemRegular.getVarName(), lastVisitedType);
		}
	}

	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj == Tab.noObj) {
			report_error("Greska!!! Ne postoji tip sa imenom " + type.getTypeName() + " u tabeli simbola.", type);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeObj.getKind()) {
				type.struct = typeObj.getType();
			} else {
				report_error("Greska!!! Simbol sa imenom " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
		lastVisitedType = type.struct;
	}

	public void visit(MatchedReturnStmt matchedReturnStmt) {
		returnFound = true;
		// report_error("Tip metode: " + methType + " tip promenjlive " +
		// matchedReturnStmt.getExpr().struct, matchedReturnStmt.getParent());
		if (matchedReturnStmt.getExpr().struct != methType && methType != Tab.noType)
			report_error("Greska!!! Return tip se razlikuje od tipa metode", matchedReturnStmt.getParent());
		else if (methType == Tab.noType && !matchedReturnStmt.getExpr().equals(null)) {
			report_error("Greska!!! Void metoda ne moze da vraca vrednost", matchedReturnStmt.getParent());
		}
	}

	public void visit(MatchedReturnStmtNoExpr matchedNOReturnStmt) {
		if (methType != Tab.noType)
			report_error("Greska!!! Ako metoda nema povratnu vrednost, mora biti void",
					matchedNOReturnStmt.getParent());
	}

	public void visit(MatchedReadStmt rdStmt) {
		if (rdStmt.getDesignator().obj.getType() != Tab.intType && rdStmt.getDesignator().obj.getType() != Tab.charType
				&& rdStmt.getDesignator().obj.getType() != boolType)

			report_error("Greska!!! Read metoda moze raditi samo sa int, bool ili char tipom", rdStmt);
		if (rdStmt.getDesignator().obj.getKind() != Obj.Var && rdStmt.getDesignator().obj.getKind() != Obj.Elem
				&& rdStmt.getDesignator().obj.getKind() != Obj.Fld) {
			report_error("Greska!!! Designator " + rdStmt.getDesignator().obj.getName()
					+ " nije promenljiva, element niza ili polje unutar objekta", rdStmt);
		}
	}

	public void visit(MatchedPrintStmt prtStmt) {
		if (prtStmt.getExpr().struct != Tab.intType && prtStmt.getExpr().struct != Tab.charType
				&& prtStmt.getExpr().struct != boolType) {
			report_error("AAAAreska!!! " + prtStmt.getExpr().struct + " ", prtStmt.getParent());
			report_error("Greska!!! Print metoda moze raditi samo sa int, bool ili char tipom", prtStmt.getParent());
		}
	}

	public void visit(ExprSolo expr) {
		expr.struct = expr.getExpr1().struct;
	}

	public void visit(ExprsList expr) {
		// report_error(termType + " !! AAAA "+expr.getExpr1().struct ,
		// expr.getParent().getParent());
		if (expr.getTerm().struct != Tab.intType || expr.getExpr1().struct != Tab.intType)
			report_error("Greska!!! Pokusaj primene aritmeticke operacije nad nenumerickim tipovima podataka",
					expr.getParent().getParent().getParent());
		expr.struct = Tab.intType;
	}

	public void visit(Expression expr) {
		expr.struct = expr.getTerm().struct;
		if (hasMinus && expr.struct != Tab.intType) {
			report_error("Greska!!! Operator '-' se mora koristiti sa numerickim tipom", expr.getParent());
		}
		hasMinus = false;
	}

	public void visit(MinusOption mOpt) {
		hasMinus = true;
	}

	public void visit(FormalParamDeclRegular forPars) {
		if (findInCurrentScope(forPars.getParamName()) != Tab.noObj) {
			report_error("Greska!!! Formalni parametar je vec definisan " + forPars.getParamName(), forPars);
		} else {
			Tab.insert(Obj.Fld, forPars.getParamName(), forPars.getType().struct);
			Tab.insert(Obj.Fld, "this", forPars.getType().struct);
		}
	}

	public void visit(FormalParamDeclArray forPars) {
		if (findInCurrentScope(forPars.getParamName()) != Tab.noObj) {
			report_error("Greska!!! Formalni parametar je vec definisan " + forPars.getParamName(), forPars);
		} else {
			Struct myType = new Struct(Struct.Array, forPars.getType().struct);
			Tab.insert(Obj.Fld, forPars.getParamName(), myType);
		}
	}

	public void visit(DesignatorDecl designator) {
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska!!! Designator " + designator.getName() + " nije deklarisan! na liniji: "
					+ designator.getLine(), null);
		} else {
			// obj.setFpPos(-1);
			// detekcija koriscenja promenljive, tj detalji iz tabele simbola da se ispisu
			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(obj);
			if (obj.getKind() == Obj.Var && obj.getLevel() == 0) {
				report_info("Detektovano koriscenje globalne promenljive " + dump.getOutput(), designator);
			} else if (obj.getKind() == Obj.Con) {
				report_info("Detektovano koriscenje konstante " + dump.getOutput(), designator);
			} else if (obj.getKind() == Obj.Var && obj.getLevel() > 0) {
				report_info("Detektovano koriscenje lokalne promenljive " + dump.getOutput(), designator);
			}
		}
		designator.obj = obj;
		isDesignatorMethod = false;
		if (!isDesignatorMethod) {
			desKind = obj.getKind();
		}
	}

	public void visit(DesignatorListItemExpr designatorArray) {
		Obj parentObj = designatorArray.getDesignator().obj;
		designatorArray.obj = parentObj;
		if (designatorArray.getExpr().struct != Tab.intType) {
			report_error("Pokusaj pristupa nizu sa nenumerickom vrednoscu", designatorArray);
		}

		if (parentObj.getType().getKind() != Struct.Array) {
			report_error("Pokusaj indeksiranja varijable koja nije niz", designatorArray);
		} else {
			Obj obj = new Obj(Obj.Elem, "elem", parentObj.getType().getElemType());
			designatorArray.obj = obj;
			DumpSymbolTableVisitor objPrinter = new DumpSymbolTableVisitor();
			objPrinter.visitObjNode(obj);
			DumpSymbolTableVisitor dump = new DumpSymbolTableVisitor();
			dump.visitObjNode(designatorArray.getDesignator().obj);
//			report_info("Pristup nizu " + designatorArray.getDesignator().obj.getName() , designatorArray);
		}
		isDesignatorMethod = false;
//		report_info("Tip niza " + parentObj.getType().getElemType() , designatorArray);			
	}

	public void visit(DesignatorListItemDot designatorCall) {
		Obj parentObj = designatorCall.getDesignator().obj;
		Obj obj = new Obj(Obj.Meth, "", parentObj.getType().getElemType());
		designatorCall.obj = obj;
		isDesignatorMethod = true;
		desKind = obj.getKind();
		designatorCall.obj = Tab.noObj;
	}

	public void visit(DesignatorStmtItemExpr desExpr) {
		Obj obj = desExpr.getDesignator().obj;

		if (!desExpr.getExpr().struct.assignableTo(obj.getType()))
			// report_error("Greska!!! Pogresan tip u designatoru " +
			// desExpr.getDesignator().obj.getName() +"
			// "+desExpr.getDesignator().obj.getType()+" "+exprType+" " +
			// desExpr.getExpr().struct , desExpr);
			report_error("Greska!!! Pogresan tip u designatoru " + obj.getName() + " ", desExpr);
		isDesignatorMethod = false;
		if (!(obj.getKind() == Obj.Fld || obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem)) {
			report_error("Greska!!! Designator " + desExpr.getDesignator().obj.getName()
					+ " nije promenljiva, element niza ili polje unutar objekta ", desExpr);
		}
	}

	public void visit(DesignatorStmtItemInc desStmt) {
		Obj obj = desStmt.getDesignator().obj;

		if (!(obj.getKind() == Obj.Fld || obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem)) {
			report_error("Greska!!! Designator " + desStmt.getDesignator().obj.getName()
					+ " nije promenljiva, element niza ili polje unutar objekta ", desStmt);
		}

		if (desStmt.getDesignator().obj.getType() != Tab.intType)
			report_error("Greska!!! Ne moze se inkrementirati nenumericka vrednost", desStmt);
	}

	public void visit(DesignatorStmtItemDec desStmt) {
		Obj obj = desStmt.getDesignator().obj;
		if (!(obj.getKind() == Obj.Fld || obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem)) {
			report_error("Greska!!! Designator " + desStmt.getDesignator().obj.getName()
					+ " nije promenljiva, element niza ili polje unutar objekta ", desStmt);
		}
		if (desStmt.getDesignator().obj.getType() != Tab.intType)
			report_error("Greska!!! Ne moze se dekrementirati nenumericka vrednost", desStmt);
	}

	public void visit(FactorArrExp fact) {
		fact.struct = Tab.intType;
		Obj obj = fact.getDesignator().obj;

		if (!obj.getType().equals(new Struct(Struct.Array, Tab.intType))) {
			report_error("Greska!!! Designator nije niz celobrojnih vrednosti", fact);
			fact.struct = Tab.noType;
		}
	}

	public void visit(FactorPom fact) {
		fact.struct = Tab.charType;
		if (fact.getExpr().struct != Tab.charType) {
			report_error("Greska!!! Prvi operand mora biti tipa char ", fact);
			fact.struct = Tab.noType;
		}
		if (fact.getExpr1().struct != Tab.intType) {
			report_error("Greska!!! Drugi operand mora biti tipa int ", fact);
			fact.struct = Tab.noType;
		}
	}

	public void visit(FactorMax fact) {
		fact.struct = fact.getExpr().struct;
		if (!fact.getExpr().struct.equals(fact.getExpr1().struct)
				|| !fact.getExpr().struct.equals(fact.getExpr2().struct)) {
			report_error("Greska!!! Operandi moraju biti istog tipa ", fact);
			fact.struct = Tab.noType;
		}

	}

	public void visit(FuncCall designator) {
		designator.struct = designator.getDesignator().obj.getType();
	}

	public void visit(FuncCallNoPars designator) {
		designator.struct = designator.getDesignator().obj.getType();
		desKind = Obj.Meth;
	}

	public void visit(FuncCallActPars designator) {
		designator.struct = designator.getDesignator().obj.getType();
		desKind = Obj.Meth;
	}

	public void visit(DesignatorStmtItemActPars designator) {
		if (desKind != Obj.Meth)
			report_error("Pokusaj poziva izraza koji nije metoda", designator.getParent());
	}

	public void visit(DesignatorStmt designator) {
		desKind = -1;
	}

	public void visit(Terms term) {
		term.struct = term.getFactor().struct;
	}

	public void visit(TermsList term) {
		if (term.getTerm().struct != Tab.intType || term.getFactor().struct != Tab.intType) {
			report_error("Greska!!! Operandi moraju biti numerickog tipa", term);
		}
		term.struct = Tab.intType;
	}

	public void visit(FactorExpr expr) {
		expr.struct = expr.getExpr().struct;
	}

	public void visit(FactorNumConst factorNum) {
		factorNum.struct = Tab.intType;
	}

	public void visit(FactorBoolConst factorBool) {
		factorBool.struct = boolType;
	}

	public void visit(FactorCharConst factorChar) {
		factorChar.struct = Tab.charType;
	}

	public void visit(FactorTypeArray newFact) {
		if (newFact.getExpr().struct != Tab.intType) {
			report_error("Greska!!! Expr u zagradama mora biti tipa int", newFact);
		}
		newFact.struct = new Struct(Struct.Array, newFact.getType().struct);
	}

	public void visit(ExprTern expr) {
		Struct e1 = expr.getExpr11().struct;
		Struct e2 = expr.getExpr12().struct;
		if (!e1.equals(e2)) {
			expr.struct = Tab.noType;
			report_error("Greska!!! Razliciti tipovi u drugom i trecem izrazu ", expr);
		} else if (!expr.getExpr1().struct.equals(boolType)) {
			expr.struct = Tab.noType;
			report_error("Greska!!! Prvi izraz nije boolean tipa ", expr);
		}
		expr.struct = e1;
	}

	public void visit(GreskaSemi gr) {
		report_info("Uspesan oporavak od sintaksne greske prilikom deklaracije promenljive do ; ", gr);
	}

	public void visit(GreskaZarez gr) {
		report_info("Uspesan oporavak od sintaksne greske prilikom deklaracije promenljive do , ", gr);
	}

	public boolean passed() {
		return !errorDetected;
	}

}
