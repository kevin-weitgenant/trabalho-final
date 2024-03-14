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

  TOKEN:{<MAIN:"main">|<VOID:"void">|<IF:"if">|<WHILE:"while">|<DO:"do">|<RETURN:"return">|<PRINTIO:"printIO">|<LET:"let">|<DEF:"def">|<FLOAT:"float">|<BOOL:"bool">|<READIO:"readIO">|<ACHAVES:"{">|<FCHAVES:"}">|<APARENTESES:"(">|<FPARENTESES:")">|<VIRGULA:",">|<TRUE:"true">|<FALSE:"false">|<PONTOEVIRGULA:";">|<ATR:":=">|<SOMA:"+">|<SUB:"-">|<MULT:"*">|<DIV:"/">|<AND:"&&">|<OR:"||">|<MENOR:"<">|<MAIOR:">">|<IGUAL:"==">}

  TOKEN:{<NUM:(["0"-"9"])+(["."]["0"-"9"])+>|<ID:["a"-"z","A"-"Z"](["a"-"z","A"-"Z","0"-"9"])*>}

  // LUGOSI -> MAIN Fun?
Prog Lugosi() :
{
  Main m;
  ArrayList<Fun> lf = new ArrayList<Fun>();
  Fun f;
}
{
m = Main() (f =Fun() { lf.add(f); })*
{return new Prog(m, lf);}
}

Main Main() :
{
  ArrayList<VarDecl> lv = new ArrayList<VarDecl>();
  ArrayList<Comando> lc = new ArrayList<Comando>();
}
{
  <VOID> <MAIN> <ACHAVES> (VarDecl(lv))* (Comando(lc))* <FCHAVES>
  {
    return new Main(lv, lc);
  }
}

void VarDecl(ArrayList<VarDecl> lv) :
{
  String t, id;
}
{
  <LET> t = Tipo() id = <ID> <PONTOEVIRGULA>
  {
    lv.add(new VarDecl(t, id));
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
    c = CAtribuicao(lc)
    | c = CChamadaFun(lc)
    | c = CIf(lc)
    | c = CWhile(lc)
    | c = CReadInput(lc)
    | c = CReturn(lc)
    | c = CPrint(lc)
  )
}

Comando CAtribuicao(ArrayList<Comando> lc) :
{
  Token id;
  Exp e;
}
{ LOOKAHEAD(3)
  id = <ID> <ATR> e = Exp() <PONTOEVIRGULA>
  {
    Comando c = new CAtribuicao(id.image, e);
    lc.add(c);
    return c;
  }
}

Comando CChamadaFun(ArrayList<Comando> lc) :
{
  Token id;
  ArrayList<Exp> le = new ArrayList<Exp>();
}
{
  id = <ID> <APARENTESES> (ListaExp(le))? <FPARENTESES> <PONTOEVIRGULA>
  {
    Comando c = new CChamadaFun(id.image, le);
    lc.add(c);
    return c;
  }
}

Comando CIf(ArrayList<Comando> lc) :
{
  Exp e;
  ArrayList<Comando> lc2 = new ArrayList<Comando>();
}
{
  <IF> e = Exp() <ACHAVES> (Comando(lc2))* <FCHAVES> <PONTOEVIRGULA>
  {
    Comando c = new CIf(e, lc2);
    lc.add(c);
    return c;
  }
}

  Comando CWhile(ArrayList<Comando> lc): {
    Exp e;ArrayList<Comando>lc2=new ArrayList<Comando>();}{<WHILE>e=Exp()<DO><ACHAVES>(Comando(lc2))*<FCHAVES><PONTOEVIRGULA>{Comando c=new CWhile(e,lc2);lc.add(c);return c;}
  }

  Comando CReadInput(ArrayList<Comando> lc): {
    Token id;}{id=<ID><ATR><READIO><APARENTESES><FPARENTESES>{Comando c=new CReadInput(id.image);lc.add(c);return c;}
  }

  Comando CReturn(ArrayList<Comando> lc): {
    Exp e;}{<RETURN>e=Exp()<PONTOEVIRGULA>{Comando c=new CReturn(e);lc.add(c);return c;}
  }

  Comando CPrint(ArrayList<Comando> lc): {
    Exp e;}{<PRINTIO>e=Exp()<PONTOEVIRGULA>{Comando c=new CPrint(e);lc.add(c);return c;}
  }

  Exp Exp(): {
    Exp e,e1,e2;String op;}{(<APARENTESES>e1=Exp()op=Op()e2=Exp()<FPARENTESES>{return new EOpExp(e1,op,e2);}|e=EChamadaFun()){return e;}
  }

  EChamadaFun EChamadaFun() :
{
    Token id, num;
    ArrayList<Exp> le = new ArrayList<Exp>();
    Exp exp;
}
{
    (
        id = <ID>
        {
            return new EVar(id.image);
        }
    |
        id = <ID> <APARENTESES> (exp =

  Exp() { le.add(exp); }(<VIRGULA> exp = Exp() { le.add(exp); })*)?<FPARENTESES>

  {
    return new EChamadaFun(id.image, le);
  }|num=<NUM>
  {
    return new EFloat(Double.parseDouble(num.image));
  }|<TRUE>
  {
    return new ETrue();
  }|<FALSE>
  {
    return new EFalse();
  })
}

String Op(): {
    Token op;}{(op=<SOMA>|op=<SUB>|op=<MULT>|op=<DIV>|op=<AND>|op=<OR>|op=<MENOR>|op=<MAIOR>|op=<IGUAL>){return op.image;}
  }

void ListaExp(ArrayList<Exp> le) :
{
  Exp e;
}
{
  e = Exp() { le.add(e); }(<VIRGULA> e = Exp() { le.add(e); })*

}

Fun Fun(): {
    String t,id;ArrayList<ParamFormalFun>la=new ArrayList<ParamFormalFun>();ArrayList<VarDecl>lv=new ArrayList<VarDecl>();ArrayList<Comando>lc=new ArrayList<Comando>();}{<DEF>t=Tipo()id=<ID><APARENTESES>(ListaArg(la))?<FPARENTESES><ACHAVES>(VarDecl(lv))*(Comando(lc))*<FCHAVES>{return new Fun(t,id,la,lv,lc);}
  }

void ListaArg(ArrayList<ParamFormalFun> la) :
{
  ParamFormalFun a;
}
{
  a =Arg() { la.add(a); }(<VIRGULA> a = Arg() { la.add(a); })*

}

ParamFormalFun Arg(): {
  String t,id;}{t=Tipo()id=<ID>{return new ParamFormalFun(t,id);}}
