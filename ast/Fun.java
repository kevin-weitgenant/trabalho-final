package ast;

import java.util.ArrayList;

public class Fun {
	public String nome;
	public ArrayList<ParamFormalFun> params;
	public String retorno;
	public ArrayList<VarDecl> vars;
	public ArrayList<Comando> body;

	public Fun(String nome, ArrayList<ParamFormalFun> params, String retorno, ArrayList<VarDecl> vars,
			ArrayList<Comando> body) {
		this.nome = nome;
		this.params = params;
		this.retorno = retorno;
		this.vars = vars;
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		// Function signature
		result.append(retorno).append(" ").append(nome).append("(");
		if (params != null && !params.isEmpty()) {
			result.append(params.get(0));
			for (int i = 1; i < params.size(); i++) {
				result.append(", ").append(params.get(i));
			}
		}
		result.append(") {\n");

		// Variable declarations
		for (VarDecl var : vars) {
			result.append("    ").append(var).append(";\n");
		}

		// Body of the function
		for (Comando comando : body) {
			result.append("    ").append(comando.toString().replace("\n", "\n    ")).append("\n");
		}

		result.append("}\n");

		return result.toString();
	}
}
