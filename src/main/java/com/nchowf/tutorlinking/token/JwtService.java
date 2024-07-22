package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.user.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration-time}")
    private Long EXPIRATION_TIME;
    @Value("${jwt.refreshable-time}")
    private Long REFRESHABLE_TIME;
    public String generateToken(User user) throws JOSEException {

        return buildToken(user, EXPIRATION_TIME);
    }
    public String generateRefreshToken(User user) throws JOSEException {

        return buildToken(user, REFRESHABLE_TIME);
    }
    private String buildToken(User user, Long expiration) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("tutor-linking.com")
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + expiration)
                )
                .jwtID(UUID.randomUUID().toString())
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
    public String extractUsername(String token) throws ParseException {
        return getSignedJWT(token).getJWTClaimsSet().getSubject();
    }
    public String extractScope(String token) throws ParseException {
        return getSignedJWT(token).getJWTClaimsSet().getStringClaim("scope");
    }
    public String extractJwtId(String token) throws ParseException {
        return getSignedJWT(token).getJWTClaimsSet().getJWTID();
    }
    public boolean isVerifiedToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = getSignedJWT(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isVerified = signedJWT.verify(verifier);
//        if(!isVerified && expiryTime.after(new Date()))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        return !isInvalidatedToken(signedJWT.getJWTClaimsSet().getJWTID());
        return isVerified && expiryTime.after(new Date());
    }
}
