package wu;

import com.danubetech.keyformats.JWK_to_PrivateKey;
import com.danubetech.keyformats.jose.JWK;
import com.danubetech.verifiablecredentials.CredentialSubject;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDException;
import info.weboftrust.ldsignatures.LdProof;
import info.weboftrust.ldsignatures.jsonld.LDSecurityKeywords;
import info.weboftrust.ldsignatures.signer.EcdsaSecp256k1Signature2019LdSigner;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.*;

import static wu.CredentialsUtil.*;


public class TestEBSIDiplomaCredential extends CredentialsHelper {
    public static void main(String[] args) throws Throwable {
        Map<String, Object> claims = makeClaims();

        VerifiableCredential vc = getVerifiableCredential(studentDID, issuerDID, claims);

        boolean verified = verify(vc);
        System.out.println(verified);
    }

    static VerifiableCredential getVerifiableCredential(URI studentDID, URI issuerDID, Map<String, Object> claims, String privateKeyIssuer) throws JsonLDException, GeneralSecurityException, IOException {
        CredentialSubject credentialSubject = CredentialSubject.builder()
                .id(studentDID)
                .type("Student")
                .claims(claims)
                .build();

        VerifiableCredential verifiableCredential = VerifiableCredential.builder()
                .contexts(Arrays.asList(
                        // URI.create("https://danubetech.github.io/ebsi4austria-examples/context/essif-schemas-vc-2020-v1.jsonld")
                        URI.create("https://wicket1001.github.io/ebsi4austria-examples/context/essif-schemas-vc-2020-v2.jsonld")
                        // URI.create("https://www.w3.org/ns/did/v1")
                        // URI.create("https://bach.wu.ac.at/static/app/ebsi/wu-schema-vc-2020-v1.jsonld")
                        ))
                .types(Arrays.asList("VerifiableAttestation", "DiplomaCredential"))
                .issuer(issuerDID)
                .issuanceDate(new Date())
                .credentialSubject(credentialSubject)
                .build();

        ConfigurableDocumentLoader documentLoader = (ConfigurableDocumentLoader) verifiableCredential.getDocumentLoader();
        documentLoader.setEnableHttps(true);

        EcdsaSecp256k1Signature2019LdSigner signer = getSigner2019();
        signer.setCreated(new Date());
        signer.setProofPurpose(LDSecurityKeywords.JSONLD_TERM_ASSERTIONMETHOD);
        signer.setVerificationMethod(URI.create(issuerDID.toString() + "#keys-1"));
        LdProof ldProof = signer.sign(verifiableCredential);
        return verifiableCredential;
    }

    /*
    static Ed25519Signature2020LdSigner getSigner2020() {
        return new Ed25519Signature2020LdSigner(DidKey.privateKey);
    }
     */

    private static EcdsaSecp256k1Signature2019LdSigner getSigner2019() {
        // ECKey privateKey = ECKey.fromPrivate(Hex.decode(privateKeyIssuer));

        // EcdsaSecp256k1Signature2019LdSigner signer = new EcdsaSecp256k1Signature2019LdSigner(privateKey);
        HashMap<String, Object> keyWU = new HashMap<>();
        String did = "did:ebsi:z23EQVGi5so9sBwytv6nMXMo";
        keyWU.put("kty", "EC");
        keyWU.put("crv", "secp256k1");
        keyWU.put("x", "XGngxt2DXUbM1l39ktGFHtRoCMca9xw1pdPAS4h98i4");
        keyWU.put("y", "rqfYMx8T9x47jRMZZmaL60Gxr65EZjYwIqckTSuh6cs");
        keyWU.put("d", "ZTb36JucH4Xz0qDO84SJWA9wmVGihFAHUyLiIK3RQrQ");
        keyWU.put("kid", "did:ebsi:z23EQVGi5so9sBwytv6nMXMo#keys-1");
        try {
            return new EcdsaSecp256k1Signature2019LdSigner(JWK_to_PrivateKey.JWK_to_secp256k1PrivateKey(JWK.fromMap(keyWU)));
        } catch (IOException e) {
            return null;
        }
        // return signer;
    }

    private static VerifiableCredential getVerifiableCredential(URI studentDID, URI issuerDID, Map<String, Object> claims) throws IOException, GeneralSecurityException, JsonLDException {
        return getVerifiableCredential(studentDID, issuerDID, claims, getPrivateKeyIssuer());
    }

    static Map<String, Object> makeClaims() {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("studyProgram", "Master Studies in Strategy, Innovation, and Management Control");
        claims.put("immatriculationNumber", "00000000");
        claims.put("currentGivenName", "Eva");
        claims.put("currentFamilyName", "Musterfrau");
        claims.put("learningAchievement", "Master's Degree");
        claims.put("dateOfBirth", "1999-10-22T00:00:00.000Z");
        claims.put("dateOfAchievement", "2021-01-04T00:00:00.000Z");
        claims.put("overallEvaluation", "passed with honors");
        claims.put("eqfLevel", "http://data.europa.eu/snb/eqf/7");
        claims.put("targetFrameworkName", "European Qualifications Framework for lifelong learning - (2008/C 111/01)");
        return claims;
    }
}

