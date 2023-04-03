import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.KeyPair;
import java.util.UUID;

public class HandlerJWT {
    //t

    // for production: https://api.high-mobility.com/v1 . For emulators/development: https://sandbox.api.high-mobility.com/v1

    String JsonWebToken = "";
    String id = "4641e174-4134-44e0-a7ad-8b7110dddeb0";
    String pk = "-----BEGIN PRIVATE KEY-----\nMIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgE76M1h3vVMe0qX8i\ngVxXT61MzKsXag4EeBp0LT0hnsmhRANCAASX/N2hmY9Y55zEboEGlyVCr/5YZ7BK\nyeh8zs/MFmTaUWQUfcV1BleFsCmkg6AuAYYHUUHFpf69i8dhOHI2rjSf\n-----END PRIVATE KEY-----";



    public void CreateJWT(){



        // App id..? Check if correct to use that for UUID. Generated from an UUID generator.
        String iss = "9C31EE0DBFA7CE490AAFE951";
        // Take care to change if needed
        String aud = "https://sandbox.api.high-mobility.com/v1";
        // UNIX timestamp
        long iat = System.currentTimeMillis()/1000L;
        // Check if needed
        String iatString = Long.toString(iat);
        // Generates an UUID and removes dashes from it
        String jti = UUID.randomUUID().toString().replace("-", "");
        // version. Supposedly static (either 1 or 2. 1 in doc text, but 2 in example from High Mobility)
        String ver = "2";

        String jsonStructure = "{ \n 'ver': " + ver + ",\n 'iss': " + iss + ",\n " +
                "'aud': " + aud + ",\n 'jti': " + jti + ",\n 'iat': " + iatString + "\n}";



        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES256);

        String signedToken = Jwts.builder()
                .setSubject("Test")
                .signWith(keyPair.getPrivate())
                .compact();



        /*
        KeyPair key = Keys.keyPairFor(SignatureAlgorithm.ES256);
        System.out.println(key.getPrivate() + "\n" + key);*/

/*
        String signedToken = Jwts.builder().setSubject("Test").signWith(key).compact();
        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(signedToken).getBody().getSubject().equals("Test");
        System.out.println(signedToken);*/




        /*{
            "iss": "API Key uuid",
                "aud": "https://sandbox.api.high-mobility.com/v1",
                "iat": "Current datetime in UNIX timestamp",
                "jti": "A random and unique UUIDv4",
                "ver": 1
        }*/

    }
}
