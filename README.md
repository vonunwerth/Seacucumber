#   Algorithm Framework for Neo4j
![logo](https://user-images.githubusercontent.com/32902225/37259706-145a1e84-258a-11e8-8bb5-a7681435116b.png)
### SeaCucumber-Framework

Before you start, please check our instructions [here](https://github.com/vonunwerth/Seacucumber/blob/master/Instruction/Instructions.pdf).
This project creates an interface for your own algorithm and the Neo4j database. The best thing is that you need no more software.
With our project you create a procedure for Neo4j and the only thing you need is the idea for your algorithm. You will see we have a lot of helpful methods, these methods will help you create your algorithm with the Java syntax.
So have fun and clone this project.

## Why you need this
- A simple way to check your new algorithm.
- You get a lot of helpful methods for generating algorithm.
- You need no background knowledge, because of the instruction and the good Javadocs.

A framework for pros and rookies. So start your new coding experience or read our instruction.

## You must change in our code:

In our framework you only must change a few things:

1. Create a new class for your new Matching-Algorithm in the matcher package and let your new class extend the abstract class Matcher.
2. Implement the matchingAlgorithm()-method and import org.neo4j.graphdb in order to use the Nodes of Neo4J. Also import the java.util.List instead of the suggested scale list. Lastly import java.util.Map to get a result map of your keys and lists of nodes in the end.

```
@Override
public Map<Integer, List<Node>> matchingAlgorithm() {
    //Your own matcher
}
```

3. You have to write a constructor in your class. The constructor’s name has to be the same as the classname. The following structure can be used:

```
 public [AlgorithmsName] (org.neo4j.graphdb.GraphDatabaseService db,
    graph.Graph graph) {
    this.db = db; //Describes your database
    this.graph = graph; //Describes your graph
    }
```

4. Now you have to create a new procedure to access your matcher on your Neo4J database. Go to the procedure.GraphProcedures class and e.g. copy one of the example procedures for Dual Simulation or Failures.

``` 
 @Procedure(value = "graph.[NAME]", mode = Mode.READ)
 @Description("[DESCRIPTION]")
 @SuppressWarnings("unused")
 public Stream<NodeResult> [NAME](@Name("query") String  query) {
    Graph graph = prepareQuery(db, query);
    [MATCHER] matcher = new [MATCHER](db, graph);
    Set<Node> simulated = matcher.simulate();
    return simulated.stream().map(NodeResult::new);
    }
    
```
Replace [NAME] with the name of your new procedure and [MATCHER] with the name of your new matcher class.

## Start with your procedure:
If you want to create your own procedure, you need maven. So your last step before you have a ready procedure is to call this in maven:

`mvn clean package`

This creates a new target folder at your storage (at the place where you storage this project). In the folder is a .jar file and this is your procedure. The last step is to drag the file to your plugins folder at your database.
If you have a problem with this step, please check [this](https://github.com/vonunwerth/Seacucumber/blob/master/Instruction/Instructions.pdf).

## Good to know: Procedures and User functions
If you want more Knowledge about Neo4j Procedures and user functions you can read this:

Procedures and UserFunctions should have an own Package for storage, this is for a better method calling.
A Query can be written in the Neo4j-Terminal with:

`CALL packageName.procedureName(param)`

Fields at the Procedure class must be static or be annotated with a @Context.
```
@Context
public GraphDatabaseService db;
Needed to work with the database.
```

If you want to change the graphdatabase, the procedure must be annotated with
`@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE)` .

Also the procedure must be annotated with `@Description("Description of the Procedure")` and describe the procedure.

Procedures are defined as follows:
```
@Procedure(value = "NameofPackage.NameofProcedure", mode = MODE.WRITE)
@Description("Description from the Procedure")
public Stream<SearchHit> nameOfProcedure(@Name("parameter1", int parameter1, @Name("parameter2", String parameter2) {...}
```

The return value of a procedure can be void. In this case the return Stream is empty.

The following types of arguments are allowed in your procedure:
String, Long, Double, Number, Boolean, Map, List, Object

The return of a procedure must be `Stream<SearchHit>`, in which SearchHit is a class with a constructor, with own attributs.

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
