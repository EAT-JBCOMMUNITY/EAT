# Convert Javax classes to Jakarta ones
---------------------------------------
mvn compile exec:java -Djar='path to the jar/war that contains the classes with javax classes to be converted to jakarta ones'


The converted classes can be found at the parent/current directory.
