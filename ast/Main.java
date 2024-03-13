package ast;

import java.util.ArrayList;

public class Main {
	public ArrayList<VarDecl> vars;
	public ArrayList<Comando> coms;

	public Main(ArrayList<VarDecl> vars, ArrayList<Comando> coms) {
		this.vars = vars;
		this.coms = coms;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		// Variable declarations
		for (VarDecl var : vars) {
			result.append(var).append(";\n");
		}

		// Main commands
		for (Comando comando : coms) {
			result.append(comando.toString().replace("\n", "\n    ")).append("\n");
		}

		return result.toString();
	}
}
