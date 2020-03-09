package app.wegual.poc.mail.service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

import javax.crypto.KeyGenerator;

import org.springframework.stereotype.Service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

@Service
public class OTPGenerator {
	
	public static String generateOTP(Instant moment) throws NoSuchAlgorithmException, InvalidKeyException {
		Key key = null;
		TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
		KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());

	    // SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers 128-byte (1024-bit) keys
	    keyGenerator.init(512);

	    key = keyGenerator.generateKey();
		return String.valueOf(totp.generateOneTimePassword(key, moment));
	}

}
