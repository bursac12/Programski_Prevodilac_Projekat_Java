package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Greska, argumenti moraju imati oblik: <input_file_name> <output_file_name>");
			return;
		}

		File sourceCode = new File(args[0]);
		if (!sourceCode.exists()) {
			System.out.println("Fajl " + sourceCode.getAbsolutePath() + " ne postoji!");
			return;
		}

		Logger log = Logger.getLogger(SemanticAnalyzer.class);

		log.info("Compiling source file: " + sourceCode.getAbsolutePath());

		try (BufferedReader br = new BufferedReader(new FileReader(sourceCode))) {
			// sourceCode = new File("test/prg1.mj");

			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);

			Symbol s = p.parse();
			Program prog = (Program) (s.value);
			// ispis sintaksnog stabla
		//	 System.out.println(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			Tab.init();
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v);
			Tab.dump();

			if (!p.errorDetected && v.passed()) {
				// code generator
				
				File objFile = new File(args[1]);

				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));

				log.info("Parsiranje uspesno zavrseno!");

			} else {
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
