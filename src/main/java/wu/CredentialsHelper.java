package wu;

import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.JsonLdErrorCode;
import com.danubetech.keyformats.crypto.impl.Ed25519_EdDSA_PublicKeyVerifier;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import com.danubetech.verifiablecredentials.validation.Validation;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDException;
import foundation.identity.jsonld.JsonLDObject;
import info.weboftrust.ldsignatures.verifier.EcdsaSecp256k1Signature2019LdVerifier;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2018LdVerifier;
import info.weboftrust.ldsignatures.verifier.Ed25519Signature2020LdVerifier;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static wu.CredentialsUtil.PUBLIC_KEY_ISSUER;

public class CredentialsHelper {
    static boolean verify(VerifiablePresentation credential) throws JsonLDException, GeneralSecurityException, IOException {
        return verify(credential, PUBLIC_KEY_ISSUER);
    }

    static boolean verify(VerifiableCredential credential) throws JsonLDException, GeneralSecurityException, IOException {
        return verify(credential, PUBLIC_KEY_ISSUER);
    }

    static boolean verify(VerifiablePresentation credential, String pubKey) throws IOException, GeneralSecurityException, JsonLDException {
        return verify(credential, List.of(Hex.decode(pubKey)));
    }

    static boolean verify(VerifiableCredential credential, String pubKey) throws IOException, GeneralSecurityException, JsonLDException {
        return verify(credential, List.of(Hex.decode(pubKey)));
    }

    static boolean verify(VerifiablePresentation credential, List<byte[]> pubKeys) throws IOException, GeneralSecurityException, JsonLDException {
        System.out.println("\n\n\n----------------\n\n\n");

        for (byte[] pubKey: pubKeys) {
            ECKey publicKey = ECKey.fromPublicOnly(pubKey);

            EcdsaSecp256k1Signature2019LdVerifier verifier = new EcdsaSecp256k1Signature2019LdVerifier(publicKey);
            if (verifier.verify(credential)) {
                return true;
            }
        }
        return false;
    }

    static boolean verify(VerifiableCredential credential, List<byte[]> pubKeys) throws IOException, GeneralSecurityException, JsonLDException {
        // System.out.println("\n\n\n----------------\n\n\n");
        // System.out.println(credential);

        ConfigurableDocumentLoader documentLoader = (ConfigurableDocumentLoader) credential.getDocumentLoader();
        documentLoader.setEnableHttps(true);


        Validation.validate(credential);

        for (byte[] pubKey: pubKeys) {
             ECKey publicKey = ECKey.fromPublicOnly(pubKey);

             EcdsaSecp256k1Signature2019LdVerifier verifier = new EcdsaSecp256k1Signature2019LdVerifier(publicKey);
             // Ed25519Signature2020LdVerifier verifier = new Ed25519Signature2020LdVerifier(pubKey);  // Da ist ein Typo in Ed25519Signature2020LdVerifier, man muss die neuste Version herunterladen damit es klappt


            // System.out.println(verifier.getSignatureSuite());
            // System.out.println(verifier.getVerifier());
            if (verifier.verify(credential)) {
                return true;
            }
        }
        return false;
    }
}
