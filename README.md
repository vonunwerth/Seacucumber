#   Algorithm Framework for Neo4j
![logo](https://user-images.githubusercontent.com/32902225/37259706-145a1e84-258a-11e8-8bb5-a7681435116b.png)
### SeaCucumber-Framework

Before you start, please check our instructions [here](https://github.com/vonunwerth/Seacucumber/blob/master/Instruction/Instructions.pdf).
This project create an interface for your own algorithm and the Neo4j database. The best thing is that you need no more software.

#### Why you need this
- A simple way to check your new algorithm.
- You get a lot of helpful methods for generating algorithm.
- You need no background knowledge, because the instruction and the good Javadocs.

A framework for pros and rookies. So start your new coding experience or read our instruction.

#### Good to know about : Procedures and User functions
If you want more Knowledge about Neo4j Procedures and user functions you can read this:

Procedures, sowie UserFunctions sollten in eigenen Packages abgelegt werden, um sie später einfacher aufrufen zu können.
Aufrufe erfolgen dann in der NEO4J Konsole mit:

`CALL packageName.procedureName(param)`

Fields in Procedure Klassen müssen static sein oder mit @Context annotiert werden.
```
@Context
public GraphDatabaseService db;
Wird benötig, um mit der Datenbank arbeiten zu können.
```

Wenn man die Graphdatenbank bearbeiten möchte, muss die Procedure mit
`@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE)` annotiert werden.

Eine Procedure muss mit `@Description("Beschreibung der Procedure")` annotiert und beschrieben werden.

Procedures werden wie folgt definiert:
```
@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE)
@Description("Beschreibung der Procedure")
public Stream<SearchHit> nameOfProcedure(@Name("parameter1", int parameter1, @Name("parameter2", String parameter2) {...}
```

Als Rückgabetyp einer Procedure kann void gewählt werden. Dabei wird dann einfach ein leerer Stream mit leeren Einträgen zurückgegeben.

Als Argumente von Prozeduren sind nur folgende Typen erlaubt:
String, Long, Double, Number, Boolean, Map, List, Object

Rückgaben einer Prozedur müssen immer `Stream<SearchHit>` sein, wobei SearchHit eine Klasse mit einem Konstruktor, sowie eigenen Attributen sein muss.

```
UserFunctions funktionieren wie Procedures. Diese können allerdings einfache Rückgabetypen haben. Beispiel:
    @UserFunction
    @Description("example.join(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public String join(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
        if (strings == null || delimiter == null) {
            return null;
        }
        return String.join(delimiter, strings);
    }
```

Ab hier kann alles weg?

==INSTALLATION

1. NEO4J herunterladen: https://neo4j.com/download/community-edition/
2. Installieren, lokale Datenbank starten. Sichtbar unter http://127.0.0.1:7474/
3. Database Information --> :server user add --> Name: trump, Passwort: password (Neuen Datenbanknutzer anlegen)
4. Intelij Projekt aus Github auschecken und bauen.
5. Maven dependencies herunterladen (lassen)

6. Projekt als .JAR bauen (mit mvn clean package)
7. JAR in den Plugins Ordner von NEO4J einfügen (in DB-Ordner --> plugins/) (plugins muss manuell erstellt werden)


==PROCEDURES AND USER FUNCTIONS

Procedures, sowie UserFunctions sollten in eigenen Packages abgelegt werden, um sie später einfacher aufrufen zu können.
Aufrufe erfolgen dann in der NEO4J Konsole mit:
CALL packageName.procedureName(param)

Fields in Procedure Klassen müssen static sein oder mit @Context annotiert werden.

@Context
public GraphDatabaseService db;
Wird benötig, um mit der Datenbank arbeiten zu können.

Wenn man die Graphdatenbank bearbeiten möchte, muss die Procedure mit
@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE) annotiert werden.

Eine Procedure muss mit @Description("Beschreibung der Procedure") annotiert und beschrieben werden.

Procedures werden wie folgt definiert:
@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE)
@Description("Beschreibung der Procedure")
public Stream<SearchHit> nameOfProcedure(@Name("parameter1", int parameter1, @Name("parameter2", String parameter2) {...}

Als Rückgabetyp einer Procedure kann void gewählt werden. Dabei wird dann einfach ein leerer Stream mit leeren Einträgen zurückgegeben.

Als Argumente von Prozeduren sind nur folgende Typen erlaubt:
String, Long, Double, Number, Boolean, Map, List, Object

Rückgaben einer Prozedur müssen immer Stream<SearchHit> sein, wobei SearchHit eine Klasse mit einem Konstruktor, sowie eigenen Attributen sein muss.

UserFunctions funktionieren wie Procedures. Diese können allerdings einfache Rückgabetypen haben. Beispiel:
    @UserFunction
    @Description("example.join(['s1','s2',...], delimiter) - join the given strings with the given delimiter.")
    public String join(
            @Name("strings") List<String> strings,
            @Name(value = "delimiter", defaultValue = ",") String delimiter) {
        if (strings == null || delimiter == null) {
            return null;
        }
        return String.join(delimiter, strings);
    }

