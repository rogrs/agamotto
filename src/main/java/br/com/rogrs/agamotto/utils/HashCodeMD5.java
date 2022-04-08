package br.com.rogrs.agamotto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class HashCodeMD5 {

	private String location;
	
	public HashCodeMD5() {
		
	}

	public HashCodeMD5(String location) {
		this.location = location;
	}

	public String checkSumMD5(File f) throws NoSuchAlgorithmException, FileNotFoundException {

		MessageDigest digest = MessageDigest.getInstance("MD5");
		InputStream is = new FileInputStream(f);
		byte[] buffer = new byte[8192];
		int read = 0;
		String output = null;
		try {
			while ((read = is.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);
			log.info(f.getAbsolutePath() + " MD5: " + output);
		} catch (IOException e) {
			throw new RuntimeException("Não foi possivel processar o arquivo.", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException("Não foi possivel fechar o arquivo", e);
			}
		}
		return output;
	}

	public String getHashMD5() {
		try {
			return checkSumMD5(new File(this.location));
		} catch (NoSuchAlgorithmException | FileNotFoundException e) {
			throw new RuntimeException("Não possível executar checkSum MD5 " + e.getMessage(), e);
		}
	}

}
