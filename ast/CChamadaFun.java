package ast;

import java.util.ArrayList;

public class CChamadaFun extends Comando {
	public int linha;
	public String fun;
	public ArrayList<Exp> args;

	public CChamadaFun(int linha, String fun, ArrayList<Exp> args) {
		this.linha = linha;
		this.fun = fun;
		this.args = args;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(fun).append("(");

		if (args != null && !args.isEmpty()) {
			result.append(args.get(0));

			for (int i = 1; i < args.size(); i++) {
				result.append(", ").append(args.get(i));
			}
		}

		result.append(");\n");
		return result.toString();
	}
}
