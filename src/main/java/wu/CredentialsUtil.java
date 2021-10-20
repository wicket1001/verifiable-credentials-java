package wu;

import java.net.URI;

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

    static String PRIVATE_KEY_ISSUER = "61bcd3b7c3b8c0ab04baa2be1bb412e448fe5ac0a4338410980a9501eb1f11d7";
    static String PUBLIC_KEY_ISSUER = "04f7fc911b217f3e3bc57011153f70865f47ebec7a27a6f02c112436637a5a3257582e28044c4493e90adfee7232874c6660011c9b32cf1627349f531fbc914805";

    static String PRIVATE_KEY_STUDENT = "5c7cd8cf40a4a5f55cc6f77ecfb591f4c23ba82306715f236ab54255f0eb8ccf";

    static URI issuerDID = URI.create("did:ebsi:51rzpDXXCtKExG47boFBahAgd2dtfAZbQxMHM17mYKoq");
    // static URI studentDID = URI.create("did:key:z6Mki97ezMnXisk1iAyvVr4rJkNrWRBoR8f5viPJ62Jw6s98");
    static URI studentDID = URI.create("did:ebsi:csapca8odx7vdpezz158sq7strxxvb3yxgq2uruawavz");
}
