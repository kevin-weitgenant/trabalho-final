PARSER_BEGIN(Lugosi)

import ast.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Lugosi {
 public static void main(String args[]) throws ParseException, IOException {
 Lugosi parser = new Lugosi(new FileInputStream(args[0]));
 Prog arvore = parser.Lugosi();
 System.out.println(arvore.toString());
 }

}PARSER_END(Lugosi)

SKIP:{" "|"\t"|"\n"|"\r"}

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

// LUGOSI -> MAIN FUNC?
Prog Lugosi() :
{
  Main m;
  ArrayList<Func> lf = new ArrayList<Func>();
  Func f;
}
{
  m = Main() (f = Func() { lf.add(f); })*
  {
    return new Prog(m, lf);
  }
}

Main Main() :
{
  ArrayList<Vardecl> lv = new ArrayList<Vardecl>();
  ArrayList<Comando> lc = new ArrayList<Comando>();
}
{
  <VOID> <MAIN> <ACHAVES> (Vardecl(lv))* (Comando(lc))* <FCHAVES>
  {
    return new Main(lv, lc);
  }
}

void Vardecl(ArrayList<Vardecl> lv) :
{
  String t, id;
}
{
  <LET> t = Tipo() id = <ID> <PONTOEVIRGULA>
  {
    lv.add(new Vardecl(t, id));
  }
}

String Tipo() :
{
  Token t;
}
{
  (t = <FLOAT> | t = <BOOL> | t = <VOID>)
  {
    return t.image;
  }
}

void Comando(ArrayList<Comando> lc) :
{
  Comando c;
}
{
  (
    c = ComandoAtrib(lc)
    | c = ComandoChamada(lc)
    | c = ComandoIf(lc)
    | c = ComandoWhile(lc)
    | c = ComandoInput(lc)
    | c = ComandoReturn(lc)
    | c = ComandoOutput(lc)
  )
}

Comando ComandoAtrib(ArrayList<Comando> lc) :
{
  Token id;
  Exp e;
}
{
  id = <ID> <ATR> e = Exp() <PONTOEVIRGULA>
  {
    Comando c = new ComandoAtrib(id.image, e);
    lc.add(c);
    return c;
  }
}

Comando ComandoChamada(ArrayList<Comando> lc) :
{
  Token id;
  ArrayList<Exp> le = new ArrayList<Exp>();
}
{
  id = <ID> <APARENTESES> (ListaExp(le))? <FPARENTESES> <PONTOEVIRGULA>
  {
    Comando c = new ComandoChamada(id.image, le);
    lc.add(c);
    return c;
  }
}

Comando ComandoIf(ArrayList<Comando> lc) :
{
  Exp e;
  ArrayList<Comando> lc2 = new ArrayList<Comando>();
}
{
  <IF> e = Exp() <ACHAVES> (Comando(lc2))* <FCHAVES> <PONTOEVIRGULA>
  {
    Comando c = new ComandoIf(e, lc2);
    lc.add(c);
    return c;
  }
}

Comando ComandoWhile(ArrayList<Comando> lc) :
{
  Exp e;
  ArrayList<Comando> lc2 = new ArrayList<Comando>();
}
{
  <WHILE> e = Exp() <DO> <ACHAVES> (Comando(lc2))* <FCHAVES> <PONTOEVIRGULA>
  {
    Comando c = new ComandoWhile(e, lc2);
    lc.add(c);
    return c;
  }
}

Comando ComandoInput(ArrayList<Comando> lc) :
{
  Token id;
}
{
  id = <ID> <ATR> <READIO> <APARENTESES> <FPARENTESES>
  {
    Comando c = new ComandoInput(id.image);
    lc.add(c);
    return c;
  }
}

Comando ComandoReturn(ArrayList<Comando> lc) :
{
  Exp e;
}
{
  <RETURN> e = Exp() <PONTOEVIRGULA>
  {
    Comando c = new ComandoReturn(e);
    lc.add(c);
    return c;
  }
}

Comando ComandoOutput(ArrayList<Comando> lc) :
{
  Exp e;
}
{
  <PRINTIO> e = Exp() <PONTOEVIRGULA>
  {
    Comando c = new ComandoOutput(e);
    lc.add(c);
    return c;
  }
}

Exp Exp() :
{
  Exp e, e1, e2;
  String op;
}
{
  (
    <APARENTESES> e1 = Exp() op = Op() e2 = Exp() <FPARENTESES>
    {
      return new ExpBin(e1, op, e2);
    }
    | e = Fator()
  )
  {
    return e;
  }
}

Fator Fator() :
{
  Token id, num;
  ArrayList<Exp> le = new ArrayList<Exp>();
}
{
  (
    id = <ID>
    {
      return new FatorId(id.image);
    }
    | id = <ID> <APARENTESES> (ListaExp(le))? <FPARENTESES>
    {
      return new FatorChamada(id.image, le);
    }
    | num = <NUM>
    {
      return new FatorNum(Double.parseDouble(num.image));
    }
    | <TRUE>
    {
      return new FatorBool(true);
    }
    | <FALSE>
    {
      return new FatorBool(false);
    }
  )
}

String Op() :
{
  Token op;
}
{
  (
    op = <SOMA>
    | op = <SUB>
    | op = <MULT>
    | op = <DIV>
    | op = <AND>
    | op = <OR>
    | op = <MENOR>
    | op = <MAIOR>
    | op = <IGUAL>
  )
  {
    return op.image;
  }
}

void ListaExp(ArrayList<Exp> le) :
{
  Exp e;
}
{
  e = Exp() { le.add(e); } (<VIRGULA> e = Exp() { le.add(e); })*
}

Func Func() :
{
  String t, id;
  ArrayList<Argdecl> la = new ArrayList<Argdecl>();
  ArrayList<Vardecl> lv = new ArrayList<Vardecl>();
  ArrayList<Comando> lc = new ArrayList<Comando>();
}
{
  <DEF> t = Tipo() id = <ID> <APARENTESES> (ListaArg(la))? <FPARENTESES> <ACHAVES>
  (Vardecl(lv))* (Comando(lc))* <FCHAVES>
  {
    return new Func(t, id, la, lv, lc);
  }
}

void ListaArg(ArrayList<Argdecl> la) :
{
  Argdecl a;
}
{
  a = Arg() { la.add(a); } (<VIRGULA> a = Arg() { la.add(a); })*
}

Argdecl Arg() :
{
  String t, id;
}
{
  t = Tipo() id = <ID>
  {
    return new Argdecl(t, id);
  }
}