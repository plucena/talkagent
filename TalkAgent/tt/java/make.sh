echo "compiling..."
rm -r com
javac -d . TTLexEntry.java
javac -d . TTLexEntryToObj.java
javac -d . TTPNode.java
javac -d . TT.java
javac -d . TTConnection.java
javac -d . Example.java
javac -d . Parse.java
javac -d . HowManyMinutesIs.java
javac -d . WhereDoIFind.java
echo "making JAR..."
rm -f tt.jar
jar cvf tt.jar com
echo "making documentation..."
javadoc -d doc -public TT*.java
