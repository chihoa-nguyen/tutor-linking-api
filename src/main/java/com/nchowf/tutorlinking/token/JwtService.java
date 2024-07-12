package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.parent.ParentRepo;
import com.nchowf.tutorlinking.tutor.TutorRepo;
import com.nchowf.tutorlinking.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Long EXPIRATION_TIME;
    public String generateToken(User user ) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("tutor-linking.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(EXPIRATION_TIME, ChronoUnit.SECONDS).toEpochMilli()
                ))
                //.jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRole().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
        return jwsObject.serialize();
    }
    private SignedJWT getSignedJWT(String token) throws ParseException {
        return SignedJWT.parse(token);
    }
}
