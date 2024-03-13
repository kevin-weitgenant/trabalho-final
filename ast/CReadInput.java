package ast;

public class CReadInput extends Comando {
	public int linha;
	public String var;

	public CReadInput(int linha, String var) {
		this.linha = linha;
		this.var = var;
	}

	@Override
	public String toString() {
		return "scanf(\"%d\", &" + var + ");\n";
		// Assuming that the variable is of integer type, adjust the format specifier
		// ("%d") as needed
	}
}
