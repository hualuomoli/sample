package sample.protostuff.serialize;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import sample.protostuff.serialize.entity.Address;
import sample.protostuff.serialize.entity.User;
import sample.protostuff.serialize.entity.UserType;
import sample.protostuff.serialize.util.Base64;

public class ProtostuffSerializeTest {

    private static final Logger logger = LoggerFactory.getLogger(ProtostuffSerializeTest.class);

    @Test
    @Ignore
    public void testSerializer() {
        User user = new User();
        user.setUsername("jack");
        user.setNickname("杰克");
        user.setUserType(UserType.ORDINARY);

        // address
        Address address = new Address();
        address.setProvince("山东省");
        address.setCity("青岛市");
        address.setCounty("市北区");
        address.setDistrict("合肥路");
        address.setDetailInfo("666号A栋3层608");
        user.setAddress(address);

        // serial
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        byte[] bytes = ProtostuffIOUtil.toByteArray(user, schema, buffer);
        logger.info("serial user base64 is {}", Base64.encode(bytes));
    }

    @Test
    @Ignore
    public void testDeserializer() {
        String base64 = "CgRqYWNrEgbmnbDlhYsYACMKCeWxseS4nOecgRIJ6Z2S5bKb5biCGgnluILljJfljLoiCeWQiOiCpei3ryoRNjY25Y+3QeagizPlsYI2MDgk";
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        User user = new User();
        ProtostuffIOUtil.mergeFrom(Base64.decode(base64), user, schema);

        Assert.assertEquals("jack", user.getUsername());
        Assert.assertEquals("杰克", user.getNickname());
        Assert.assertEquals(UserType.ORDINARY, user.getUserType());
        Assert.assertNotNull(user.getAddress());
        Assert.assertEquals("山东省", user.getAddress().getProvince());
        Assert.assertEquals("青岛市", user.getAddress().getCity());
        Assert.assertEquals("市北区", user.getAddress().getCounty());
        Assert.assertEquals("合肥路", user.getAddress().getDistrict());
        Assert.assertEquals("666号A栋3层608", user.getAddress().getDetailInfo());
    }

}
