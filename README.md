# verifiable-credentials-java

## Credits

This library is developed by the awsome team of [danubetech](https://danubetech.com/). Check out their library at [verifiable-credentials-java](https://github.com/danubetech/verifiable-credentials-java) which is updated and maintained frequently.

The library also uses [picocli](https://github.com/remkop/picocli) as a commandline wrapper.

## Disclaimer

This library is not stable and subject to changes as work progresses. The most frequent commit should mostly work, but if it does not feel free to ask.

## General

This fork is a simple command line wrapper for the [verifiable-credentials-java](https://github.com/danubetech/verifiable-credentials-java) library as our servers use python in background that it is the easiest to call the library via the command line.

## First steps

First, you need to build the project with maven to obtain the compiled classes.

Just run:

	mvn install

Then you can call wu.CommandLineVC in your favorite IDE and it should result in the commandline parameters for the programm:

    wu.CommandLineVC

    Usage: CommandLineVC [-hV] [-o=<outputPath>] (-j=<credentialsJson> | -f=<credentialsPath>) [[-i=<issuerDID>][-k=<issuerPrivKey>] [-p=<issuerPubKey>]] <did-student>

    <did-student>                 DID of the Student

    -f, --file=<credentialsPath>  The path to the credential subject of the student in a json file format
    -h, --help                    Show this help message and exit.
    -i, --issuer=<issuerDID>      The DID of the Issuer
    -j, --json=<credentialsJson>  credential subject of student as json string
    -k, --key=<issuerPrivKey>     The private key of the issuer as hex string without leading 0x.
    -o, --out=<outputPath>        The file where to write the verifiable credential to.
    -p, --pub=<issuerPubKey>      The public key of the issuer as hex string without leading 0x.
    -V, --version                 Print version information and exit.

A full call from the command line could look something like this:

    ~/.jdks/openjdk-16.0.1/bin/java -Dfile.encoding=UTF-8 -classpath ~/verifiable-credentials-java/target/classes:~/.m2/repository/org/slf4j/slf4j-api/1.7.26/slf4j-api-1.7.26.jar:~/.m2/repository/com/google/protobuf/protobuf-java/3.11.1/protobuf-java-3.11.1.jar:~/.m2/repository/com/google/guava/guava/30.0-jre/guava-30.0-jre.jar:~/.m2/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:~/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:~/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:~/.m2/repository/org/checkerframework/checker-qual/3.5.0/checker-qual-3.5.0.jar:~/.m2/repository/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar:~/.m2/repository/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar:~/.m2/repository/info/weboftrust/ld-signatures-java/0.4-SNAPSHOT/ld-signatures-java-0.4-20210721.133711-28.jar:~/.m2/repository/decentralized-identity/jsonld-common-java/0.2.0/jsonld-common-java-0.2.0.jar:~/.m2/repository/com/apicatalog/titanium-json-ld/1.1.0/titanium-json-ld-1.1.0.jar:~/.m2/repository/io/setl/rdf-urdna/1.1/rdf-urdna-1.1.jar:~/.m2/repository/org/glassfish/jakarta.json/2.0.0/jakarta.json-2.0.0.jar:~/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar:~/.m2/repository/com/danubetech/key-formats-java/0.3.0/key-formats-java-0.3.0.jar:~/.m2/repository/org/abstractj/kalium/kalium/0.8.0/kalium-0.8.0.jar:~/.m2/repository/com/github/jnr/jnr-ffi/2.0.5/jnr-ffi-2.0.5.jar:~/.m2/repository/com/github/jnr/jffi/1.2.9/jffi-1.2.9.jar:~/.m2/repository/com/github/jnr/jffi/1.2.9/jffi-1.2.9-native.jar:~/.m2/repository/org/ow2/asm/asm/5.0.3/asm-5.0.3.jar:~/.m2/repository/org/ow2/asm/asm-commons/5.0.3/asm-commons-5.0.3.jar:~/.m2/repository/org/ow2/asm/asm-analysis/5.0.3/asm-analysis-5.0.3.jar:~/.m2/repository/org/ow2/asm/asm-tree/5.0.3/asm-tree-5.0.3.jar:~/.m2/repository/org/ow2/asm/asm-util/5.0.3/asm-util-5.0.3.jar:~/.m2/repository/com/github/jnr/jnr-x86asm/1.0.2/jnr-x86asm-1.0.2.jar:~/.m2/repository/org/bitcoinj/bitcoinj-core/0.15.10/bitcoinj-core-0.15.10.jar:~/.m2/repository/org/bouncycastle/bcprov-jdk15to18/1.68/bcprov-jdk15to18-1.68.jar:~/.m2/repository/com/squareup/okhttp3/okhttp/3.12.8/okhttp-3.12.8.jar:~/.m2/repository/com/squareup/okio/okio/1.15.0/okio-1.15.0.jar:~/.m2/repository/net/jcip/jcip-annotations/1.0/jcip-annotations-1.0.jar:~/.m2/repository/com/github/multiformats/java-multibase/v1.0.0/java-multibase-v1.0.0.jar:~/.m2/repository/com/nimbusds/nimbus-jose-jwt/9.9/nimbus-jose-jwt-9.9.jar:~/.m2/repository/com/github/stephenc/jcip/jcip-annotations/1.0-1/jcip-annotations-1.0-1.jar:~/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.11.1/jackson-databind-2.11.1.jar:~/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.11.1/jackson-annotations-2.11.1.jar:~/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.11.1/jackson-core-2.11.1.jar:~/.m2/repository/decentralized-identity/did-common-java/0.3-SNAPSHOT/did-common-java-0.3-20210721.083123-35.jar wu.CommandLineVC

There is a python script which generates this full call, if there is any further demand for it, it will be added.

## Using the wrapper

The java call then accepts different parameters as stated above and will return the VC/VP on standard output.

### Calling the program with a credential subject directly

The easiest way of using the program is to call it with the credential subject directly and also other programs will call it like this.

    wu.CommandLineVC
    -j "{\"type\": \"Student\", \"id\": \"did:ebsi:csapca8odx7vdpezz158sq7strxxvb3yxgq2uruawavz\", \"studyProgram\": \"Master Studies in Strategy, Innovation, and Management Control\", \"immatriculationNumber\": \"00000000\", \"currentGivenName\": \"Eva\", \"currentFamilyName\": \"Musterfrau\", \"learningAchievement\": \"Master's Degree\", \"dateOfBirth\": \"1999-10-32T00:00:00.000Z\", \"dateOfAchievement\": \"2021-01-04T00:00:00.000Z\", \"overallEvaluation\": \"passed with honors\", \"eqfLevel\": \"http://data.europa.eu/snb/eqf/7\", \"targetFrameworkName\": \"European Qualifications Framework for lifelong learning - (2008/C 111/01)\"}"
    -o "generatedVC.json"
    did:ebsi:csapca8odx7vdpezz158sq7strxxvb3yxgq2uruawavz

Do not forget to escape the json if used as a parameter.

### Calling the program with a credential subject in a file

The more convenient way for a human is to write the credential subject into a file.

    -f ~/verifiable-credentials-java/src/test/resources/wu/credentialSubject.json
    -o ~/verifiable-credentials-java/src/test/resources/wu/verifiableCredential.json
    did:ebsi:csapca8odx7vdpezz158sq7strxxvb3yxgq2uruawavz

## The verification

As of now there has been a bit of work done to also write a wrapper for the verification process.
The problem is that the verification process is far complexer and so the incentive now goes to using the [Univerifier](https://univerifier.io/) from [danubetech](https://danubetech.com/).
If there is any particular need or interest in these programs it could also be shared, so feel free to ask.

## About

This project was made at [Vienna University of Economics and Business](https://www.wu.ac.at/) as part of the [EBSI - European Blockchain Service Infrastructure](https://ec.europa.eu/cefdigital/wiki/display/CEFDIGITAL/EBSI) initiative.

If you have any questions feel free to ask.
