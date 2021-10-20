package wu;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import picocli.CommandLine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class CommandLineHelper {
    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    InputClaims inputClaims;

    static class InputClaims {
        @CommandLine.Option(names = {"-j", "--json"}, description = "credential subject of student as json string")
        String credentialsJson;

        @CommandLine.Option(names = {"-f", "--file"}, description = "The path to the credential subject of the student in a json file format")
        Path credentialsPath;
    }

    static VerifiableCredential getVerifiableCredential(CommandLineVP.InputClaims inputClaims) throws FileNotFoundException, IllegalArgumentException {
        VerifiableCredential inputCredential = null;
        if (inputClaims != null) {
            if (inputClaims.credentialsJson != null && !inputClaims.credentialsJson.isEmpty()) {
                inputCredential = VerifiableCredential.fromJson(inputClaims.credentialsJson);
            } else if (inputClaims.credentialsPath != null) {
                inputCredential = VerifiableCredential.fromJson(
                        new InputStreamReader(
                                new FileInputStream(
                                        inputClaims.credentialsPath.toFile()
                                )
                        )
                );
            } else {
                throw new IllegalArgumentException("There was an error in handling the json input.");
            }
        }
        return inputCredential;
    }
}
