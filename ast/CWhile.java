package ast;

import java.util.ArrayList;

public class CWhile extends Comando {
	public int linha;
	public Exp exp;
	public ArrayList<Comando> bloco;

	public CWhile(int linha, Exp exp, ArrayList<Comando> bloco) {
		this.linha = linha;
		this.exp = exp;
		this.bloco = bloco;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("while (").append(exp).append(") {\n");

		for (Comando comando : bloco) {
			result.append("    ").append(comando.toString().replace("\n", "\n    ")).append("\n");
		}

		result.append("}\n");

		return result.toString();
	}
}
