Compiling and running the client:

javac -d bin -cp "bin;res" src\main\\*.java src\entity\\*.java src\map\\*.java src\network\\*.java src\object\\*.java src\structs\\*.java src\util\\*.java

java -cp "bin;res" main.Main   

Compiling and running the server:

javac -d bin -cp "bin;res" src\main\\*.java src\entity\\*.java src\object\\*.java src\structs\\*.java

java -cp "bin;res" main.Main   
