package sample.netty.chat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleChatTest {

	private static final Logger logger = LoggerFactory.getLogger(SampleChatTest.class);

	@Test
	public void testTypeLength() {
		String character = new String("A");
		logger.info("character length is {}", character.getBytes().length);
		logger.info("{}", character.getBytes()[0]);
	}

	@Test
	public void testWriteBytes2File() throws IOException {
		String filename = "chat.tmp";
		String path = SampleChatTest.class.getClassLoader().getResource(".").getPath();
		File file = new File(path, filename);
		logger.info("file {}", file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream os = new DataOutputStream(fos);

		os.writeInt(1);
		os.writeByte(2);
		os.write(0x87);
		os.writeChar('A');
		os.writeChar('男');
		os.writeByte(1);
		os.writeBytes("1234567890");

		// close
		os.close();
		fos.close();
	}

	@Test
	public void testBytes() {
		byte[] array = new byte[] { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30 };
		String str = new String(array);
		logger.info("string is {}", str);
	}

	@Test
	public void testMax() {
		int max = 1 << 15;
		logger.info("max:{}", max);
	}

	@Test
	public void testMd5() {
		logger.info("1234 md5:{}", DigestUtils.md5("1234").length);
		logger.info("abcd md5:{}", DigestUtils.md5("abcd").length);
	}

}
