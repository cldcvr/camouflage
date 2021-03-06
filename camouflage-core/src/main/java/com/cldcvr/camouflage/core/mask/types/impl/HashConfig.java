package com.cldcvr.camouflage.core.mask.types.impl;

import com.cldcvr.camouflage.core.mask.types.AbstractMaskType;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Masks data with a hash string.
 * Note: Changes in salt over the same input will result in different hash strings.
 */
public final class HashConfig extends AbstractMaskType implements Serializable {

    private final String salt;
    private final byte[] saltBytes;
    private final Logger LOG = LoggerFactory.getLogger(HashConfig.class);

    /**
     * Accepts a salt string from the used or then uses blank as default.
     * Strips salt string to only 16 chars.
     *
     * @param salt
     */
    public HashConfig(String salt) {
        this.salt = salt == null ? "" : salt;
        this.saltBytes = formatSalt();
    }

    public String name() {
        return "HASH_CONFIG";
    }

    /**
     * Strips salt to 16 chars and converts to byte array.
     *
     * @return
     */
    public byte[] formatSalt() {
        byte[] saltBytes = this.salt.getBytes();
        if (saltBytes.length > 16)
            return Arrays.copyOfRange(saltBytes, 0, 16);
        else return saltBytes;
    }

    /**
     * Takes the input string and generates a hash digest for the same
     *
     * @param input String to hash.
     * @return byte array of hash digest.
     * @throws NoSuchAlgorithmException
     */
    public byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(this.saltBytes);
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Changes byte array of hash to String
     *
     * @param hash
     * @return
     */
    public String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * Apply masking over input
     *
     * @param input String to mask
     * @param regex Not used here as salt is being applied.
     * @return
     */
    public String applyMaskStrategy(String input, String regex) {
        try {
            if (input == null) {
                return null;
            }
            return toHexString(getSHA(input));
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("Algorithm error :: " + ex.toString());
        } catch (Exception ex) {
            LOG.error("Unknown error while applying hash config :: " + ex.toString());
        }
        return "";
    }
}
