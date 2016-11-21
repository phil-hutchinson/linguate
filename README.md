# linguate
Compiler tools - linguate.org

## Overview
The goal of the linguate project is to create a set of tools that can be used to create a self-contained compiler definition
that can be used on any platform. As such, linguate contains two types of tools: those that are used to create compiler defintions,
and those that consume compiler definitions. The compiler definition itself will be stored as an XML file. The compiler consumer
can be implemented on any platform, and once it has been implemented, any tool built for linguate will be accessible on that 
platform (where extensibility has not been used - see below)

It is important to note that "compiler" is broadly defined, and the linguate platform should be an effective tool for writing code
minifiers, transpilers, etc - in fact, this may be its main niche, as teams involved in writing a full-blown compiler may prefer 
to do so in code. However, once advantage of using linguate will be that it will be straightforward to make the compiler "native"
(i.e. written in its own languate) because the only component that will need to be built is the compiler definition consumer.

### Compiler Definition Producers vs Consumers
Only one compiler definition producer will need to be written, and it is part of the linguate project. One compiler definition
producer will be need to be written per platform/environment (eg. JVM, .NET runtime, JavaScript, etc.)

### Extensibility
In order to allow for additional functionality beyond what linguate tools permit, when building compiler definitions, it will be 
possible to specify that an extension be used at some phase of the compilation. This extension will need to be written and made 
available in the runtime environment (i.e. by the consumer). This should be avoided where possible, as it means that additional
code needs to be written for each platform/environment. The aim will be to make linguate powerful enough that extensions rarely
need to be used, but they will be made available nonetheless to ensure that where limitations of linguate do exist, they can be
overcome.

## Distinction from Existing Tools
The traditional tools used to build compilers have been lexers like [Lex](https://en.wikipedia.org/wiki/Lex_(software)) and
parsers like [Yacc](https://en.wikipedia.org/wiki/Yacc) and [Bison](https://en.wikipedia.org/wiki/GNU_bison), and other tools 
that mimic their functionality. These tools tend to target a single platform (for example, the traditional tools are built
for GNU C, and are most often used in a UNIX environment.

One goal of linguate is to build similar lexing and parsing components that are easily transportable to any environment, in a 
cleaner package. For example, input files for lex and yacc tend to intermix language definitions such as the context-free
grammar with snippets of code that will be placed into the compiler. Linguate will attempt to keep these elements distinct,
allowing the language description to be more organized. This involves splitting the input into a project type environment
containing multiple files of multiple types) rather than a single multi-purpose input file, and a single language definition 
stored in XML that can easily be consumed in any environment, rather than an output file written in a specific language.

Additionally, linguate will present additional functionality that will allow other stages of the compiler (such as syntax
analysis and intermediate code generation) to be included as part of the initial input, so that the entire compiler can be
specified within the target XML file.

Finally, the overall approach will be modernized - a web interface will be built for linguate that allows linguate users to
produce and store language definitions, and 

In summary, the overall goals for linguate are to provide a cleaner approach for existing functionality, to include broader
functionality, to facilitate developing tools that work in all platforms, and to design the tools using a modern approach 
to software development.

## Scope
The main linguate project contains both a language definition producer (written in Java), and a language definition consumer
for Java. An additional project will be created for each other language definition consumer (which must be built for each
environment.)

The linguate tools will focus primarily on the front end of compilers (i.e. converting from raw source to intermediate language),
with the goal being to produce a complete solution for compiler front ends. Because .NET CLR and JVM effectively provide the back
end of the compiler, this step can often be skipped. More backend tools may be added in future if warranted.

## Phase One
Phase One of the project will focus on implementing the traditional compiler tools i.e. a scanner and a parser. The main
components of phase one will include the following. 

Compiler Definition Producer:
* Language Source Readers for regex-based token definitions, context-free grammar, etc.
* Lexer Generator
* Parser Generator
* Compiler Definition Writer

Compiler Definition Consumer:
* Compiler Definition Reader
* Lexer
* Parser

Web Interface:
* inteface for building language source projects and files
* save functionality for language source projects/files (a la jsfiddle)
* compiler definition builder (using Compiler Definition Producer)

### Outstanding Work for Phase One
The main tasks to be completed for phase one include:
* the parser generator
* the web interface (all)
* clean-up of unit tests (consistent methodology and approach)

## Future Enhancements
The following features will be added in future. The order they are listed provides a rough idea on their relative priority.

### Additional Compiler Tools
Additional tools will be added to the compiler pipeline to include phases such as syntax analysis and semantic analysis. To this
end, a domain-specific langauge will be written (using the linguate lexer and parser) that allows for the manipulation of syntax
trees, and the editing of attributes on syntax tree nodes. Upon completion of this phase, a linguate compiler definition should
be able to contain an entire front end of a compiler.

### Automated IL Generators
Writing to an Intermediate Language (such as Java byte code or .NET's CIL) requires the understanding of a lengthy spec, and
involves a lot of "grunt work" to build the correct sequence of bytes. Tools can be written that will convert a syntax tree
into a specific IL, if the syntax tree is correctly formatted for the IL-specific generator. Since this is the last step of 
the front end of a compiler, this further reduces complexity in developing a compiler, since it is now only necessary to 
build the front end of the front end.

### Building Consumer Tools in DSL
The domain-specific language developed as part of the "Additional Compiler Tools" project can also be used to write some 
components of the language definition consumer, such as the lexer and parser. By writing these tools in the DSL, it reduces the
amount of code that needs to be built for each target environment. Ideally, only a tree-manipulator will need to be written
for each target environment

### Creation of Consumers for Other Target Environments
The consumer-component of linguate will be built for additional environments (such as .NET and JavaScript). Note: these
components will be built in separate projects.


