Requirements for Successful Build of Bo2 with Unit Tests :
 * 7z installed and accessible via command prompt (for windows)
 * openoffice installed and accessible via command prompt ( command is soffice )

Javadoc validation :
 * javadoc validation is enabled on Bo2
 * to be sure the changes you did has not broken this - run mvn javadoc:javadoc -rf (project name) 
 
Rules are :

no self-closed HTML tags, such as <br /> or <a id="x" />
no unclosed HTML tags, such as <ul> without matching </ul>
no invalid HTML end tags, such as </br>
no invalid HTML attributes, based on doclint's interpretation of W3C HTML 4.01
no duplicate HTML id attribute
no empty HTML href attribute
no incorrectly nested headers, such as class documentation must have <h3>, not <h4>
no invalid HTML tags, such as List<String> (where you forgot to escape using &lt;)
no broken @link references
no broken @param references, they must match the actual parameter name
no broken @throws references, the first word must be a class name 
replace inside all java files -> with -&gt;
replace inside all java files <- with &lt;-
<ul><li></li>...</ul> : need fix by hand. All <li></li> must be inside <ul></ul>
This is not allowed <p> <ul>....</ul> </p>
Do not put generics in comments or add &lt;ClassName&gt;
ex: &lt;T extends Object &amp; Comparable&lt;? super T&gt;&gt;
Generics are allowed only as param name @param <T> comment
unresolved @see @link : delete by hand
@ -> &#64; for example code annotation in javadoc
inside javadoc <table> must be <table summary=""> 