PARSER_BEGIN(Lugosi)

import ast.*;
import java.io.*;
import java.text.ParseException;

public class Lugosi {
  public static void main(String args[]) throws ParseException, IOException {
    Lugosi parser = new Lugosi(new FileInputStream(args[0]));
    Prog arvore = parser.Lugosi();
    System.out.println(arvore.toString());
  }

  
}
  PARSER_END(Lugosi)

SKIP :
{
  " "
| "\t"
| "\n"
| "\r"
}

TOKEN :
{
  <MAIN: "main">
| <VOID: "void">
| <IF: "if">
| <WHILE: "while">
| <DO: "do">
| <RETURN: "return">
| <PRINTIO: "printIO">
| <LET: "let">
| <DEF: "def">
| <FLOAT: "float">
| <BOOL: "bool">
| <READIO: "readIO">
| <ACHAVES: "{">
| <FCHAVES: "}">
| <APARENTESES: "(">
| <FPARENTESES: ")">
| <VIRGULA: ",">
| <TRUE: "true">
| <FALSE: "false">
| <PONTOEVIRGULA: ";">
| <ATR: ":=">
| <SOMA: "+">
| <SUB: "-">
| <MULT: "*">
| <DIV: "/">
| <AND: "&&">
| <OR: "||">
| <MENOR: "<">
| <MAIOR: ">">
| <IGUAL: "==">
}

TOKEN :
{
  <NUM : (["0"-"9"])+(["."]["0"-"9"])+>
| <ID: ["a"-"z","A"-"Z"] ( ["a"-"z","A"-"Z","0"-"9"])*>
}


void Lugosi () :
{Token t;}
{

(
  <MAIN> { System.out.println("Palavra reservada: main");}
| <VOID> { System.out.println("Palavra reservada: void");}
| <IF> { System.out.println("Palavra reservada: if");}
| <WHILE> { System.out.println("Palavra reservada: while");}
| <DO> { System.out.println("Palavra reservada: do");}
| <RETURN> { System.out.println("Palavra reservada: return");}
| <PRINTIO> { System.out.println("Palavra reservada: printIO");}
| <LET> { System.out.println("Palavra reservada: let");}
| <DEF> { System.out.println("Palavra reservada: def");}
| <FLOAT> { System.out.println("Palavra reservada: float");}
| <BOOL> { System.out.println("Palavra reservada: bool");}
| <READIO> { System.out.println("Palavra reservada: readIO");}
| <ACHAVES> { System.out.println("Abre chaves: {");}
| <FCHAVES> { System.out.println("Fecha chaves: }");}
| <APARENTESES> { System.out.println("Abre parenteses: (");}
| <FPARENTESES> { System.out.println("Fecha parenteses: )");}
| <VIRGULA> { System.out.println("Vírgula: ,");}
| <TRUE> { System.out.println("Tipo bool: true");}
| <FALSE> { System.out.println("Tipo bool: false");}
| t=<NUM> { System.out.println("Número: "+ t.image);}
| t=<ID> { System.out.println("Identificador: "+ t.image);}
| t=<PONTOEVIRGULA> { System.out.println("Ponto e virgula: "+ t.image);}
| t=<ATR> { System.out.println("Atribuição: "+ t.image);}
| t=<SOMA> { System.out.println("Op. aritmético: "+ t.image);}
| t=<SUB> { System.out.println("Op. aritmético: "+ t.image);}
| t=<MULT> { System.out.println("Op. aritmético: "+ t.image);}
| t=<DIV> { System.out.println("Op. aritmético: "+ t.image);}
| t=<AND> { System.out.println("Op. lógico: "+ t.image);}
| t=<OR> { System.out.println("Op. lógico: "+ t.image);}
| t=<MENOR> { System.out.println("Op. de comparação: "+ t.image);}
| t=<MAIOR> { System.out.println("Op. de comparação: "+ t.image);}
| t=<IGUAL> { System.out.println("Op. de comparação: "+ t.image);}
)*
<EOF> 
}