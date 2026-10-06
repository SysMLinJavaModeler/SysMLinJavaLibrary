# SysMLinJava Library

### Library of reusable elements
SysMLinJava is a Java-based model development kit (MDK) for high-precision modeling of executable SysML models in the Java programming language.  This repository contains nine example models that are based on SysMLinJava.   Each example can be downloaded and imported into an IDE as a project.  The SysMLinJava module, on which the example model will depend, should be in the IDE as another java project.  The examples are fully tested and can be compiled, built, and executed within the scope of an IDE project.

### Attribute Types
A few attribute types are provided by the library, but the tendency to include attribute types in the SysMLinJava API portends this collection to be limited.

### Annotations
A numver of commonly used comments, hyperlinks, rationales, and element groups are provided in the library. These are provided as collections of such elements for reference by instances that use these elements.

### Requirements
To the extent many requirements follow a common pattern of specification, these requirements are provided as templates or reusable references for use in other model elements. primarily in parts, ports, and items.
### Components
System components (parts, ports, connectors, etc.) that frequently appear in system models, such as ethernet switches, IP routers, comm protocols, computers, etc. are provided for reuse and/or adaptation for system models. 
### Common
Other commonly used elements such as messages, ports, signalsm and miscellaneous objects are also provided by the library.

The current state of tne library is immature, to be sure, but as more commonly used types of model elements, especially parts, ports, and connectors are identified and modeled, they will be included in the library as deemed useful.

This model's dependencies are specified in the model's `module-info.java` file.  In addition to the standard Java API, it "requires transitive" only the SysMLinJava module, which can be obtained at https://github.com/SysMLinJavaModeler/SysMLinJava.git

## Contact for Comments, Questions, Requests for Training or Assistance
Send any comments, questions, or requests for training or assistance to sysmlinjava@earthlink.net.