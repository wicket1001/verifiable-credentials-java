package wu;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import foundation.identity.jsonld.JsonLDException;
import picocli.CommandLine;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.security.GeneralSecurityException;

import static wu.CredentialsUtil.*;
import static wu.TestEBSIDiplomaPresentation.getVerifiablePresentation;
import static wu.CredentialsHelper.verify;

@CommandLine.Command(name = "CommandLineVP", version = "CommandLineVP 1.1", mixinStandardHelpOptions = true)
public class CommandLineVP extends CommandLineHelper implements Runnable {
    @CommandLine.ArgGroup(exclusive = false)
    InputIssuer inputIssuer;

    static class InputIssuer {
        @CommandLine.Option(names = {"-i", "--issuer"}, description = "The DID of the Issuer")
        String issuerDID;

        @CommandLine.Option(names = {"-k", "--key"}, description = "The private key of the issuer as hex string without leading 0x.")
        String issuerPrivKey;

        @CommandLine.Option(
                required = false,
                names = {"-p", "--pub"},
                description = "The private key of the issuer as hex string without leading 0x."
        )
        String issuerPubKey;
    }

    @CommandLine.Option(names = {"-o", "--out"}, description = "The file where to write the verifiable credential to.")
    Path outputPath;

    @CommandLine.Parameters(index = "0", paramLabel = "<did-student>", description = "DID of the Student")
    String studentInputDID;

    URI studentDID;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CommandLineVP()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            studentDID = URI.create(studentInputDID);
            VerifiableCredential verifiableCredential = CommandLineHelper.getVerifiableCredential(inputClaims);
            String publicKey = getPublicKey(PUBLIC_KEY_ISSUER);

            if (verifiableCredential != null) {
                VerifiablePresentation vp = getVerifiablePresentation(verifiableCredential);

                System.out.println(vp.toJson());
                if (outputPath != null) {
                    boolean created = outputPath.toFile().createNewFile();
                    if (created) {
                        FileWriter fw = new FileWriter(outputPath.toFile());
                        fw.write(vp.toJson());
                        fw.close();
                    }
                }

                boolean verified = verify(vp, publicKey);
                if (!verified) {
                    throw new IllegalStateException("Something has gone wrong with the execution. Could not verify verifiable presentation.");
                }
            }
        } catch (JsonLDException | GeneralSecurityException | IOException e) {
            System.out.println("There was an error creating the DID from the input.");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error paring your input.");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalStateException e) {
            System.out.println("There was an error in verifying the newly created VC. Please contact an admin.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    String getPublicKey(String defaultPubKey) {
        if (inputIssuer != null) {
            if (inputIssuer.issuerPubKey != null && !inputIssuer.issuerPubKey.isEmpty()) {
                return inputIssuer.issuerPubKey;
            }
        }
        return defaultPubKey;
    }
}
