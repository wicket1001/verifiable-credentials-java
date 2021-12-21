package wu;

import com.danubetech.keyformats.JWK_to_PrivateKey;
import com.danubetech.keyformats.jose.JWK;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class CredentialsUtil {
        /*
        static String PRIVATE_KEY_ISSUER = "{\n" +
            "                \"kty\": \"EC\",\n" +
            "                \"d\": \"YbzTt8O4wKsEuqK-G7QS5Ej-WsCkM4QQmAqVAesfEdc\",\n" +
            "                \"crv\": \"secp256k1\",\n" +
            "                \"x\": \"9_yRGyF_PjvFcBEVP3CGX0fr7HonpvAsESQ2Y3paMlc\",\n" +
            "                \"y\": \"WC4oBExEk-kK3-5yModMZmABHJsyzxYnNJ9TH7yRSAU\"\n" +
            "        }";
            */

    // static String PRIVATE_KEY_ISSUER = "61bcd3b7c3b8c0ab04baa2be1bb412e448fe5ac0a4338410980a9501eb1f11d7";
    // static String PUBLIC_KEY_ISSUER = "04f7fc911b217f3e3bc57011153f70865f47ebec7a27a6f02c112436637a5a3257582e28044c4493e90adfee7232874c6660011c9b32cf1627349f531fbc914805";

    // static String PRIVATE_KEY_STUDENT = "5c7cd8cf40a4a5f55cc6f77ecfb591f4c23ba82306715f236ab54255f0eb8ccf";
    private static String privateKeyStudent = "0xc364810acc1a7882eacc611a07098060faa6d07e2a843e3ba959467af58ed7f4";

    static URI issuerDID = URI.create("did:ebsi:z23EQVGi5so9sBwytv6nMXMo");
    // static URI studentDID = URI.create("did:key:z6Mki97ezMnXisk1iAyvVr4rJkNrWRBoR8f5viPJ62Jw6s98");
    static URI studentDID = URI.create("did:ebsi:zqpZej3RbScW9feAjwipKn4");

    private static HashMap<String, Object> keyWU = new HashMap<>();

    public static HashMap<String, Object> getKeyWU() {
        keyWU.put("kty", "EC");
        keyWU.put("crv", "secp256k1");
        keyWU.put("x", "XGngxt2DXUbM1l39ktGFHtRoCMca9xw1pdPAS4h98i4");
        keyWU.put("y", "rqfYMx8T9x47jRMZZmaL60Gxr65EZjYwIqckTSuh6cs");
        keyWU.put("d", "ZTb36JucH4Xz0qDO84SJWA9wmVGihFAHUyLiIK3RQrQ");
        keyWU.put("kid", "did:ebsi:z23EQVGi5so9sBwytv6nMXMo#keys-1");
        return keyWU;
    }

    private static HashMap<String, Object> keyStudent = new HashMap<>();

    public static HashMap<String, Object> getKeyStudent() {

        return keyStudent;
    }

    public static String getPrivateKeyIssuer() {
        try {
            return JWK_to_PrivateKey.JWK_to_secp256k1PrivateKey(JWK.fromMap(getKeyWU())).getPrivateKeyAsHex();
        } catch (IOException e) {
            return "";
        }
    }

    public static String getPublicKeyIssuer() {
        try {
            return JWK_to_PrivateKey.JWK_to_secp256k1PrivateKey(JWK.fromMap(getKeyWU())).getPublicKeyAsHex();
        } catch (IOException e) {
            return "";
        }
    }

    public static String getPublicKeyStudent() {
        return ECKey.fromPrivate(Hex.decode(privateKeyStudent.substring(2))).getPublicKeyAsHex();
    }

    public static String getPrivateKeyStudent() {
        return privateKeyStudent.substring(2);
    }
}
