package wu;

import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.VerifiablePresentation;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDException;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.jsonld.LDSecurityKeywords;
import info.weboftrust.ldsignatures.signer.EcdsaSecp256k1Signature2019LdSigner;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;

import static wu.CredentialsUtil.*;
import static wu.TestEBSIDiplomaCredential.getVerifiableCredential;
import static wu.TestEBSIDiplomaCredential.makeClaims;

public class TestEBSIDiplomaPresentation extends CredentialsHelper {
    public static void main(String[] args) throws Throwable {
        Map<String, Object> claims = makeClaims();

        VerifiableCredential vc = getVerifiableCredential(studentDID, issuerDID, claims, getPrivateKeyIssuer());

        VerifiablePresentation vp = getVerifiablePresentation(vc);
        System.out.println(vp.toJson(true));

        boolean verified = verify(vp);
        System.out.println(verified);
    }

    static VerifiablePresentation getVerifiablePresentation(VerifiableCredential vc) throws JsonLDException, GeneralSecurityException, IOException {
        VerifiablePresentation verifiablePresentation = VerifiablePresentation.builder()
                .verifiableCredential(vc)
                .id(studentDID)
                .build();

        ConfigurableDocumentLoader documentLoader = (ConfigurableDocumentLoader) verifiablePresentation.getDocumentLoader();
        documentLoader.setEnableHttps(true);

        ECKey privateKey = ECKey.fromPrivate(Hex.decode(getPrivateKeyStudent()));

        EcdsaSecp256k1Signature2019LdSigner signer = new EcdsaSecp256k1Signature2019LdSigner(privateKey);
        // Ed25519Signature2020LdSigner signer = getSignerV2();
        signer.setCreated(new Date());
        signer.setProofPurpose(LDSecurityKeywords.JSONLD_TERM_ASSERTIONMETHOD);
        signer.setVerificationMethod(URI.create(studentDID.toString() + "#keys-1"));
        LdProof ldProof = signer.sign(verifiablePresentation);

        return verifiablePresentation;
    }
}
