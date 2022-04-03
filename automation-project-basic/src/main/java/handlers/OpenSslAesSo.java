package handlers;

/*set PASSP=pass
openssl enc -aes-256-cbc -a -salt -p -pass env:PASSP -in msg.txt -out msg.txt.enc
type msg.txt.enc
openssl enc -aes-256-cbc -a -salt -p -pass env:PASSP -d -in msg.txt.enc
@echo off
@echo:*/
	
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

//Solution from Stackoverflow - Only decryption

public class OpenSslAesSo {
	
	private static final Charset ASCII = Charset.forName("ASCII");
	private static final int INDEX_KEY = 0;
	private static final int INDEX_IV = 1;
	private static final int ITERATIONS = 1;
	private static final int SALT_OFFSET = 8;
	private static final int SALT_SIZE = 8;
	private static final int CIPHERTEXT_OFFSET = SALT_OFFSET + SALT_SIZE;
	private static final int KEY_SIZE_BITS = 256;

	/**
	 * Thanks go to Ola Bini for releasing this source on his blog. The source was
	 * obtained from <a href="http://olabini.com/blog/tag/evp_bytestokey/">here</a>
	 * 
	 */

	public static byte[][] EVP_BytesToKey(int key_len, int iv_len, MessageDigest md, byte[] salt, byte[] data,
			int count) {

		byte[][] both = new byte[2][];
		byte[] key = new byte[key_len];
		int key_ix = 0;
		byte[] iv = new byte[iv_len];
		int iv_ix = 0;
		both[0] = key;
		both[1] = iv;
		byte[] md_buf = null;
		int nkey = key_len;
		int niv = iv_len;
		int i = 0;
		if (data == null) {
			return both;
		}
		int addmd = 0;
		for (;;) {
			md.reset();
			if (addmd++ > 0) {
				md.update(md_buf);
			}
			md.update(data);
			if (null != salt) {
				md.update(salt, 0, 8);
			}
			md_buf = md.digest();
			for (i = 1; i < count; i++) {
				md.reset();
				md.update(md_buf);
				md_buf = md.digest();
			}
			i = 0;
			if (nkey > 0) {
				for (;;) {
					if (nkey == 0)
						break;
					if (i == md_buf.length)
						break;
					key[key_ix++] = md_buf[i];
					nkey--;
					i++;
				}
			}
			if (niv > 0 && i != md_buf.length) {
				for (;;) {
					if (niv == 0)
						break;
					if (i == md_buf.length)
						break;
					iv[iv_ix++] = md_buf[i];
					niv--;
					i++;
				}
			}
			if (nkey == 0 && niv == 0) {
				break;
			}
		}
		for (i = 0; i < md_buf.length; i++) {
			md_buf[i] = 0;
		}
		return both;
	}

	//Create Key & IV
	public static byte[][] getKeyIV(byte[] headerSaltAndCipherText, Cipher aesCBC, String password) {		
		byte[] salt = Arrays.copyOfRange(headerSaltAndCipherText, SALT_OFFSET, SALT_OFFSET + SALT_SIZE);
		System.out.println("SALT: "+salt.toString());
		byte[][] keyAndIV=null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			keyAndIV = EVP_BytesToKey(KEY_SIZE_BITS / Byte.SIZE, aesCBC.getBlockSize(), md5, salt,
					password.getBytes(ASCII), ITERATIONS);
		} catch (Exception e) {e.printStackTrace();}		
		return keyAndIV;
	}
		
	// https://stackoverflow.com/questions/11783062/how-to-decrypt-file-in-java-encrypted-with-openssl-command-using-aes
	public static String decrypt(String encryptedMsg, String password) {

		String decryptedMsg =null;		
		byte[] headerSaltAndCipherText = Base64.decodeBase64(encryptedMsg);
		byte[] encrypted = Arrays.copyOfRange(headerSaltAndCipherText, CIPHERTEXT_OFFSET, headerSaltAndCipherText.length);
		try {
			Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			final byte[][] keyAndIV = getKeyIV(headerSaltAndCipherText, aesCBC, password);
			SecretKeySpec key = new SecretKeySpec(keyAndIV[INDEX_KEY], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[INDEX_IV]);
			aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decrypted = aesCBC.doFinal(encrypted);
			decryptedMsg = new String(decrypted, ASCII);
		} catch (Exception e) {e.printStackTrace();}
		
		return decryptedMsg;
	}

	public static void main(String[] args) {

		String msg = "the decrypted message is this";		
		String password = "pass";
					
		String encryptedMsg = "U2FsdGVkX190A5FsNTanwTKBdex29SpnH4zWkZN+Ld+MmbJgK4BH1whGIRRSpOJT";
		System.out.println(decrypt(encryptedMsg, password));		
	}
}