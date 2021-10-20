package wu;

import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import foundation.identity.jsonld.JsonLDException;
import picocli.CommandLine;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Map;

import static wu.CredentialsUtil.*;
import static wu.CredentialsHelper.verify;

@CommandLine.Command(name = "CommandLineVC", version = "CommandLineVC 1.1", mixinStandardHelpOptions = true)
public class CommandLineVC extends CommandLineHelper implements Runnable {
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
                description = "The public key of the issuer as hex string without leading 0x."
        )
        String issuerPubKey;
    }

    @CommandLine.Option(names = {"-o", "--out"}, description = "The file where to write the verifiable credential to.")
    Path outputPath;

    @CommandLine.Parameters(index = "0", paramLabel = "<did-student>", description = "DID of the Student")
    String studentInputDID;

    URI studentDID;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CommandLineVC()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            studentDID = URI.create(studentInputDID);
            CredentialSubject credentialSubject = getCredentialSubject();
            String privateKey = getPrivateKey(PRIVATE_KEY_ISSUER);
            String publicKey = getPublicKey(PUBLIC_KEY_ISSUER);
            URI issuerDID = getIssuerDID(CredentialsUtil.issuerDID);


            if (credentialSubject != null) {
                Map<String, Object> claims = credentialSubject.getClaims();
                claims.remove("type");
                claims.remove("id");

                VerifiableCredential vc = TestEBSIDiplomaCredential.getVerifiableCredential(studentDID, issuerDID, claims, privateKey);

                System.out.println(vc.toJson());
                if (outputPath != null) {
                    boolean created = outputPath.toFile().createNewFile();
                    if (created) {
                        FileWriter fw = new FileWriter(outputPath.toFile());
                        fw.write(vc.toJson());
                        fw.close();
                    }
                }

                boolean verified = verify(vc, publicKey);
                if (!verified) {
                    throw new IllegalStateException("Something has gone wrong with the execution. Could not verify verifiable credential.");
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

    private URI getIssuerDID(URI defaultIssuerDID) {
        if (inputIssuer != null) {
            if (inputIssuer.issuerDID != null && !inputIssuer.issuerDID.isEmpty()) {
                return URI.create(inputIssuer.issuerDID);
            }
        }
        return defaultIssuerDID;
    }

    String getPublicKey(String defaultPubKey) {
        if (inputIssuer != null) {
            if (inputIssuer.issuerPubKey != null && !inputIssuer.issuerPubKey.isEmpty()) {
                return inputIssuer.issuerPubKey;
            }
        }
        return defaultPubKey;
    }

    String getPrivateKey(String defaultPrivKey) {
        if (inputIssuer != null) {
            if (inputIssuer.issuerPrivKey != null && !inputIssuer.issuerPrivKey.isEmpty()) {
                return inputIssuer.issuerPrivKey;
            }
        }
        return defaultPrivKey;
    }

    private CredentialSubject getCredentialSubject() throws FileNotFoundException, IllegalArgumentException {
        CredentialSubject inputSubject = null;
        if (inputClaims != null) {
            if (inputClaims.credentialsJson != null && !inputClaims.credentialsJson.isEmpty()) {
                inputSubject = CredentialSubject.fromJson(inputClaims.credentialsJson);
            } else if (inputClaims.credentialsPath != null) {
                inputSubject = CredentialSubject.fromJsonObject((Map<String, Object>) CredentialSubject.fromJson(
                        new InputStreamReader(
                                new FileInputStream(
                                        inputClaims.credentialsPath.toFile()
                                )
                        )
                ).getJsonObject().get("credentialSubject"));
            } else {
                throw new IllegalArgumentException("There was an error in handling the json input.");
            }
        }
        return inputSubject;
    }
}
